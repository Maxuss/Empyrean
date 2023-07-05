package net.empyrean.mixin.player;

import net.empyrean.events.EmpyreanPlayerEvents;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    public void tick(CallbackInfo ci) {
        EmpyreanPlayerEvents.PLAYER_TICK.invoker().onPlayerTick((ServerPlayer) (Object) this);
    }
}
