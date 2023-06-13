package net.empyrean.mixin.client;

import net.empyrean.gui.util.EmpyreanResources;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(LogoRenderer.class)
public class LogoRendererMixin {
    @Redirect(
            method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"
            ),
            slice = @Slice(
                    to = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/components/LogoRenderer;MINECRAFT_EDITION:Lnet/minecraft/resources/ResourceLocation;")
            )
    )
    public void renderMacrocosmLogo(GuiGraphics instance, ResourceLocation resourceLocation, int x, int y, float f, float g, int k, int l, int m, int n) {
        instance.blit(EmpyreanResources.EMPYREAN_LOGO, x, y, f, g, k, 64, m, n);
    }
}
