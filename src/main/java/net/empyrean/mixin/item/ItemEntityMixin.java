package net.empyrean.mixin.item;

import net.empyrean.item.EmpyreanItem;
import net.empyrean.tag.EmpyreanTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
    @Shadow public abstract ItemStack getItem();

    @Inject(
            method = "hurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/Item;canBeHurtBy(Lnet/minecraft/world/damagesource/DamageSource;)Z"
            ),
            cancellable = true
    )
    public void injectExplosionAtHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        Item item = getItem().getItem();
        if(!(item instanceof EmpyreanItem))
            return;
        ItemEntity selfEntity = (ItemEntity) (Object) this;
        if(selfEntity.level().isClientSide)
            return;
        if(getItem().is(EmpyreanTags.VOLATILE) && source.is(DamageTypeTags.IS_FIRE)) {
            // explode :3
            selfEntity.level().explode(selfEntity, selfEntity.getX(), selfEntity.getY(), selfEntity.getZ(), 2, Level.ExplosionInteraction.MOB); // for mob griefing game rule
            this.getItem().onDestroyed(selfEntity);
            selfEntity.discard();
            cir.setReturnValue(true);
        }
    }
}
