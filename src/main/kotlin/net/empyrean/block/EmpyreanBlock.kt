package net.empyrean.block

import net.empyrean.item.rarity.ItemRarity
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.DropExperienceBlock

interface EmpyreanBlock {
    val itemRarity: ItemRarity
}

class SimpleEmpyreanBlock(properties: Properties, override val itemRarity: ItemRarity): Block(properties), EmpyreanBlock

class EmpyreanExpBlock(properties: Properties, override val itemRarity: ItemRarity): DropExperienceBlock(properties, UniformInt.of(4, 9)), EmpyreanBlock