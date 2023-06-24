package net.empyrean.block

import io.wispforest.owo.registration.reflect.FieldRegistrationHandler
import net.empyrean.EmpyreanMod

fun bootstrapBlocks() {
    FieldRegistrationHandler.register(EmpyreanBlocks::class.java, EmpyreanMod.modId, false)
}