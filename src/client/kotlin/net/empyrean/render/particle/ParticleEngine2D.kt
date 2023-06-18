package net.empyrean.render.particle

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics

object ParticleEngine2D {
    private val particles: MutableList<Particle2D> = mutableListOf()
    private val active: Boolean get() = !Minecraft.getInstance().isPaused

    @JvmStatic
    fun tick() {
        if(active)
            particles.removeIf {
                it.tick()
                !it.isAlive
            }
    }

    @JvmStatic
    fun render(graphics: GuiGraphics, partialTicks: Float) {
        if(particles.isEmpty() || !active) // don't render if there's nothing to render or if the game is paused
            return
        Minecraft.getInstance().profiler.push("empyrean:particle2d") // Profiling

        graphics.pose().pushPose()

        graphics.pose().translate(0f, 0f, 50f)

        RenderSystem.enableBlend()
        RenderSystem.disableDepthTest()

        particles.forEach { it.render(graphics, partialTicks) }

        graphics.pose().popPose()

        Minecraft.getInstance().profiler.pop()
    }

    @JvmStatic
    fun spawn(particle: Particle2D) {
        if(active)
            particles.add(particle)
    }
}