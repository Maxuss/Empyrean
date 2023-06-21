package net.empyrean.model.wings

import com.google.common.collect.ImmutableList
import net.minecraft.client.model.AgeableListModel
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.player.AbstractClientPlayer
import net.minecraft.util.Mth
import net.minecraft.world.entity.LivingEntity
import kotlin.math.pow


abstract class AbstractWingsModel<T: LivingEntity>(part: ModelPart) : AgeableListModel<T>() {
    protected val rightWing: ModelPart = part.getChild("rightWing")
    protected val leftWing: ModelPart = part.getChild("leftWing")
    protected var state: WingState = WingState.IDLE

    companion object {
        fun modelData(): MeshDefinition {
            val md = MeshDefinition()
            val root = md.root

            root.addOrReplaceChild("rightWing", CubeListBuilder.create(), PartPose.rotation(0f, 5f, 0f))
            root.addOrReplaceChild("leftWing", CubeListBuilder.create(), PartPose.rotation(0f, 5f, 0f))

            return md
        }
    }

    override fun setupAnim(
        entity: T,
        limbSwing: Float,
        limbSwingAmount: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float
    ) {
        state = WingState.IDLE
        var a = 0.125f
        var b = 0.1f
        var k = 0.4f
        var l = -0.5f
        var m = -1.0f
        var n = 0.0f

        if (entity.isFallFlying) {
            state = WingState.FLYING
            var o = 1.0f
            val vec3d = entity.deltaMovement
            if (vec3d.y < 0.0) {
                val vec3d2 = vec3d.normalize()
                o = 1.0f - (-vec3d2.y).pow(1.5).toFloat()
            }
            k = o * 0.35f + (1.0f - o) * k
            l = o * -1.6f + (0.3f - o) * l
            // WHY THE HELL IS FORWARD SPEED NAMED ZZA???
            if (entity.zza > 0) {
                a = 0.4f
                b = 1.0f
            }
        } else if (entity.isCrouching) {
            state = WingState.CROUCHING
            // shift position down
            k = 0.7f
            m = 0.0f
            n = 0.09f
        }

        k += Mth.sin(entity.tickCount * a) * b
        leftWing.x = 7.0f
        leftWing.y = m

        if (entity is AbstractClientPlayer) {
            entity.elytraRotX = entity.elytraRotX + (k - entity.elytraRotX) * 0.1f
            entity.elytraRotY = entity.elytraRotY + (n - entity.elytraRotY) * 0.1f
            entity.elytraRotZ = entity.elytraRotZ + (l - entity.elytraRotZ) * 0.1f
            leftWing.xRot = entity.elytraRotX
            leftWing.yRot = entity.elytraRotY
            leftWing.zRot = entity.elytraRotZ
        } else {
            leftWing.xRot = k
            leftWing.yRot = l
            leftWing.zRot = n
        }

        rightWing.x = -leftWing.x
        rightWing.yRot = -leftWing.yRot
        rightWing.y = leftWing.y
        rightWing.xRot = leftWing.xRot
        rightWing.zRot = -leftWing.zRot
    }

    override fun headParts(): MutableIterable<ModelPart> {
        return ImmutableList.of()
    }

    override fun bodyParts(): MutableIterable<ModelPart> {
        return ImmutableList.of(rightWing, leftWing)
    }

    enum class WingState {
        IDLE,
        CROUCHING,
        FLYING
    }

}