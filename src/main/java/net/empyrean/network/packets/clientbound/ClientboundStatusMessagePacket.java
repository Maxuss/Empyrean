package net.empyrean.network.packets.clientbound;

import net.minecraft.network.chat.Component;

public record ClientboundStatusMessagePacket(Component message, int delayFrames) { }