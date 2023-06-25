package net.empyrean.item.prefix

import kotlinx.serialization.Serializable
import net.empyrean.item.kind.ItemKind
import net.empyrean.player.Stats
import net.empyrean.registry.EmpyreanRegistries
import net.empyrean.util.serde.RegistrySerializer

@Serializable(with = PrefixSerializer::class)
open class Prefix(
    val applicableTo: Array<ItemKind>,
    val baseStats: Stats,
    val rarityMultiplier: Float = 0f // no additional multiplier
) {
    val translationKey: String get() = "prefix.empyrean.${EmpyreanRegistries.PREFIX.getKey(this)?.path}"
}

object PrefixSerializer: RegistrySerializer<Prefix>(EmpyreanRegistries.PREFIX)