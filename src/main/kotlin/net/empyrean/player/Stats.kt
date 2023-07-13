package net.empyrean.player

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.nullable
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.*
import net.empyrean.util.text.Case
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.TextColor
import java.util.*
import kotlin.math.roundToInt
import kotlin.reflect.KProperty

val ChatFormatting.modern: TextColor get() = TextColor.fromLegacyFormat(this)!!

@Serializable
enum class PlayerStat(
    val defaultValue: Float,
    val color: TextColor,
    val display: Boolean = true,
    val shouldFormat: (Float) -> Boolean = { it > 0f },
    val formatKeyValue: Boolean = false,
    translationKey: String? = null,
    val percentage: Boolean = true,
    val modNames: TreeMap<Float, String> = StatsFormatter.generalModNames
) {
    DAMAGE_GENERIC(1f, ChatFormatting.WHITE.modern, formatKeyValue = true, shouldFormat = { it > 1f }, translationKey = "damage.generic", percentage = false),
    DAMAGE_MELEE(0f, ChatFormatting.WHITE.modern, formatKeyValue = true, translationKey = "damage.melee", percentage = false),
    DAMAGE_RANGED(0f, ChatFormatting.WHITE.modern, formatKeyValue = true, translationKey = "damage.ranged", percentage = false),
    DAMAGE_MAGIC(0f, ChatFormatting.WHITE.modern, formatKeyValue = true, translationKey = "damage.magic", percentage = false),
    DAMAGE_SUMMON(0f, ChatFormatting.WHITE.modern, formatKeyValue = true, translationKey = "damage.summon", percentage = false),
    DAMAGE_ROGUE(0f, ChatFormatting.WHITE.modern, formatKeyValue = true, translationKey = "damage.rogue", percentage = false),

    MAX_HEALTH(20f, ChatFormatting.RED.modern, percentage = false, formatKeyValue = true),
    MAX_MANA(20f, ChatFormatting.BLUE.modern, percentage = false, formatKeyValue = true),
    DEFENSE(0f, ChatFormatting.WHITE.modern, formatKeyValue = true, percentage = false),
    ATTACK_SPEED(0.05f, ChatFormatting.WHITE.modern, modNames = StatsFormatter.movementModNames), // yes its movement, don't ask why
    // TODO: test regen capabilities might need balancing
    MANA_REGEN(2f, ChatFormatting.BLUE.modern, percentage = false, modNames = StatsFormatter.regenModNames), // mana/s
    HEALTH_REGEN(2f, ChatFormatting.RED.modern, translationKey = "regen", percentage = false, modNames = StatsFormatter.regenModNames), // health/s
    MOVEMENT_SPEED(0.12f, ChatFormatting.WHITE.modern, modNames = StatsFormatter.movementModNames),
    FLIGHT_SPEED(0f, ChatFormatting.WHITE.modern, modNames = StatsFormatter.flightModNames),
    FLIGHT_TIME(0f, ChatFormatting.WHITE.modern, modNames = StatsFormatter.flightModNames),
    JUMP_HEIGHT(0f, ChatFormatting.WHITE.modern, modNames = StatsFormatter.movementModNames),
    JUMP_SPEED(0f, ChatFormatting.WHITE.modern, modNames = StatsFormatter.movementModNames),
    ACCELERATION(0f, ChatFormatting.WHITE.modern, modNames = StatsFormatter.movementModNames),
    DAMAGE_REDUCTION(0f, ChatFormatting.WHITE.modern, modNames = StatsFormatter.movementModNames),

    CRIT_CHANCE(0.01f, ChatFormatting.WHITE.modern, modNames = StatsFormatter.critChanceModNames),
    CRIT_DAMAGE(0.5f, ChatFormatting.WHITE.modern, modNames = StatsFormatter.critDamageModNames),

    DAMAGE_MUL(1f, ChatFormatting.WHITE.modern, formatKeyValue = true, display = false),
    DAMAGE_MELEE_MUL(1f, ChatFormatting.WHITE.modern, false),
    DAMAGE_RANGED_MUL(1f, ChatFormatting.WHITE.modern, false),
    DAMAGE_MAGIC_MUL(1f, ChatFormatting.WHITE.modern, false),
    DAMAGE_SUMMON_MUL(1f, ChatFormatting.WHITE.modern, false),

    KNOCKBACK(0.1f, ChatFormatting.WHITE.modern, modNames = StatsFormatter.generalModNames),
    MANA_COST_MUL(0f, ChatFormatting.WHITE.modern, formatKeyValue = true)

    ;

    val componentKey: String = "stat.empyrean.${translationKey ?: name.lowercase()}"
}

@Serializable(with = StatsSerializer::class)
data class Stats(val inner: EnumMap<PlayerStat, Float>) {
    companion object {
        fun prefill() = Stats(EnumMap(PlayerStat.values().associateWith { it.defaultValue }))
        fun empty() = Stats(EnumMap(PlayerStat::class.java))
        fun of(builder: StatBuilder.() -> Unit) = StatBuilder(empty()).apply(builder).inner
    }

    operator fun get(stat: PlayerStat): Float {
        return if(inner.containsKey(stat)) inner[stat]!! else stat.defaultValue
    }

    fun getOrNull(stat: PlayerStat): Float? {
        return inner[stat]
    }

    operator fun set(stat: PlayerStat, value: Float) {
        inner[stat] = value
    }

    operator fun getValue(selfRef: Any, prop: KProperty<*>): Number {
        // attempting to determine stat by property name
        val statName = Case.camelToSnake(prop.name).uppercase()
        return try {
            val stat = PlayerStat.valueOf(statName)
            this[stat]
        } catch(e: IllegalArgumentException) {
            0f // unlucky
        }
    }

    operator fun setValue(selfRef: Any?, prop: KProperty<*>, newValue: Number) {
        // attempting to determine stat by property name
        val statName = Case.camelToSnake(prop.name).uppercase()
        try {
            val stat = PlayerStat.valueOf(statName)
            this[stat] = newValue.toFloat()
        } catch(e: IllegalArgumentException) {
            // unlucky
        }
    }

    fun merge(with: Stats): Stats {
        if(with.isEmpty())
            return this
        val selfClone = this.inner.clone()
        with.inner.forEach { (key, value) ->
            selfClone.merge(key, value) { current, new -> current + new }
        }
        return Stats(selfClone)
    }

    fun mergeMany(iterable: Iterable<Stats>): Stats {
        val selfClone = this.inner.clone()
        for(stat in iterable) {
            if(stat.isEmpty())
                continue
            stat.inner.forEach { (key, value) ->
                selfClone.merge(key, value) { current, new -> current + new }
            }
        }
        return Stats(selfClone)
    }

    fun mergeMany(vararg many: Stats): Stats {
        val list = many.toList().iterator()
        return this.mergeMany(Iterable { list })
    }

    operator fun times(by: Float): Stats {
        val selfClone = this.inner.clone()
        return Stats(EnumMap(selfClone.mapValues { each -> each.value * by }))
    }

    operator fun minus(other: Stats): Stats {
        if(other.isEmpty())
            return this
        val selfClone = this.inner.clone()
        other.inner.forEach { (key, value) ->
            selfClone.merge(key, value) { current, new -> current - new }
        }
        return Stats(selfClone)
    }

    fun isEmpty(): Boolean {
        return this.inner.isEmpty()
    }
}

data class StatBuilder(val inner: Stats) {
    var damageGeneric by inner
    var damageMelee by inner
    var damageRanged by inner
    var damageMagic by inner
    var damageSummon by inner
    var damageRogue by inner
    var attackSpeed by inner

    var maxHealth by inner
    var maxMana by inner
    var defense by inner
    var manaRegen by inner
    var healthRegen by inner
    var movementSpeed by inner
    var flightSpeed by inner
    var flightTime by inner
    var jumpHeight by inner
    var jumpSpeed by inner
    var acceleration by inner
    var damageReduction by inner

    var critChance by inner
    var critDamage by inner

    var damageMul by inner
    var damageMeleeMul by inner
    var damageRangedMul by inner
    var damageMagicMul by inner
    var damageSummonMul by inner
    var damageRogueMul by inner

    var knockback by inner
    var manaCostMul by inner
}

object StatsSerializer : KSerializer<Stats> {
    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("net.empyrean.player.Stats") {
            PlayerStat.values().forEach {
                element(it.name.lowercase(), serialDescriptor<Float>().nullable)
            }
        }

    @OptIn(ExperimentalSerializationApi::class)
    override fun deserialize(decoder: Decoder): Stats {
        val outMap = EnumMap<PlayerStat, Float>(PlayerStat::class.java)
        decoder.decodeStructure(descriptor) {
            val expectedSize = PlayerStat.values().size
            if(decodeSequentially()) {
                for(single in 0 until expectedSize) {
                    val stat = PlayerStat.values()[single]
                    val value = decodeFloatElement(descriptor, single)
                    outMap[stat] = value
                }
            } else {
                for (single in 0 until expectedSize) {
                    val idx = decodeElementIndex(descriptor)
                    if (idx == CompositeDecoder.DECODE_DONE)
                        break
                    val stat = PlayerStat.values()[idx]
                    val value = decodeFloatElement(descriptor, idx)
                    outMap[stat] = value
                }
            }
            PlayerStat.values().forEach {
                outMap.putIfAbsent(it, it.defaultValue)
            }
        }
        return Stats(outMap)
    }

    override fun serialize(encoder: Encoder, value: Stats) {
        encoder.encodeStructure(descriptor) {
            value.inner.toList().forEachIndexed { idx, (_, value) ->
                encodeFloatElement(descriptor, idx, value)
            }
        }
    }
}

object StatsFormatter {
    val flightModNames = TreeMap(mapOf(
        0f to "Nonexistent",
        0.01f to "Very Low",
        0.02f to "Low",
        0.03f to "Moderate",
        0.06f to "Great",
        0.1f to "Amazing",
        0.5f to "Ludicrous"
    ))

    val critChanceModNames = TreeMap(mapOf(
        0f to "Extremely Low",
        0.03f to "Very Low",
        0.07f to "Low",
        0.1f to "Moderate",
        0.15f to "Great",
        0.20f to "Awesome",
        0.25f to "Insane",
        0.3f to "Empyrical",
        0.5f to "Macrocosmic",
        0.98f to "Guaranteed" // 0.98 hehe :3
    ))

    val critDamageModNames = TreeMap(mapOf(
        0f to "Zero",
        0.1f to "Very Low",
        0.3f to "Low",
        0.5f to "Moderate",
        0.7f to "Great",
        1f to "Amazing",
        1.5f to "Insane",
        2f to "Legendary",
        4f to "Empyrical",
        6f to "Macrocosmic"
    ))

    val regenModNames = TreeMap(mapOf(
        0f to "Nonexistent",
        0.1f to "Very Low",
        0.2f to "Low",
        0.5f to "Moderate",
        1f to "Good",
        1.5f to "Great",
        3f to "Amazing",
        5f to "Empyrical",
        10f to "Macrocosmic"
    ))

    val movementModNames = TreeMap(mapOf(
        0f to "Nonexistent",
        0.05f to "Very Low",
        0.1f to "Low",
        0.15f to "Moderate",
        0.2f to "Good",
        0.25f to "Great",
        0.35f to "Amazing",
        0.5f to "Insane",
        0.7f to "Empyrical",
        1f to "Macrocosmic"
    ))

    val generalModNames = TreeMap(mapOf(
        0f to "Nonexistent",
        0.1f to "Horrible",
        0.3f to "Very Low",
        0.4f to "Low",
        0.5f to "Average",
        0.7f to "Good",
        0.8f to "Great",
        1f to "Very high",
        1.5f to "Ludicrous"
    ))

    fun clampValue(stat: PlayerStat, value: Float): String {
        return stat.modNames.floorEntry(value).value
    }

    fun format(stats: Stats): List<Component> {
        return stats.inner.mapNotNull { (key, value) ->
            if(key.shouldFormat(value) && key.display) {
                if(key.formatKeyValue) {
                    Component.translatable(key.componentKey)
                        .append(Component.literal(": ")
                            .append(Component.literal(value.roundToInt().toString()).withStyle(Style.EMPTY.withColor(if(value > 0) ChatFormatting.GRAY else ChatFormatting.RED))))
                        .withStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))
                } else {
                    Component.translatable(clampValue(key, value)).append(Component.literal(" ")).append(Component.translatable(key.componentKey)).withStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))
                }
            } else null
        }
    }

    fun formatWithValues(stats: Stats): List<Component> {
        return formatExplicit(stats.inner.filter { it.key.display })
    }

    fun formatExplicit(map: Map<PlayerStat, Float>): List<Component> {
        return map.map { (key, value) ->
            if(key.percentage) {
                Component.translatable(key.componentKey).append(Component.literal(": ${if(value > 0f) "+" else ""}${(value * 100f).roundToInt()}%")).withStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))
            } else {
                Component.translatable(key.componentKey).append(Component.literal(": ${if(value > 0f) "+" else ""}${value.roundToInt()}")).withStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))
            }
        }
    }

    fun formatDiff(stats: Stats, diff: Stats): List<Component> {
        return stats.inner.mapNotNull { (key, value) ->
            if(key.shouldFormat(value) && key.display) {
                val otherValue = diff.getOrNull(key) ?: value
                if(key.formatKeyValue) {
                    val (prefix, postfixColor) = if(value > otherValue) {
                        // currently held item is worse
                        " ▲ " to ChatFormatting.GREEN
                    } else {
                        // currently held item is better
                        " ▼ " to ChatFormatting.RED
                    }
                    if(otherValue == value)
                        Component.translatable(key.componentKey)
                            .append(Component.literal(": ${value.roundToInt()}"))
                            .withStyle(ChatFormatting.GRAY)
                    else
                        Component.translatable(key.componentKey)
                            .append(Component.literal(":")
                                .append(Component.literal("$prefix${value.roundToInt()}").withStyle(Style.EMPTY.withColor(postfixColor))))
                            .append(Component.literal(" (${otherValue.roundToInt()})").withStyle(ChatFormatting.DARK_GRAY))
                            .withStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))
                } else {
                    val clamped = clampValue(key, value)
                    val otherClamped = if(otherValue == null) clamped else clampValue(key, otherValue)

                    val (prefix, postfixColor) = if(value > otherValue) {
                        // currently held item is worse
                        "▲ " to ChatFormatting.GREEN
                    } else if(otherValue > value) {
                        // currently held item is better
                        "▼ " to ChatFormatting.RED
                    } else "" to ChatFormatting.GRAY

                    Component.empty().append(Component.literal("$prefix$clamped ").withStyle(postfixColor))
                        .let {
                            if (clamped != otherClamped && otherValue != value) {
                                it.append(Component.literal("($otherClamped) ").withStyle(ChatFormatting.DARK_GRAY))
                            } else it
                        }
                        .append(Component.translatable(key.componentKey))
                        .withStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))
                }
            } else null
        }
    }
}