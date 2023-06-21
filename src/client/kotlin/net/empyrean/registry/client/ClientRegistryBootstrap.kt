package net.empyrean.registry.client

import net.empyrean.EmpyreanModClient
import net.empyrean.model.wings.GenericEmpyreanWingsModel
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.resources.ResourceLocation

val GENERIC_WINGS = ModelLayerLocation(ResourceLocation(EmpyreanModClient.MODID, "feathered"), "main")

fun bootstrapClientRegistries() {
    EntityModelLayerRegistry.registerModelLayer(GENERIC_WINGS) {
        GenericEmpyreanWingsModel.getTexturedModelData()
    }
}