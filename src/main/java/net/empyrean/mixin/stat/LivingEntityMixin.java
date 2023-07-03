package net.empyrean.mixin.stat;

import net.empyrean.player.PlayerStat;
import net.empyrean.util.JInterop;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @SuppressWarnings("ConstantValue")
    @Redirect(
            method = "jumpFromGround",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V")
    )
    public void modifyJumpHeight(LivingEntity instance, Vec3 vec3) {
        if(!(((Object) this) instanceof Player)) {
            instance.setDeltaMovement(vec3);
            return;
        }
        Vec3 delta = instance.getDeltaMovement();
        instance.setDeltaMovement(new Vec3(delta.x, delta.y * (1 + JInterop.getStats(this).get(PlayerStat.JUMP_HEIGHT)), delta.z));
    }

    @SuppressWarnings("ConstantValue")
    @Redirect(
            method = "jumpFromGround",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(DDD)V")
    )
    public void modifySprintJumpHeight(LivingEntity instance, double x, double y, double z) {
        if(!(((Object) this) instanceof Player)) {
            instance.setDeltaMovement(x, y, z);
            return;
        }
        Vec3 delta = instance.getDeltaMovement();
        float jumpSpeed = JInterop.getStats(this).get(PlayerStat.JUMP_SPEED);
        float jumpHeight = JInterop.getStats(this).get(PlayerStat.JUMP_HEIGHT);
        instance.setDeltaMovement(delta.add(x, (y + jumpHeight) * Mth.square(1 + jumpSpeed * 0.5), z));
    }
}
