package net.empyrean.effect

import io.wispforest.owo.registration.reflect.FieldRegistrationHandler
import net.empyrean.EmpyreanMod

fun bootstrapEffects() {
    FieldRegistrationHandler.register(EmpyreanEffects::class.java, EmpyreanMod.modId, false)
}