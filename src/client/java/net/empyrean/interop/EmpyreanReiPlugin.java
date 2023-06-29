package net.empyrean.interop;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import net.empyrean.block.EmpyreanBlocks;
import net.empyrean.interop.rei.AdvancedCraftingCategory;
import net.empyrean.interop.rei.AdvancedShapedCraftingDisplay;
import net.empyrean.interop.rei.AdvancedShapelessCraftingDisplay;
import net.empyrean.interop.rei.EmpyreanReiIds;
import net.empyrean.recipe.AdvancedShapedRecipe;
import net.empyrean.recipe.AdvancedShapelessRecipe;

public class EmpyreanReiPlugin implements REIClientPlugin {
    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerFiller(AdvancedShapelessRecipe.class, AdvancedShapelessCraftingDisplay::new);
        registry.registerFiller(AdvancedShapedRecipe.class, AdvancedShapedCraftingDisplay::new);
    }

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new AdvancedCraftingCategory());
        registry.addWorkstations(EmpyreanReiIds.ADVANCED_CRAFTING, EntryStacks.of(EmpyreanBlocks.ADVANCED_CRAFTING_TABLE));
        registry.addWorkstations(BuiltinPlugin.CRAFTING, EntryStacks.of(EmpyreanBlocks.ADVANCED_CRAFTING_TABLE));
    }
}
