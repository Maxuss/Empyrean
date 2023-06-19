package net.empyrean.util

import net.empyrean.gui.text.StatusMessageRenderer
import net.minecraft.network.chat.Component

fun sendStatusMessage(msg: Component, tickDelay: Int = 2) {
    StatusMessageRenderer.status(msg, tickDelay)
}