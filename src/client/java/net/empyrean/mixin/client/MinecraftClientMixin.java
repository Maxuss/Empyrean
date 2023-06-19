package net.empyrean.mixin.client;

import net.empyrean.event.client.event.client.RenderTickEvent;
import net.empyrean.render.particle.ParticleEngine2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Timer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftClientMixin {
    @Shadow
    private boolean pause;

    @Shadow
    private float pausePartialTick;

    @Final
    @Shadow
    private Timer timer;

    @Inject(
            method = "runTick",
            at = @At(
                    value = "INVOKE_STRING",
                    target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V",
                    args = {"ldc=gameRenderer"}),
            require = 1
    )
    public void runTick(boolean tickWorld, CallbackInfo ci) {
        RenderTickEvent.START.invoker().onStart(pause ? pausePartialTick : timer.partialTick);
    }

    @Inject(
            method = "runTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V",
                    ordinal = 4
            )
    )
    public void runPostTick(boolean tickWorld, CallbackInfo ci) {
        RenderTickEvent.END.invoker().invoke();
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/particle/ParticleEngine;tick()V"
            )
    )
    public void beforeParticleEngineTick(CallbackInfo ci) {
        ParticleEngine2D.tick(); // Injecting UI particle tick
    }
}
