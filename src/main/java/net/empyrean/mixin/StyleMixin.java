package net.empyrean.mixin;

import net.empyrean.chat.EmpyreanStyle;
import net.empyrean.chat.SpecialFormatting;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

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

}
