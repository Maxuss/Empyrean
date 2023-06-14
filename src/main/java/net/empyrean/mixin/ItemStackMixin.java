package net.empyrean.mixin;

import net.empyrean.item.EmpyreanItem;
import net.empyrean.item.EmpyreanItemStack;
import net.empyrean.item.data.ItemData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements EmpyreanItemStack {
    @Shadow @Final @Nullable private Item item;
    private ItemData empyreanData = null;

    @Nullable
    @Override
    public ItemData getItemData() {
        return empyreanData;
    }

    @Inject(method = "<init>(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"))
    private void nbtCtorInjector(CompoundTag compoundTag, CallbackInfo ci) {
        if(item instanceof EmpyreanItem empyrean) {
            this.empyreanData = empyrean.data((ItemStack) (Object) this);
            this.empyreanData.validate();
        }
    }

    @Inject(method = "<init>(Lnet/minecraft/world/level/ItemLike;I)V", at = @At("TAIL"))
    private void itemCtorInjector(ItemLike itemLike, int i, CallbackInfo ci) {
        if(item instanceof EmpyreanItem empyrean) {
            this.empyreanData = empyrean.data((ItemStack) (Object) this);
            this.empyreanData.validate();
        }
    }
}
