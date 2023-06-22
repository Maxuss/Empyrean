package net.empyrean.render.particle

import net.empyrean.EmpyreanModClient
import net.empyrean.gui.text.color.EmpyreanColors
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.FastColor
import kotlin.math.max

class OutlinedParticle(
    x: Float,
    y: Float
) : Particle2D(
    16,
    x, y,
    ResourceLocation(EmpyreanModClient.MODID, "textures/gui/particles/outlined.png"),
    16, 16,
    -1
) {
    override val r: Float
        get() = FastColor.ARGB32.red(EmpyreanColors.CINDER.altValue) / 255f
    override val g: Float
        get() = FastColor.ARGB32.green(EmpyreanColors.CINDER.altValue) / 255f
    override val b: Float
        get() = FastColor.ARGB32.blue(EmpyreanColors.CINDER.altValue) / 255f
    override val alpha: Float
        get() =
            if (age > lifetime / 2) {
                // we should be fading out
                max(0f, 1f - (age.toFloat() / lifetime.toFloat()))
            } else {
                // we should be fading in
                (age.toFloat() / lifetime.toFloat()).coerceIn(0.2f, 0.9f)
            }

    override fun tick() {
        super.tick()
        y += 1f
    }
}