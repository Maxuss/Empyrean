package net.empyrean.mixin.client.player;

import net.empyrean.events.EmpyreanDamageEvents;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @Inject(
            method = "hurt",
            at = @At("TAIL")
    )
    public void onActualHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        EmpyreanDamageEvents.CLIENT_PLAYER_DAMAGED.invoker().onDamageClient((LocalPlayer) (Object) this, amount, source);
    }
}
