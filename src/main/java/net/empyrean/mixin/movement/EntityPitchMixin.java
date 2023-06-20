package net.empyrean.mixin.movement;

import net.empyrean.movement.wings.WingUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public class EntityPitchMixin {
    @Redirect(
            method = "turn",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/Mth;clamp(FFF)F"
            )
    )
    public float adjustPitch(float value, float min, float max) {
        return WingUtil.adjustPitch((Entity) (Object) this, Mth.clamp(value, min, max));
    }
}
