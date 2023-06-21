package net.empyrean.model.wings

import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.model.geom.builders.PartDefinition
import net.minecraft.world.entity.LivingEntity


class GenericEmpyreanWingsModel<T: LivingEntity>(root: ModelPart): AbstractWingsModel<T>(root)  {
    private val leftWing01: ModelPart
    private val leftWing02: ModelPart
    private val leftWing03: ModelPart
    private val leftWing04: ModelPart
    private val leftWing05: ModelPart
    private val lFeathers02: ModelPart
    private val boxR1: ModelPart
    private val lFeathers01: ModelPart
    private val boxR2: ModelPart
    private val rightWing01: ModelPart
    private val rightWing02: ModelPart
    private val rightWing03: ModelPart
    private val rightWing04: ModelPart
    private val rightWing05: ModelPart
    private val rFeathers02: ModelPart
    private val boxR3: ModelPart
    private val rFeathers01: ModelPart
    private val boxR4: ModelPart

    init {
        leftWing01 = root.getChild("leftWing").getChild("leftWing01")
        rightWing01 = root.getChild("rightWing").getChild("rightWing01")
        rightWing02 = rightWing01.getChild("rightWing02")
        rFeathers01 = rightWing02.getChild("rFeathers01")
        boxR4 = rFeathers01.getChild("Box_r4")
        rightWing03 = rightWing02.getChild("rightWing03")
        rightWing04 = rightWing03.getChild("rightWing04")
        rFeathers02 = rightWing04.getChild("rFeathers02")
        boxR3 = rFeathers02.getChild("Box_r3")
        rightWing05 = rightWing04.getChild("rightWing05")
        leftWing02 = leftWing01.getChild("leftWing02")
        lFeathers01 = leftWing02.getChild("lFeathers01")
        boxR2 = lFeathers01.getChild("Box_r2")
        leftWing03 = leftWing02.getChild("leftWing03")
        leftWing04 = leftWing03.getChild("leftWing04")
        lFeathers02 = leftWing04.getChild("lFeathers02")
        boxR1 = lFeathers02.getChild("Box_r1")
        leftWing05 = leftWing04.getChild("leftWing05")
    }

    override fun setupAnim(
        entity: T,
        limbSwing: Float,
        limbSwingAmount: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float
    ) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch)
        if (state === WingState.IDLE || state === WingState.CROUCHING) leftWing03.xRot = Math.toRadians(-60.0).toFloat()
        if (state === WingState.FLYING) leftWing03.xRot = Math.toRadians(-32.5).toFloat()
        rightWing03.xRot = leftWing03.xRot
    }

    companion object {
        fun getTexturedModelData(): LayerDefinition {
            val modelData: MeshDefinition = modelData()
            val modelPartData: PartDefinition = modelData.root

            val modelPartData1 = modelPartData.getChild("leftWing").addOrReplaceChild(
                "leftWing01", CubeListBuilder.create().texOffs(0, 0)
                    .addBox(-1.0f, -2.0f, -1.0f, 2.0f, 4.0f, 6.0f),
                PartPose.offsetAndRotation(-6.0f, 0.0f, 0.0f, 0.0f, 1.5708f, 0.436332f)
            )
            val modelPartData2 = modelPartData1.addOrReplaceChild(
                "leftWing02", CubeListBuilder.create().texOffs(0, 47)
                    .addBox(-0.5f, -1.5f, 0.5f, 1.0f, 2.0f, 8.0f),
                PartPose.offsetAndRotation(-0.5f, 0.0f, 3.5f, 0.1309f, 0.3054f, 0.0f)
            )
            val modelPartData3 = modelPartData2.addOrReplaceChild(
                "leftWing03", CubeListBuilder.create().texOffs(39, 0)
                    .addBox(-0.5f, -0.1f, -0.5f, 1.0f, 2.0f, 8.0f),
                PartPose.offsetAndRotation(0.0f, -1.0f, 8.5f, -0.5672f, 0.3054f, 0.0f)
            )
            val modelPartData4 = modelPartData3.addOrReplaceChild(
                "leftWing04", CubeListBuilder.create().texOffs(33, 25)
                    .addBox(-0.7f, -0.2f, -0.5f, 1.0f, 14.0f, 1.0f, true),
                PartPose.offsetAndRotation(0.0f, 0.5f, 7.2f, 1.0908f, 0.0f, 0.0f)
            )
            modelPartData4.addOrReplaceChild(
                "leftWing05", CubeListBuilder.create().texOffs(0, 13)
                    .addBox(0.4f, -4.0f, -12.3f, 0.0f, 20.0f, 13.0f, true),
                PartPose.offset(-0.5f, 4.8f, -0.2f)
            )
            val modelPartData5 = modelPartData4.addOrReplaceChild(
                "lFeathers02", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0f, -3.5f, -3.2f, 0.0f, 0.0f, 0.0873f)
            )
            modelPartData5.addOrReplaceChild(
                "Box_r1", CubeListBuilder.create().texOffs(26, 26)
                    .addBox(0.0f, -6.6f, -13.8f, 1.0f, 14.0f, 14.0f, true),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.48f, 0.0f, 0.0f)
            )
            val modelPartData6 = modelPartData2.addOrReplaceChild(
                "lFeathers01", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.6f, 1.3f, 1.5f, -0.1745f, -0.0873f, 0.0f)
            )
            modelPartData6.addOrReplaceChild(
                "Box_r2", CubeListBuilder.create().texOffs(0, 0)
                    .addBox(-0.5f, -0.8f, -8.1f, 1.0f, 10.0f, 16.0f, true),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.1745f, 0.0f, 0.0f)
            )
            val modelPartData7 = modelPartData.getChild("rightWing").addOrReplaceChild(
                "rightWing01", CubeListBuilder.create().texOffs(0, 0)
                    .addBox(-1.0f, -2.0f, -1.0f, 2.0f, 4.0f, 6.0f, true),
                PartPose.offsetAndRotation(6.0f, 0.0f, 0.0f, 0.0f, -1.5708f, -0.436332f)
            )
            val modelPartData8 = modelPartData7.addOrReplaceChild(
                "rightWing02", CubeListBuilder.create().texOffs(0, 47)
                    .addBox(-0.5f, -1.5f, 0.5f, 1.0f, 2.0f, 8.0f, true),
                PartPose.offsetAndRotation(0.5f, 0.0f, 3.5f, 0.1309f, -0.3054f, 0.0f)
            )
            val modelPartData9 = modelPartData8.addOrReplaceChild(
                "rightWing03", CubeListBuilder.create().texOffs(39, 0)
                    .addBox(-0.5f, -0.1f, -0.5f, 1.0f, 2.0f, 8.0f, true),
                PartPose.offsetAndRotation(0.0f, -1.0f, 8.5f, -0.5672f, -0.3054f, 0.0f)
            )
            val modelPartData10 = modelPartData9.addOrReplaceChild(
                "rightWing04", CubeListBuilder.create().texOffs(33, 25)
                    .addBox(-0.3f, -0.2f, -0.5f, 1.0f, 14.0f, 1.0f),
                PartPose.offsetAndRotation(0.0f, 0.5f, 7.2f, 1.0908f, 0.0f, 0.0f)
            )
            modelPartData10.addOrReplaceChild(
                "rightWing05", CubeListBuilder.create().texOffs(0, 13)
                    .addBox(-0.4f, -4.0f, -12.3f, 0.0f, 20.0f, 13.0f),
                PartPose.offset(0.5f, 4.8f, -0.2f)
            )
            val modelPartData11 = modelPartData10.addOrReplaceChild(
                "rFeathers02", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0f, -3.5f, -3.2f, 0.0f, 0.0f, -0.0873f)
            )
            modelPartData11.addOrReplaceChild(
                "Box_r3", CubeListBuilder.create().texOffs(26, 26)
                    .addBox(-1.0f, -6.6f, -13.8f, 1.0f, 14.0f, 14.0f),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.48f, 0.0f, 0.0f)
            )
            val modelPartData12 = modelPartData8.addOrReplaceChild(
                "rFeathers01", CubeListBuilder.create(),
                PartPose.offsetAndRotation(-0.6f, 1.3f, 1.5f, -0.1745f, 0.0873f, 0.0f)
            )
            modelPartData12.addOrReplaceChild(
                "Box_r4", CubeListBuilder.create().texOffs(0, 0)
                    .addBox(-0.5f, -0.8f, -8.1f, 1.0f, 10.0f, 16.0f),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.1745f, 0.0f, 0.0f)
            )
            return LayerDefinition.create(modelData, 64, 64)
        }
    }
}