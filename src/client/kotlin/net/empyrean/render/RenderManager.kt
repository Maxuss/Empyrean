package net.empyrean.render

import com.mojang.blaze3d.systems.RenderSystem
import net.empyrean.gui.animate.GuiAnimation
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics

object RenderManager: ClientTickEvents.EndTick, HudRenderCallback {
    private val tasks: MutableSet<RenderTask> = mutableSetOf()

    fun enqueue(task: RenderTask) {
        RenderSystem.assertOnRenderThread()
        task.setup()
        tasks.add(task)
    }

    override fun onEndTick(client: Minecraft) {
        tasks.filter { task -> task.tick(); task.age > task.lifetime }.forEach { task ->
            val next = task.next()
            if(next != null)
                enqueue(next)
            tasks.remove(task)
        }
    }

    override fun onHudRender(graphics: GuiGraphics, partialTicks: Float) {
        for(task in tasks) {
            task.render(graphics, partialTicks)
        }
    }
}

abstract class RenderTask(val lifetime: Float) {
    var age: Float = 0f
    private val animations: MutableSet<GuiAnimation> = mutableSetOf()

    fun animation(animation: GuiAnimation) {
        animations.add(animation)
    }

    abstract fun setup()

    abstract fun internalTick()
    abstract fun internalRender(graphics: GuiGraphics, partialTicks: Float)

    open fun next(): RenderTask? = null

    fun tick() {
        if(age > lifetime)
            return
        val filtered = animations.filter { it.tickImmediate(); it.age >= it.lifetime };
        filtered.forEach {
            val next = it.next()
            if(next != null)
                animation(next)
            animations.remove(it)
        }
        age += 1f
        internalTick()
    }

    fun render(graphics: GuiGraphics, partialTicks: Float) {
        if(age > lifetime)
            return
        graphics.pose().pushPose()

        for(animation in animations)
            animation.render(graphics, partialTicks)
        internalRender(graphics, partialTicks)

        graphics.pose().popPose()
    }
}