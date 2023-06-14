package net.empyrean.util.pos

import net.minecraft.core.BlockPos
import net.minecraft.core.Vec3i
import net.minecraft.util.Mth
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.Vec3
import kotlin.math.roundToInt

val Vec3.pos get() = BlockPos(Vec3i(x.roundToInt(), y.roundToInt(), z.roundToInt()))

fun BlockPos.block(level: Level): BlockState = level.getBlockState(this)

fun Vec3.mul(num: Number): Vec3 = this.multiply(num.toDouble(), num.toDouble(), num.toDouble())

val Vec3i.vec get() = Vec3(x.toDouble(), y.toDouble(), z.toDouble())

fun direction(yaw: Float, pitch: Float): Vec3 {
    val x = -Mth.sin(yaw * 0.017453292F) * Mth.cos(pitch * 0.017453292F)
    val y = -Mth.sin(pitch * 0.017453292F)
    val z = Mth.cos(yaw * 0.017453292F) * Mth.cos(pitch * 0.017453292F)
    return Vec3(x.toDouble(), y.toDouble(), z.toDouble()).normalize()
}