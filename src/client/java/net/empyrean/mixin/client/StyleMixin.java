package net.empyrean.mixin.client;

import net.empyrean.chat.EmpyreanStyle;
import net.empyrean.chat.SpecialFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Style.class)
public abstract class StyleMixin {
    @Inject(
            method = "getColor",
            at = @At(value = "RETURN"),
            cancellable = true
    )
    public void injectSpecialColor(CallbackInfoReturnable<TextColor> cir) {
        EmpyreanStyle styleHolder = (EmpyreanStyle) this;
        if (styleHolder.getSpecialFormat() == SpecialFormatting.NONE)
            return;
        SpecialFormatting format = styleHolder.getSpecialFormat();
        if(format.getSelfColor() != null)
            cir.setReturnValue((TextColor) format.getSelfColor());
    }
}
