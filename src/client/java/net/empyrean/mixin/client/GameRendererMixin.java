package net.empyrean.mixin.client;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import net.empyrean.render.effects.BloomRenderer;
import net.empyrean.render.particle.ParticleEngine2D;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @SuppressWarnings("InvalidInjectorMethodSignature") // its valid, you silly plugin
    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;flush()V"
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    public void doRenderParticles(float partialTicks, long nanoTime, boolean renderLevel, CallbackInfo ci, int i, int j, Window window, Matrix4f matrix, PoseStack poseStack, GuiGraphics guiGraphics) {
        if(!renderLevel) // we do not draw particles outside of level
            return;
        ParticleEngine2D.render(guiGraphics, partialTicks);
        BloomRenderer.render(guiGraphics);
    }
}
