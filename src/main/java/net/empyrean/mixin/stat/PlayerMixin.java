package net.empyrean.mixin.stat;

import net.empyrean.player.PlayerStat;
import net.empyrean.util.JInterop;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {
    @Shadow @Final private Abilities abilities;

    @Inject(
            method = "getFlyingSpeed",
            at = @At("RETURN"),
            cancellable = true
    )
    void modifyFlyingSpeed(CallbackInfoReturnable<Float> cir) {
        // TODO: kinda bugged right now
        if(abilities.flying)
            cir.setReturnValue(cir.getReturnValue() * (1 + JInterop.getStats(this).get(PlayerStat.FLIGHT_SPEED)));
    }
}
