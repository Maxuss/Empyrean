package net.empyrean.mixin;

import net.empyrean.predicate.CompoundTest;
import net.empyrean.predicate.GameStateTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RuleTestType.class)
public interface RuleTestTypeMixin {
    @Inject(
            method = "<clinit>",
            at = @At("TAIL")
    )
    private static void injectCustomRuleTests(CallbackInfo ci) {
        RuleTestType.register("empyrean:game_state", GameStateTest.Companion.getCODEC());
        RuleTestType.register("empyrean:compound_test", CompoundTest.Companion.getCODEC());
    }
}
