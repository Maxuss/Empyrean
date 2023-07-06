package net.empyrean.key

import com.mojang.blaze3d.platform.InputConstants
import net.empyrean.components.data
import net.empyrean.network.EmpyreanNetworking
import net.empyrean.network.packets.serverbound.ServerboundPlayerActionPacket
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft
import org.lwjgl.glfw.GLFW

object EmpyreanKeybinds: ClientTickEvents.EndTick {
    val ADRENALINE_DISCHARGE = KeyBindingHelper.registerKeyBinding(KeyMapping(
        "key.empyrean.adrenaline_discharge",
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_V,
        "category.empyrean.keybinds"
    ))

    override fun onEndTick(client: Minecraft) {
        val player = client.player ?: return
        if(ADRENALINE_DISCHARGE.consumeClick() && player.data.adrenalineLevel >= 1f) {
            EmpyreanNetworking.EMPYREAN_CHANNEL.clientHandle().send(ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.ACTIVATE_ADRENALINE))
        }
    }
}