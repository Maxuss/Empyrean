package net.empyrean.block

import net.empyrean.item.rarity.ItemRarity
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.DropExperienceBlock

interface EmpyreanBlock {
    val itemRarity: ItemRarity
}

open class SimpleEmpyreanBlock(properties: Properties, override val itemRarity: ItemRarity): Block(properties), EmpyreanBlock

open class EmpyreanExpBlock(properties: Properties, override val itemRarity: ItemRarity): DropExperienceBlock(properties, UniformInt.of(4, 9)), EmpyreanBlock