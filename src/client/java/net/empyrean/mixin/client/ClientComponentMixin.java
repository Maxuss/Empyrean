package net.empyrean.mixin.client;

import net.empyrean.chat.EmpyreanStyle;
import net.empyrean.chat.SpecialFormatting;
import net.empyrean.gui.text.color.EmpyreanColors;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Style.class)
public abstract class ClientComponentMixin {
    @Inject(
            method = "getColor",
            at = @At(value = "RETURN"),
            cancellable = true
    )
    public void injectSpecialColor(CallbackInfoReturnable<TextColor> cir) {
        EmpyreanStyle styleHolder = (EmpyreanStyle) this;
        if(styleHolder.getSpecialFormat() == SpecialFormatting.NONE)
            return;
        /* no-op */
        if (styleHolder.getSpecialFormat() == SpecialFormatting.EMPYREAN_L_NAUTICAL) {
            cir.setReturnValue(EmpyreanColors.NAUTICAL);
        }
    }
}
