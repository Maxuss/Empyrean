package net.empyrean.block;

import io.wispforest.owo.registration.reflect.BlockRegistryContainer;
import net.empyrean.block.states.AdvancedCraftingTable;
import net.empyrean.item.block.EmpyreanBlockItem;
import net.empyrean.item.rarity.ItemRarity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class EmpyreanBlocks implements BlockRegistryContainer {
    public static final Block GEYSERITE_ORE = new EmpyreanExpBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE_DIAMOND_ORE).requiresTool(), ItemRarity.RARE);

    public static final Block ADVANCED_CRAFTING_TABLE = new AdvancedCraftingTable();

    @Override
    public BlockItem createBlockItem(Block block, String identifier) {
        return new EmpyreanBlockItem(block, ((EmpyreanBlock) block).getItemRarity());
    }
}
