package net.empyrean.mixin.player;

import net.empyrean.client.PlayerClientData;
import net.empyrean.client.PlayerClientDataAccessor;
import net.empyrean.events.EmpyreanDamageEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin implements PlayerClientDataAccessor {
    @Shadow public abstract boolean isLocalPlayer();

    private PlayerClientData clientData = null;

    @SuppressWarnings("DataFlowIssue")
    @Inject(
            method = "actuallyHurt",
            at = @At("TAIL")
    )
    public void onDamage(DamageSource damageSource, float damageAmount, CallbackInfo ci) {
        if(!this.isLocalPlayer())
            EmpyreanDamageEvents.SERVER_PLAYER_DAMAGED.invoker().onDamage((ServerPlayer) (Object) this, damageAmount, damageSource);
    }

    @NotNull
    @Override
    public PlayerClientData getClientData() {
        if(!this.isLocalPlayer())
            throw new IllegalStateException("Tried to access player client data for server player!");
        if(clientData == null)
            clientData = new PlayerClientData();
        return clientData;
    }
}
