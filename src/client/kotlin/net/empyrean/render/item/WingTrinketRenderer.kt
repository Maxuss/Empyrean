package net.empyrean.render.item

import com.mojang.blaze3d.vertex.PoseStack
import dev.emi.trinkets.api.SlotReference
import net.empyrean.render.item.model.WingItemModel
import net.minecraft.client.model.EntityModel
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

class WingTrinketRenderer(base: Item): ComplexTrinketRenderer(base) {
    private var model: WingItemModel? = null

    override fun render(
        stack: ItemStack,
        slotReference: SlotReference,
        contextModel: EntityModel<out LivingEntity>,
        pose: PoseStack,
        buffers: MultiBufferSource,
        light: Int,
        entity: LivingEntity,
        limbAngle: Float,
        limbDistance: Float,
        tickDelta: Float,
        animationProgress: Float,
        headYaw: Float,
        headPitch: Float
    ) {
        // TODO: implement wing renderer?
    }

}