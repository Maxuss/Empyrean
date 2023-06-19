package net.empyrean.mixin.client.effects;

import net.empyrean.config.ClientConfig;
import net.empyrean.gui.util.EffectUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Mixin(Gui.class)
public class EffectsGuiMixin {
    @Inject(
            method = "renderEffects",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    public void renderEffects(GuiGraphics guiGraphics, CallbackInfo ci, Collection<?> collection, int i, int j, MobEffectTextureManager mobEffectTextureManager, List<Runnable> list, Iterator<?> var7, MobEffectInstance mobEffectInstance, MobEffect mobEffect, int k, int l, float f, TextureAtlasSprite textureAtlasSprite, int n, int o, float g) {
        if(!ClientConfig.getDisplayEffectLength())
            return; // turned off by client
        list.remove(list.size() - 1); // removing previously submitted task
        list.add(() -> {
            guiGraphics.setColor(1.0f, 1.0f, 1.0f, g);
            guiGraphics.blit(n + 3, o + 3, 0, 18, 18, textureAtlasSprite);
            guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            guiGraphics.drawString(Minecraft.getInstance().font, EffectUtil.formatDuration(mobEffectInstance), n, o + 18, 0xFFFFFFFF);
        });
    }
}
