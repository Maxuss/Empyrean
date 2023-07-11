package net.empyrean.render.particle

import net.empyrean.EmpyreanModClient
import net.empyrean.client.text.color.EmpyreanColors
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.FastColor
import kotlin.math.max

class CrystalSparkleParticle(
    x: Float,
    y: Float
) : Particle2D(
    10, // 10 ticks
    x, y,
    ResourceLocation(EmpyreanModClient.MODID, "textures/gui/particles/crystal_sparkle.png"),
    14, 14,
    -1
) {
    override val r: Float
        get() = FastColor.ARGB32.red(EmpyreanColors.STARLIKE.value) / 255f
    override val g: Float
        get() = FastColor.ARGB32.green(EmpyreanColors.STARLIKE.value) / 255f
    override val b: Float
        get() = FastColor.ARGB32.blue(EmpyreanColors.STARLIKE.value) / 255f

    override val alpha: Float
        get() =
            if (age > lifetime / 2) {
                // we should be fading out
                max(0f, 1f - (age.toFloat() / lifetime.toFloat()))
            } else {
                // we should be fading in
                (age.toFloat() / lifetime.toFloat()).coerceIn(0.3f, 0.9f)
            }


    override fun tick() {
        super.tick()
        y -= 1f * (EmpyreanModClient.clientRandom.nextFloat() * 2f).coerceIn(0.8f, 1.5f)
    }
}