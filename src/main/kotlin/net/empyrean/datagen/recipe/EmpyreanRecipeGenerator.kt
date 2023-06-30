package net.empyrean.datagen.recipe

import com.google.gson.JsonObject
import net.empyrean.recipe.ser.StackedIngredient
import net.empyrean.registry.EmpyreanItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.recipes.*
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.ItemLike
import java.util.function.Consumer

class EmpyreanRecipeGenerator(out: FabricDataOutput): FabricRecipeProvider(out) {
    override fun buildRecipes(exporter: Consumer<FinishedRecipe>) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, EmpyreanItems.ASPECT_OF_THE_END)
            .pattern("#")
            .pattern("#")
            .define('#', stacked(Items.DIAMOND, 32))
            .pattern("I")
            .define('I', Items.STICK)
            .showNotification(false)
            .unlockedBy(RecipeProvider.getHasName(EmpyreanItems.ASPECT_OF_THE_END), RecipeProvider.has(EmpyreanItems.ASPECT_OF_THE_END))
            .asAdvanced(exporter)
    }
}

fun stacked(item: ItemLike, count: Int): Ingredient = StackedIngredient(Ingredient.of(item), count).toVanilla()

fun ShapedRecipeBuilder.asAdvanced(exporter: Consumer<FinishedRecipe>) {
    this.save { recipe ->
        exporter.accept(AdvancedShapedRecipe(recipe))
    }
}

fun ShapelessRecipeBuilder.asAdvanced(exporter: Consumer<FinishedRecipe>) {
    this.save { recipe ->
        exporter.accept(AdvancedShapelessRecipe(recipe))
    }
}

abstract class AdvancedDelegatingRecipe(val inner: FinishedRecipe): FinishedRecipe {
    abstract val id: String

    override fun serializeRecipeData(json: JsonObject) {
        inner.serializeRecipeData(json)
        json.addProperty("type", id)
    }

    override fun getId(): ResourceLocation {
        return inner.id
    }

    override fun getType(): RecipeSerializer<*> {
        return inner.type
    }

    override fun serializeAdvancement(): JsonObject? {
        return inner.serializeAdvancement()
    }

    override fun getAdvancementId(): ResourceLocation? {
        return inner.advancementId
    }
}

class AdvancedShapedRecipe(inner: FinishedRecipe): AdvancedDelegatingRecipe(inner) {
    override val id: String = "empyrean:advanced_shaped"
}

class AdvancedShapelessRecipe(inner: FinishedRecipe): AdvancedDelegatingRecipe(inner) {
    override val id: String = "empyrean:advanced_shapeless"
}