package net.empyrean.mixin.client;

import net.empyrean.gui.text.ComponentSplashRenderer;
import net.empyrean.gui.text.Splashes;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.resources.SplashManager;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.ThreadLocalRandom;

@Mixin(SplashManager.class)
public class SplashManagerMixin {
    @Inject(method = "getSplash", cancellable = true, at = @At("HEAD"))
    public void getEmpyreanMixin(CallbackInfoReturnable<SplashRenderer> cir) {
        Object randomSplash = Splashes.getAllSplashes().get(ThreadLocalRandom.current().nextInt(Splashes.getAllSplashes().size()));
        SplashRenderer renderer = randomSplash instanceof String str ? new SplashRenderer(str) : new ComponentSplashRenderer((Component) randomSplash);
        cir.setReturnValue(renderer);
    }
}
