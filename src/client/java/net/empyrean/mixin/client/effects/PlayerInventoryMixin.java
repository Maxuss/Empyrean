package net.empyrean.mixin.client.effects;

import com.google.common.collect.ImmutableList;
import net.empyrean.config.ClientConfig;
import net.empyrean.gui.util.EffectUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collection;
import java.util.Optional;

import static net.minecraft.client.gui.screens.inventory.AbstractContainerScreen.INVENTORY_LOCATION;

@Mixin(EffectRenderingInventoryScreen.class)
public abstract class PlayerInventoryMixin {
    @Shadow protected abstract void renderIcons(GuiGraphics guiGraphics, int i, int j, Iterable<MobEffectInstance> iterable, boolean bl);

    @SuppressWarnings("SameParameterValue") // idc, abstraction
    private boolean isMouseAround(int x, int y, int width, int height, int mouseX, int mouseY) {
        return (mouseX > x && mouseX < x + width) && (mouseY > y && mouseY < y + height);
    }

    private int renderBg(GuiGraphics guiGraphics, int x, int height, Iterable<MobEffectInstance> iterable, int mouseX, int mouseY) {
        var screen = (AbstractContainerScreen<?>) (Object) this;

        int y = screen.topPos;
        int hoveredIdx = -1;
        int tmpIdx = 0;
        for (MobEffectInstance ignored : iterable) {
            guiGraphics.blit(INVENTORY_LOCATION, x, y, 0, 198, 32, 32);
            if(isMouseAround(x, y, 32, 32, mouseX, mouseY))
                hoveredIdx = tmpIdx;
            y += height;
            tmpIdx++;
        }
        return hoveredIdx;
    }

    private void renderTooltips(GuiGraphics guiGraphics, int x, int height, Iterable<MobEffectInstance> iterable) {
        int y = ((AbstractContainerScreen<?>) (Object) this).topPos;
        for (MobEffectInstance effect : iterable) {
            Component component2 = EffectUtil.formatDuration(effect);
            guiGraphics.drawString(Minecraft.getInstance().font, component2, x + 8, y + 22, 0xFFFFFF);
            y += height;
        }
    }

    @Inject(
            method = "renderEffects",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/inventory/EffectRenderingInventoryScreen;renderBackgrounds(Lnet/minecraft/client/gui/GuiGraphics;IILjava/lang/Iterable;Z)V"
            ),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, CallbackInfo ci, int k, int l, Collection<MobEffectInstance> collection, boolean bl, int m, Iterable<MobEffectInstance> iterable) {
        if(!ClientConfig.getCompactEffectDisplay())
            return;

        int hovered = renderBg(guiGraphics, k, m, iterable, mouseX, mouseY);
        renderIcons(guiGraphics, k, m, iterable, true);
        renderTooltips(guiGraphics, k, m, iterable);

        if(hovered != -1) {
            MobEffectInstance hoveredEffect = ImmutableList.copyOf(iterable).get(hovered);
            guiGraphics.renderTooltip(
                    Minecraft.getInstance().font,
                    EffectUtil.buildTooltip(hoveredEffect), Optional.empty(),
                    mouseX, mouseY
            );
        }

        ci.cancel();
    }
}
