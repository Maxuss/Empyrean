package net.empyrean.mixin.craft;

import net.empyrean.recipe.EmpyreanRecipes;
import net.empyrean.recipe.ser.StackedIngredient;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ResultSlot.class)
public class ResultSlotMixin {
    @Shadow @Final private CraftingContainer craftSlots;

    @Redirect(
            method = "onTake",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/crafting/RecipeManager;getRemainingItemsFor(Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/world/Container;Lnet/minecraft/world/level/Level;)Lnet/minecraft/core/NonNullList;")
    )
    public <C extends Container, T extends Recipe<C>> NonNullList<ItemStack> injectAdvancedRecipeType(RecipeManager instance, RecipeType<T> recipeType, C inventory, Level level) {
        if(recipeType.equals(RecipeType.CRAFTING)) {
            var optional = instance.getRemainingItemsFor(EmpyreanRecipes.AdvancedCrafting.INSTANCE, (CraftingContainer) inventory, level);
            var original = instance.getRemainingItemsFor(recipeType, inventory, level);
            return optional.isEmpty() ? original : optional;
        } else {
            return instance.getRemainingItemsFor(recipeType, inventory, level);
        }
    }

    @SuppressWarnings({"ConstantValue", "UnstableApiUsage"}) // idc that its unstable, i need to use it :3
    @Inject(
            method = "onTake",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;size()I", shift = At.Shift.BEFORE),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    public void modifyStackSize(Player player, ItemStack stack, CallbackInfo ci, NonNullList<ItemStack> nonNullList) {
        var advancedRecipe = player.level().getRecipeManager().getRecipeFor(EmpyreanRecipes.AdvancedCrafting.INSTANCE, craftSlots, player.level());
        if(advancedRecipe.isEmpty())
            return;
        var ingredients = advancedRecipe.get().getIngredients();
        for (int i = 0; i < nonNullList.size(); ++i) {
            ItemStack itemStack = this.craftSlots.getItem(i);
            ItemStack itemStack2 = nonNullList.get(i);
            var ingredient = ingredients.get(i).getCustomIngredient();
            int removeCount = 1;
            if (ingredient instanceof StackedIngredient stacked) {
                removeCount = stacked.getCount$empyrean();
            }
            if (!itemStack.isEmpty()) {
                this.craftSlots.removeItem(i, removeCount);
                itemStack = this.craftSlots.getItem(i);
            }
            if (itemStack2.isEmpty()) continue;
            if (itemStack.isEmpty()) {
                this.craftSlots.setItem(i, itemStack2);
                continue;
            }
            if (ItemStack.isSameItemSameTags(itemStack, itemStack2)) {
                itemStack2.grow(itemStack.getCount());
                this.craftSlots.setItem(i, itemStack2);
                continue;
            }
            if (player.getInventory().add(itemStack2)) continue;
            player.drop(itemStack2, false);
        }
        ci.cancel();
    }
}