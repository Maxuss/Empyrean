package net.empyrean.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftServer.class)
public class ServerBrandMixin {
    @Inject(method = "getServerModName", at = @At("RETURN"), cancellable = true, remap = false) // Not remapping since it is not obfuscated
    public void overrideServerModName(CallbackInfoReturnable<String> ret) {
        ret.setReturnValue("Macrocosm (Empyrean)");
    }
}
