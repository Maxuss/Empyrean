package net.empyrean.commands

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.empyrean.commands.api.EmpyreanArgBuilder
import net.empyrean.gui.animate.Interpolation
import net.empyrean.gui.animate.SlideInAnimation
import net.empyrean.render.RenderManager
import net.empyrean.render.tasks.renderTask
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.Minecraft

inline fun commandClient(
    name: String,
    initializer: EmpyreanArgBuilder<FabricClientCommandSource, LiteralArgumentBuilder<FabricClientCommandSource>>.() -> Unit
): LiteralArgumentBuilder<FabricClientCommandSource> {
    val command = EmpyreanArgBuilder(ClientCommandManager.literal(name))
    command.initializer()
    command.finalize()
    return command.arg
}

fun bootstrapClientCommands() {
    ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
        dispatcher.register(commandClient("empyreanClient") {
            "slide" runs {
                RenderManager.enqueue(renderTask(80f) {
                    val font = Minecraft.getInstance().font

                    setup {
                        animation(SlideInAnimation(40f, 40f, 4f, Interpolation.COSINE, true))
                    }

                    render { graphics, _ ->
                        graphics.drawString(font, "Hello, World!", 0, 0, 0xFFFFFF)
                    }
                })
            }
            "interpolate" runs {
                RenderManager.enqueue(renderTask(80f) {
                    val font = Minecraft.getInstance().font
                    val inter = Interpolation.SINE

                    render { graphics, _ ->
                        val x = inter(age / lifetime) * 20f
                        val y = inter(age / lifetime) * 20f

                        graphics.pose().translate(x, y, 0f)

                        graphics.drawString(font, "Hello!", 0, 0, 0xFFFFFF)
                    }
                })
            }
        })
    }
}