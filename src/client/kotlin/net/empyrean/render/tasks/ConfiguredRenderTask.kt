package net.empyrean.render.tasks

import net.empyrean.render.RenderTask
import net.minecraft.client.gui.GuiGraphics

typealias RenderSetupFn = RenderTask.() -> Unit
typealias RenderFn = RenderTask.(GuiGraphics, Float) -> Unit

inline fun renderTask(lifetime: Float, configurator: RenderTaskConfigurator.() -> Unit) = RenderTaskConfigurator(lifetime).apply(configurator).finish()

class ConfiguredRenderTask(
    lifetime: Float,
    val setup: RenderSetupFn, val tick: RenderSetupFn,
    val render: RenderFn,
    val next: RenderTask.() -> RenderTask? = { null },
): RenderTask(lifetime) {
    override fun setup() {
        this.setup(this)
    }

    override fun internalTick() {
        this.tick.invoke(this)
    }

    override fun internalRender(graphics: GuiGraphics, partialTicks: Float) {
        this.render(this, graphics, partialTicks)
    }

    override fun next(): RenderTask? {
        return this.next.invoke(this)
    }
}

class RenderTaskConfigurator(val lifetime: Float) {
    private var setupTask: RenderSetupFn = { }
    private var tickTask: RenderSetupFn = { }
    private var renderTask: RenderFn = { _, _ -> }
    private var next: RenderTask.() -> RenderTask? = { null }

    fun setup(executor: RenderSetupFn) {
        setupTask = executor
    }

    fun tick(executor: RenderSetupFn) {
        tickTask = executor
    }

    fun render(executor: RenderFn) {
        renderTask = executor
    }

    fun next(executor: RenderTask.() -> RenderTask?) {
        this.next = executor
    }

    fun finish() = ConfiguredRenderTask(lifetime, setupTask, tickTask, renderTask, next)
}