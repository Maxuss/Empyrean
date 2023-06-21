package net.empyrean.render.feature

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import dev.emi.trinkets.api.TrinketComponent
import dev.emi.trinkets.api.TrinketsApi
import net.empyrean.EmpyreanModClient
import net.empyrean.item.impl.wings.EmpyreanWings
import net.empyrean.model.wings.AbstractWingsModel
import net.empyrean.model.wings.GenericEmpyreanWingsModel
import net.empyrean.registry.client.GENERIC_WINGS
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.geom.EntityModelSet
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.ItemRenderer
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.layers.RenderLayer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import java.util.*


class WingFeatureRenderer<T: LivingEntity, M: EntityModel<T>>(parent: RenderLayerParent<T, M>, loader: EntityModelSet): RenderLayer<T, M>(parent) {
    private val baseModel = GenericEmpyreanWingsModel<T>(loader.bakeLayer(GENERIC_WINGS))

    override fun render(
        poses: PoseStack,
        buffer: MultiBufferSource,
        packedLight: Int,
        entity: T,
        limbSwing: Float,
        limbSwingAmount: Float,
        partialTick: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float
    ) {
        if (entity is Player) {
            val component: Optional<TrinketComponent> = TrinketsApi.getTrinketComponent(entity)
            component.ifPresent { trinketComponent ->
                trinketComponent.allEquipped.forEach { pair ->
                    val stack: ItemStack = pair.b
                    val item = stack.item
                    if (item is EmpyreanWings) {
                        // TODO: colors?
//                        val primaryColour: FloatArray = item.getPrimaryColour().getColorComponents()
//                        val secondaryColour: FloatArray = wingItem.getSecondaryColour().getColorComponents()
//                        val r1 = primaryColour[0]
//                        val g1 = primaryColour[1]
//                        val b1 = primaryColour[2]
//                        val r2 = secondaryColour[0]
//                        val g2 = secondaryColour[1]
//                        val b2 = secondaryColour[2]
                        val wingModel = baseModel
                        val layer1 = ResourceLocation(EmpyreanModClient.MODID, "textures/entity/featheredd_wings.png")
                        val layer2 = ResourceLocation(EmpyreanModClient.MODID, "textures/entity/featheredd_wings_2.png")
                        poses.pushPose()
                        poses.translate(0.0, 0.0, 0.125)
                            this.parentModel
                        parentModel.copyPropertiesTo(wingModel)
                        wingModel.setupAnim(entity, limbSwing, limbSwingAmount, partialTick, netHeadYaw, headPitch)
                        this.renderWings(
                            wingModel,
                            poses,
                            buffer,
                            stack,
                            RenderType.entityTranslucent(layer2),
                            packedLight,
                            1f,
                            1f,
                            1f
                        )
                        renderWings(
                            wingModel,
                            poses,
                            buffer,
                            stack,
                            RenderType.entityTranslucent(layer1),
                            packedLight,
                            1f,
                            1f,
                            1f
                        )
                        poses.popPose()
                    }
                }
            }
        }
    }

    fun renderWings(
        model: AbstractWingsModel<T>,
        poses: PoseStack,
        buffer: MultiBufferSource,
        stack: ItemStack?,
        renderType: RenderType,
        light: Int,
        r: Float,
        g: Float,
        b: Float
    ) {
        val vertexConsumer: VertexConsumer =
            ItemRenderer.getArmorFoilBuffer(buffer, renderType, false, stack?.isEnchanted == true)
        model.renderToBuffer(poses, vertexConsumer, light, OverlayTexture.NO_OVERLAY, r, g, b, 1f)
    }
}