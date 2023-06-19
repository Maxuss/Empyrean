package net.empyrean.mixin.client.menu;

import net.empyrean.config.ClientConfig;
import net.empyrean.gui.button.DisabledButton;
import net.empyrean.mixin.client.IScreenAccessor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(TitleScreen.class)
public class MainMenuScreenMixin {
    @Redirect(
            slice = @Slice(to = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/TitleScreen;getMultiplayerDisabledReason()Lnet/minecraft/network/chat/Component;")),
            method = "createNormalMenuOptions",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/TitleScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;"
            )
    )
    private GuiEventListener disableSingleplayerButton(TitleScreen instance, GuiEventListener guiEventListener) {
        if(ClientConfig.getEnableSingleplayer()) {
            ((IScreenAccessor) instance).callAddRenderableWidget((Button) guiEventListener);
            return guiEventListener;
        }
        Button btn = (Button) guiEventListener;
        DisabledButton disabled = new DisabledButton(
                btn.getX(), btn.getY(),
                btn.getWidth(), btn.getHeight(),
                btn.getMessage(),
                Tooltip.create(Component.literal("Singleplayer is not supported in Empyrean! Please join a server instead."))
        );
        ((IScreenAccessor) instance).callAddRenderableWidget(disabled);
        return btn;
    }
}
