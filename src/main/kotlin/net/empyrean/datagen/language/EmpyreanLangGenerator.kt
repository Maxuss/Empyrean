package net.empyrean.datagen.language

import net.empyrean.block.EmpyreanBlocks
import net.empyrean.registry.EmpyreanItems
import net.empyrean.registry.EmpyreanRegistries
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import java.util.*

class EmpyreanLangGenerator(out: FabricDataOutput): FabricLanguageProvider(out, "en_us") {
    override fun generateTranslations(lang: TranslationBuilder) {
        generateItemTranslations(lang)
        generateBlockTranslations(lang)
        generateAdditionalTranslations(lang)
    }
    
    private fun generateAdditionalTranslations(lang: TranslationBuilder) {
        // <editor-fold desc="Config">
        lang.add("config.empyrean.category.stable", "Stable Options")
        lang.add("config.empyrean.category.stable.description", "Contains stable tested options for Empyrean")
        lang.add("config.empyrean.category.experimental", "Experimental Options")
        lang.add("config.empyrean.category.experimental.description", "Contains experimental/untested options for Empyrean")
        lang.add("config.empyrean.group.effects", "Effects")
        lang.add("config.empyrean.group.effects.tooltip", "Different options related to Empyrean effects. This is related to vanilla effects, not VFX.")
        lang.add("config.empyrean.option.compactEffectDisplay", "Compact effect display")
        lang.add("config.empyrean.option.compactEffectDisplay.tooltip", "Renders effects in inventory more compactly to leave space for more effects.")
        lang.add("config.empyrean.option.displayEffectLength", "Display effect duration")
        lang.add("config.empyrean.option.displayEffectLength.tooltip", "Display effect duration outside of inventory. Recommended to be turned on")
        lang.add("config.empyrean.group.expSingleplayer", "Singleplayer")
        lang.add("config.empyrean.group.expSingleplayer.tooltip", "Different options related to singleplayer for Empyrean. Highly experimental.")
        lang.add("config.empyrean.option.enableSingleplayer", "Enable singleplayer")
        lang.add("config.empyrean.option.enableSingleplayer.tooltip", "Allows to play Empyrean in singleplayer")
        // </editor-fold>
        
        // <editor-fold desc="Effect descriptions">
        lang.add("effect.minecraft.speed.description", "Movement speed increased")
        lang.add("effect.minecraft.slowness.description", "Movement speed decreased")
        lang.add("effect.minecraft.haste.description", "Digging speed increased vastly")
        lang.add("effect.minecraft.mining_fatigue.description", "Digging speed decreased vastly")
        lang.add("effect.minecraft.strength.description", "Deal much more damage")
        lang.add("effect.minecraft.instant_health.description", "Healed!")
        lang.add("effect.minecraft.instant_damage.description", "Damage dealt!")
        lang.add("effect.minecraft.jump_boost.description", "Jumping height increased moderately")
        lang.add("effect.minecraft.nausea.description", "Everything is warping")
        lang.add("effect.minecraft.regeneration.description", "Health regeneration boosted")
        lang.add("effect.minecraft.resistance.description", "Damage resistance greatly increased")
        lang.add("effect.minecraft.fire_resistance.description", "Immune to fire!")
        lang.add("effect.minecraft.water_breathing.description", "Oxygen capacity increased")
        lang.add("effect.minecraft.invisibility.description", "Enemies will not notice you")
        lang.add("effect.minecraft.blindness.description", "Can't see anything!")
        lang.add("effect.minecraft.night_vision.description", "Can see in the dark")
        lang.add("effect.minecraft.hunger.description", "Craving food!")
        lang.add("effect.minecraft.weakness.description", "Damage greatly decreased")
        lang.add("effect.minecraft.poison.description", "Slowly draining life")
        lang.add("effect.minecraft.wither.description", "Slowly draining life")
        lang.add("effect.minecraft.health_boost.description", "Maximum health increased")
        lang.add("effect.minecraft.absorption.description", "Protective health buffer")
        lang.add("effect.minecraft.saturation.description", "No longer feel hungry!")
        lang.add("effect.minecraft.glowing.description", "Might be attracting enemies")
        lang.add("effect.minecraft.levitation.description", "Slowly floating up")
        lang.add("effect.minecraft.luck.description", "You are feeling extra lucky!")
        lang.add("effect.minecraft.unluck.description", "You are feeling extra unlucky")
        lang.add("effect.minecraft.slow_falling.description", "Fall speed decreased")
        lang.add("effect.minecraft.conduit_power.description", "Conduit transports you nautical energy")
        lang.add("effect.minecraft.dolphins_grace.description", "Underwater movement speed increased")
        lang.add("effect.minecraft.bad_omen.description", "You are feeling ominous presence watching you from away...")
        // </editor-fold>
        
        // <editor-fold desc="Stats">
        lang.add("stat.empyrean.damage.generic", "Damage")
        lang.add("stat.empyrean.damage.melee", "Melee Damage")
        lang.add("stat.empyrean.damage.ranged", "Ranged Damage")
        lang.add("stat.empyrean.damage.magic", "Magic Damage")
        lang.add("stat.empyrean.damage.summon", "Summon Damage")
        lang.add("stat.empyrean.damage.rogue", "Rogue Damage")
        lang.add("stat.empyrean.max_health", "Health")
        lang.add("stat.empyrean.max_mana", "Mana")
        lang.add("stat.empyrean.mana_regen", "Mana Regeneration")
        lang.add("stat.empyrean.regen", "Regeneration")
        lang.add("stat.empyrean.defense", "Defense")
        lang.add("stat.empyrean.movement_speed", "Movement Speed")
        lang.add("stat.empyrean.flight_speed", "Flight Speed")
        lang.add("stat.empyrean.flight_time", "Flight Time")
        lang.add("stat.empyrean.jump_height", "Jump Height")
        lang.add("stat.empyrean.jump_speed", "Jump Speed")
        lang.add("stat.empyrean.acceleration", "Acceleration")
        lang.add("stat.empyrean.damage_reduction", "Damage Reduction")
        lang.add("stat.empyrean.crit_chance", "Crit Chance")
        lang.add("stat.empyrean.crit_damage", "Crit Damage")
        lang.add("stat.empyrean.knockback", "Knockback")
        lang.add("stat.empyrean.damage_mul", "Damage")
        lang.add("stat.empyrean.mana_cost_mul", "Mana Cost")
        lang.add("stat.empyrean.attack_speed", "Attack Speed")
        // </editor-fold>

        // <editor-fold desc="Tags">
        lang.add("tag.empyrean.material", "Material")
        lang.add("tag.empyrean.volatile", "Volatile")
        // </editor-fold>

        // <editor-fold desc="Prefixes">
        EmpyreanRegistries.PREFIX.keySet().forEach { key ->
            lang.add("prefix.empyrean.${key.path}",
                key.path.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
        }
        // </editor-fold>
    }
    
    private fun generateBlockTranslations(lang: TranslationBuilder) {
        lang.add(EmpyreanBlocks.GEYSERITE_ORE, "Geyserite Ore")
    }
    
    private fun generateItemTranslations(lang: TranslationBuilder) {
        // <editor-fold desc="Wings">
        lang.add(EmpyreanItems.FLEDGLING_WINGS, "Fledgling Wings")
        lang.add(EmpyreanItems.ANGEL_WINGS, "Angel Wings")
        lang.add(EmpyreanItems.INSANE_WINGS, "Insane Wings")
        // </editor-fold>

        // <editor-fold desc="Materials">
        lang.add(EmpyreanItems.RAW_GEYSERITE, "Raw Geyserite")
        lang.add(EmpyreanItems.COSMILIUM_INGOT, "Cosmilium Ingot")
        lang.add(EmpyreanItems.PRECURSOR_ASHES, "Precursor's Ashes")
        // </editor-fold>

        // <editor-fold desc="Weapons">
        lang.add(EmpyreanItems.ASPECT_OF_THE_END, "Aspect of the End")
        // </editor-fold>
    }
}