package net.empyrean.mixin.client.item;

import net.empyrean.client.text.color.EmpyreanColor;
import net.empyrean.item.EmpyreanItem;
import net.empyrean.util.general.CachedDataHolder;
import net.empyrean.util.item.ItemCachedData;
import net.empyrean.item.rarity.ItemRarity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TooltipRenderUtil.class)
public class TooltipRenderUtilMixin {
    @SuppressWarnings("unchecked")
    @Inject(
            method = "renderFrameGradient",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void renderEmpyreanTooltipBackground(GuiGraphics guiGraphics, int x, int y, int width, int height, int z, int providedStartColor, int providedEndColor, CallbackInfo ci) {
        Screen screen = Minecraft.getInstance().screen;
        if (!(screen instanceof AbstractContainerScreen<?> containerScreen))
            return;
        Slot hovered = containerScreen.hoveredSlot;
        if (hovered == null) // we are probably inside hover event? really weird
            return;
        ItemStack containerStack = hovered.getItem();
        if (containerStack.isEmpty()) // how is it possible? catching edge case though
            return;

        int beginColor;
        int endColor;

        if (containerStack.getItem() instanceof EmpyreanItem empyrean) {
            // we can use special colors!
            ItemRarity rarity = empyrean.getItemRarity();
            beginColor = ((EmpyreanColor) rarity.getColor()).getAltValue();
        } else {
            ItemCachedData cachedData = ((CachedDataHolder<ItemCachedData>) (Object) containerStack).getOrCalculate();
            ItemRarity rarity = cachedData.rarity();
            beginColor = ((EmpyreanColor) rarity.getColor()).getAltValue();
        }

        endColor = beginColor + (64 << 24); // fading out
        beginColor += (0xFF << 24); // fading in

        TooltipRenderUtil.renderVerticalLineGradient(guiGraphics, x, y, height - 2, z, beginColor, endColor);
        TooltipRenderUtil.renderVerticalLineGradient(guiGraphics, x + width - 1, y, height - 2, z, beginColor, endColor);
        TooltipRenderUtil.renderHorizontalLine(guiGraphics, x, y - 1, width, z, beginColor);
        TooltipRenderUtil.renderHorizontalLine(guiGraphics, x, y - 1 + height - 1, width, z, endColor);

        ci.cancel();
    }
}
