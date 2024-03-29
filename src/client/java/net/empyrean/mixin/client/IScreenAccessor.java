package net.empyrean.mixin.client;

import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Screen.class)
public interface IScreenAccessor {
    @SuppressWarnings("UnusedReturnValue")
    @Invoker
    <T extends GuiEventListener & Renderable & NarratableEntry> T callAddRenderableWidget(T guiEventListener);
}
