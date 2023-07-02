package net.empyrean.mixin.item;

import com.google.common.collect.Lists;
import net.empyrean.game.ItemManager;
import net.empyrean.item.EmpyreanItem;
import net.empyrean.item.EmpyreanItemStack;
import net.empyrean.item.data.ItemData;
import net.empyrean.item.data.VanillaItemData;
import net.empyrean.item.prefix.Prefix;
import net.empyrean.util.general.CachedDataHolder;
import net.empyrean.util.item.ItemCachedData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements EmpyreanItemStack, CachedDataHolder<ItemCachedData> {
    @Shadow
    @Final
    @Nullable
    private Item item;

    @Shadow public abstract Item getItem();

    @Shadow @Nullable private CompoundTag tag;
    private ItemData empyreanData = null;
    private ItemCachedData cachedData = null;

    @Nullable
    @Override
    public ItemData getItemData() {
        if(item instanceof EmpyreanItem)
            return empyreanData; // there is either no data (and we don't care), or there is data already initialized
        if(empyreanData == null && tag != null && tag.contains("empyrean")) {
            // We are dealing with vanilla data that was initialized on server
            // but was not yet baked on client
            // doing it right now
            VanillaItemData vanillaData = new VanillaItemData((ItemStack) (Object) this);
            vanillaData.validate();
            empyreanData = vanillaData;
        }
        return empyreanData;
    }

    @Inject(method = "<init>(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"))
    private void nbtCtorInjector(CompoundTag compoundTag, CallbackInfo ci) {
        if (item instanceof EmpyreanItem empyrean) {
            ItemData nullableData = empyrean.data((ItemStack) (Object) this);
            if(nullableData == null)
                return;
            this.empyreanData = nullableData;
            this.empyreanData.validate();
        }
    }

    @Inject(method = "<init>(Lnet/minecraft/world/level/ItemLike;I)V", at = @At("TAIL"))
    private void itemCtorInjector(ItemLike itemLike, int i, CallbackInfo ci) {
        if (item instanceof EmpyreanItem empyrean) {
            ItemData nullableData = empyrean.data((ItemStack) (Object) this);
            if(nullableData == null)
                return;
            this.empyreanData = nullableData;
            this.empyreanData.validate();
        }
    }

    @Inject(method = "getDisplayName", cancellable = true, at = @At("HEAD"))
    public void overrideGetName(CallbackInfoReturnable<Component> cir) {
        cir.setReturnValue(Component.literal("HEEEEY"));
    }

    @Override
    public ItemCachedData getOrCalculate() {
        if(cachedData == null) {
            if(getItem() instanceof EmpyreanItem empyrean) {
                return new ItemCachedData(empyrean.getItemKind(), empyrean.getItemRarity(), empyrean.calculateStats((ItemStack) (Object) this));
            }
            cachedData = new ItemCachedData(ItemManager.tryExtractVanillaItemKind(getItem()), ItemManager.tryExtractVanillaRarity(getItem()), ItemManager.extractItemStats((ItemStack) (Object) this));
        }
        return cachedData;
    }

    @Inject(
            method = "getTooltipLines",
            at = @At("RETURN"),
            cancellable = true
    )
    public void overrideLore(Player player, TooltipFlag isAdvanced, CallbackInfoReturnable<List<Component>> cir) {
        if(getItem() instanceof EmpyreanItem) // already handled
            return;
        if(player == null)
            return; // yes, this can happen

        List<Component> lines = Lists.newArrayList(cir.getReturnValue().get(0));
        ItemCachedData cached = getOrCalculate();
        EmpyreanItem.appendHoverTextVanilla(cached.kind(), cached.rarity(), cached.stats(), (ItemStack) (Object) this, player.level(), lines);
        cir.setReturnValue(lines);
    }

    @Inject(
            method = "getHoverName",
            at = @At("HEAD"),
            cancellable = true
    )
    public void overrideHoverName(CallbackInfoReturnable<Component> cir) {
        if(getItem() instanceof EmpyreanItem) // already handled
            return;
        cir.setReturnValue(hoverName());
    }

    @Inject(
            method = "getDisplayName",
            at = @At("HEAD"),
            cancellable = true
    )
    public void overrideDisplayName(CallbackInfoReturnable<Component> cir) {
        if(getItem() instanceof EmpyreanItem) // already handled
            return;
        cir.setReturnValue(hoverName());
    }

    private Component hoverName() {
        ItemCachedData cached = getOrCalculate();
        return EmpyreanItem.getNameVanilla(cached, (ItemStack) (Object) this);
    }

    @Override
    public void setPrefix(@NotNull Prefix newPrefix) {
        // THIS IS MOST LIKELY DONE SERVER SIDE!
        if(empyreanData == null) {
            // we are probably dealing with a vanilla item, so we need to initialize vanilla data here
            empyreanData = new VanillaItemData((ItemStack) (Object) this);
            empyreanData.saveDefault();
        }
        empyreanData.setPrefix(newPrefix);
    }
}
