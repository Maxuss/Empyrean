package net.empyrean.mixin.client.text;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import net.empyrean.gui.text.StatusMessageRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GameRenderer.class)
public class GuiStatusMessageMixin {
    @SuppressWarnings("InvalidInjectorMethodSignature") // everything is fine actually :p
    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;flush()V"
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    public void renderStatusMessage(float partialTick, long nanoTime, boolean doRenderLevel, CallbackInfo ci, int i, int j, Window window, Matrix4f matrix4f, PoseStack poseStack, GuiGraphics guiGraphics) {
        StatusMessageRenderer.renderBackdrop(guiGraphics, partialTick);
        StatusMessageRenderer.render(guiGraphics, partialTick);
    }
}
