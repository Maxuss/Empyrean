package net.empyrean.item

import io.wispforest.owo.registration.reflect.FieldRegistrationHandler
import net.empyrean.EmpyreanMod
import net.empyrean.registry.EmpyreanItems

fun bootstrapItems() {
    FieldRegistrationHandler.register(EmpyreanItems::class.java, EmpyreanMod.modId, false)
}