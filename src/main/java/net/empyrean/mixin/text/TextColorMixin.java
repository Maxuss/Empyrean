package net.empyrean.mixin.text;

import net.empyrean.gui.text.color.EmpyreanColor;
import net.empyrean.gui.text.color.LerpingColor;
import net.minecraft.network.chat.TextColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextColor.class)
public abstract class TextColorMixin implements EmpyreanColor {
    private static final String EMPYREAN_COLOR_PREFIX = "<E";

    @Inject(
            method = "parseColor",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void injectEmpyreanColorDecoder(String hexString, CallbackInfoReturnable<TextColor> cir) {
        if (hexString.startsWith(EMPYREAN_COLOR_PREFIX)) {
            String operated = hexString.substring(2);
            if (operated.startsWith(":L")) {
                cir.setReturnValue(LerpingColor.parse(operated.substring(3)));
            }
        }
    }

    @Shadow
    public abstract int getValue();

    @Override
    public int getColorValue() {
        return ((TextColor) (Object) this).getValue();
    }

    @Override
    public int getAltValue() {
        return getValue();
    }
}
