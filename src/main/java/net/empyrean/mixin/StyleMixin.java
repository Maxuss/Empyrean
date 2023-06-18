package net.empyrean.mixin;

import net.empyrean.chat.EmpyreanStyle;
import net.empyrean.chat.SpecialFormatting;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Style.class)
public abstract class StyleMixin implements EmpyreanStyle {
    @Shadow public abstract Style withBold(@Nullable Boolean bool);

    @Shadow @Final @Nullable Boolean bold;

    private SpecialFormatting empyreanFormatting = SpecialFormatting.NONE;

    @NotNull
    @Override
    public Style withSpecial(@NotNull SpecialFormatting fmt) {
        Style clone = this.withBold(this.bold);
        ((StyleMixin) (Object) clone).empyreanFormatting = fmt;
        return clone;
    }

    @NotNull
    @Override
    public SpecialFormatting getSpecialFormat() {
        return empyreanFormatting;
    }

    @Inject(
            method = "toString",
            at = @At("RETURN"),
            cancellable = true
    )
    public void overrideToString(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue("%s <emp fmt=%s>".formatted(cir.getReturnValue(), empyreanFormatting));
    }

    @Inject(
            method = "applyTo",
            at = @At("RETURN"),
            cancellable = true
    )
    public void overrideApply1(Style style, CallbackInfoReturnable<Style> cir) {
        // Applying
        SpecialFormatting other = ((EmpyreanStyle) style).getSpecialFormat();
        SpecialFormatting self = empyreanFormatting;
        cir.setReturnValue(((EmpyreanStyle) cir.getReturnValue()).withSpecial(self.merge(other))); // applying merged formatting as well
    }

    @Inject(
            method = "applyFormat",
            at = @At("RETURN"),
            cancellable = true
    )
    public void overrideApply2(ChatFormatting formatting, CallbackInfoReturnable<Style> cir) {
        cir.setReturnValue(((EmpyreanStyle) cir.getReturnValue()).withSpecial(empyreanFormatting));
    }

    @Inject(
            method = "applyFormat",
            at = @At("RETURN"),
            cancellable = true
    )
    public void overrideApply3(ChatFormatting formatting, CallbackInfoReturnable<Style> cir) {
        cir.setReturnValue(((EmpyreanStyle) cir.getReturnValue()).withSpecial(empyreanFormatting));
    }

    @Inject(
            method = "applyLegacyFormat",
            at = @At("RETURN"),
            cancellable = true
    )
    public void overrideApply4(ChatFormatting formatting, CallbackInfoReturnable<Style> cir) {
        cir.setReturnValue(((EmpyreanStyle) cir.getReturnValue()).withSpecial(empyreanFormatting));
    }
}
