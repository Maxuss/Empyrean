package net.empyrean.effect

import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory

abstract class EmpyreanEffect(category: MobEffectCategory, particleColor: Int) : MobEffect(category, particleColor)