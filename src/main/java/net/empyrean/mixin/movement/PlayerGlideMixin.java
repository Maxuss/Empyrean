package net.empyrean.mixin.movement;

import net.empyrean.movement.wings.GlidingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Player.class)
public class PlayerGlideMixin implements GlidingEntity {
    @Unique
    private boolean slowFalling = false;

    @Override
    public boolean isSlowFalling() {
        return slowFalling;
    }

    @Override
    public void setSlowFalling(boolean b) {
        slowFalling = b;
    }
}
