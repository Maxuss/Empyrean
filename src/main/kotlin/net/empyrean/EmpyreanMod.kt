package net.empyrean

import io.wispforest.owo.registration.reflect.FieldRegistrationHandler
import net.empyrean.block.bootstrapBlocks
import net.empyrean.commands.bootstrapCommands
import net.empyrean.effect.bootstrapEffects
import net.empyrean.events.bootstrapEvents
import net.empyrean.game.GameManager
import net.empyrean.item.bootstrapItems
import net.empyrean.network.bootstrapNetworking
import net.empyrean.recipe.AdvancedShapedRecipe
import net.empyrean.recipe.AdvancedShapelessRecipe
import net.empyrean.recipe.ser.StackedIngredientSerializer
import net.empyrean.sound.EmpyreanSounds
import net.empyrean.worldgen.bootstrapOrePlacement
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.util.RandomSource
import org.slf4j.LoggerFactory
import java.util.concurrent.ThreadLocalRandom

object EmpyreanMod : ModInitializer {
    private val logger = LoggerFactory.getLogger("empyrean")
    const val modId: String = "empyrean"

    val serverRandom = kotlin.random.Random(ThreadLocalRandom.current().nextInt())
    val serverRandomSource = RandomSource.createNewThreadLocalInstance()

    override fun onInitialize() {
        preloadClasses()

        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, "empyrean:advanced_shaped",
            AdvancedShapedRecipe.Serializer)
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, "empyrean:advanced_shapeless",
            AdvancedShapelessRecipe.Serializer)

        CustomIngredientSerializer.register(StackedIngredientSerializer)

        FieldRegistrationHandler.register(EmpyreanSounds::class.java, modId, true)

        bootstrapItems()
        bootstrapBlocks()
        bootstrapCommands()
        bootstrapNetworking()
        bootstrapEvents()
        bootstrapOrePlacement()
        bootstrapEffects()

        GameManager.init()
    }

    /**
     * Preloads necessary classes
      */
    private fun preloadClasses() {
        Class.forName("net.empyrean.registry.EmpyreanRegistries")
        Class.forName("net.empyrean.item.prefix.EmpyreanPrefixes")
        Class.forName("net.empyrean.menu.EmpyreanMenu")
    }
}