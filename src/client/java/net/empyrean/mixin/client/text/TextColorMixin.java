package net.empyrean.mixin.client.text;

import net.minecraft.network.chat.TextColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextColor.class)
public class TextColorMixin {
    @Inject(method = "parseColor", at = @At("HEAD"), cancellable = true, require = 1)
    private static void parseColor(String colorString, CallbackInfoReturnable<TextColor> info) {
        if (!colorString.startsWith("#")) {
            return;
        }

        try {
            int i = Integer.parseUnsignedInt(colorString.substring(1), 16);
            info.setReturnValue(TextColor.fromRgb(i));
        } catch (NumberFormatException numberformatexception) {
            info.setReturnValue(null);
        }
    }
}
