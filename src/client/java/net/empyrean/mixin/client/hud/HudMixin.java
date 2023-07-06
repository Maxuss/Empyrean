package net.empyrean.mixin.client.hud;

import net.empyrean.gui.hud.AdrenalineRenderer;
import net.empyrean.gui.hud.ManaRenderer;
import net.empyrean.util.JInterop;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(Gui.class)
public abstract class HudMixin {
    @Shadow private int screenWidth;

    @Shadow private int screenHeight;

    @Shadow private int tickCount;

    @Shadow @Final private Minecraft minecraft;

    @Redirect(
            method = "renderPlayerHealth",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V",
                    ordinal = 6
            )
    )
    public void relocateAirBubbles1(GuiGraphics instance, ResourceLocation resourceLocation, int x, int y, int k, int l, int m, int n) {
        instance.blit(resourceLocation, x, y - 9, k, l, m, n);
    }

    @Redirect(
            method = "renderPlayerHealth",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V",
                    ordinal = 7
            )
    )
    public void relocateAirBubbles2(GuiGraphics instance, ResourceLocation resourceLocation, int x, int y, int k, int l, int m, int n) {
        instance.blit(resourceLocation, x, y - 9, k, l, m, n);
    }

    @Inject(
            method = "renderPlayerHealth",
            at = @At("TAIL")
    )
    public void renderMana(GuiGraphics graphics, CallbackInfo ci) {
        int xBegin = screenWidth / 2 + 9;
        int yBegin = screenHeight - 39 - 10;
        ManaRenderer.renderMana(tickCount, Objects.requireNonNull(Minecraft.getInstance().player), graphics, xBegin, yBegin);
    }

    /**
     * @author maxus
     * @reason Render adrenaline bar alongside the experience bar
     */
    @Inject(
            method = "renderExperienceBar",
            at = @At("HEAD"),
            cancellable = true
    )
    public void renderExperienceBar(GuiGraphics graphics, int x, CallbackInfo ci) {
        boolean renderBar = AdrenalineRenderer.shouldRender();
        if(renderBar) {
            AdrenalineRenderer.renderAdrenaline(tickCount, JInterop.getPlayerData(minecraft.player).getAdrenalineLevel(), graphics, x);
            ci.cancel();
        }
    }
}
