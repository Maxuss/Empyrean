package net.empyrean.mixin.client.effects;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import net.empyrean.config.ClientConfig;
import net.empyrean.gui.util.EffectUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collection;
import java.util.List;

import static net.minecraft.client.gui.screens.inventory.AbstractContainerScreen.INVENTORY_LOCATION;

@Mixin(EffectRenderingInventoryScreen.class)
public abstract class EffectRenderingScreenMixin {
    @SuppressWarnings("SameParameterValue") // idc, abstraction
    private boolean isMouseAround(int x, int y, int width, int height, int mouseX, int mouseY) {
        return (mouseX > x && mouseX < x + width) && (mouseY > y && mouseY < y + height);
    }

    private void renderEffectIcons(
            GuiGraphics guiGraphics,
            int x, int height,
            Iterable<MobEffectInstance> iterable
    ) {
        var screen = (AbstractContainerScreen<?>) (Object) this;

        int topY = screen.topPos;
        int y = topY;

        MobEffectTextureManager mobEffectTextureManager = Minecraft.getInstance().getMobEffectTextures();
        for (MobEffectInstance mobEffectInstance : iterable) {
            MobEffect mobEffect = mobEffectInstance.getEffect();
            TextureAtlasSprite textureAtlasSprite = mobEffectTextureManager.get(mobEffect);
            guiGraphics.blit(x + 7, y + 7, 0, 18, 18, textureAtlasSprite);
            y += height;
            if(y >= topY + 166) {
                // we have reached the bottom of the screen
                // shift x forward
                y = topY;
                x += 34;
            }
        }
    }

    private int renderBg(GuiGraphics guiGraphics, int x, int height, Iterable<MobEffectInstance> iterable, int mouseX, int mouseY) {
        var screen = (AbstractContainerScreen<?>) (Object) this;

        int topY = screen.topPos;

        int y = topY;
        int hoveredIdx = -1;
        int tmpIdx = 0;
        for (MobEffectInstance ignored : iterable) {
            guiGraphics.blit(INVENTORY_LOCATION, x, y, 0, 198, 32, 32);
            if(isMouseAround(x, y, 32, 32, mouseX, mouseY))
                hoveredIdx = tmpIdx;
            y += height;
            if(y >= topY + 166) {
                // we have reached the bottom of the screen
                // shift x forward
                y = topY;
                x += 34;
            }
            tmpIdx++;
        }
        return hoveredIdx;
    }

    private void renderTooltips(GuiGraphics guiGraphics, int x, int height, Iterable<MobEffectInstance> iterable) {
        int topY = ((AbstractContainerScreen<?>) (Object) this).topPos;
        int y = topY;

        for (MobEffectInstance effect : iterable) {
            Component component2 = EffectUtil.formatDuration(effect);
            guiGraphics.drawString(Minecraft.getInstance().font, component2, x + 8, y + 22, 0xFFFFFF);
            y += height;
            if(y >= topY + 166) {
                // shifting x
                y = topY;
                x += 34;
            }
        }
    }

    @Inject(
            method = "renderEffects",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Collection;size()I",
                    ordinal = 0
            ),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, CallbackInfo ci, int k, int l, Collection<MobEffectInstance> collection, boolean bl, int m) {
        if(!ClientConfig.getCompactEffectDisplay())
            return;

        List<MobEffectInstance> iterable = Ordering.natural().sortedCopy(collection);

        int hovered = renderBg(guiGraphics, k, m, iterable, mouseX, mouseY);
        renderEffectIcons(guiGraphics, k, m, iterable);
        renderTooltips(guiGraphics, k, m, iterable);

        if(hovered != -1) {
            MobEffectInstance hoveredEffect = ImmutableList.copyOf(iterable).get(hovered);
            guiGraphics.renderTooltip(
                    Minecraft.getInstance().font,
                    EffectUtil.buildTooltip(hoveredEffect),
                    mouseX, mouseY
            );
        }

        ci.cancel();
    }
}
