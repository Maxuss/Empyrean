package net.empyrean.mixin;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.empyrean.chat.EmpyreanStyle;
import net.empyrean.chat.SpecialFormatting;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Type;

@Mixin(Style.Serializer.class)
public abstract class StyleSerializerMixin {
    @Inject(
            method = "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/network/chat/Style;",
            at = @At("RETURN"),
            cancellable = true
    )
    public void getEmpyreanStyling(JsonElement jsonElement, Type type, JsonDeserializationContext ctx, CallbackInfoReturnable<Style> cir) {
        JsonObject obj = jsonElement.getAsJsonObject();
        SpecialFormatting special = obj.has("empyreanFormat") ? SpecialFormatting.values()[obj.get("empyreanFormat").getAsInt()] : SpecialFormatting.NONE;
        Style returnValue = cir.getReturnValue();
        Style ret = ((EmpyreanStyle) returnValue).withSpecial(special);
        cir.setReturnValue(ret);
    }

    @Inject(
            method = "serialize(Lnet/minecraft/network/chat/Style;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;",
            at = @At("RETURN")
    )
    public void injectEmpyreanStyling(Style style, Type type, JsonSerializationContext ctx, CallbackInfoReturnable<JsonElement> cir) {
        JsonElement returnValue = cir.getReturnValue();
        if(returnValue == null)
            return;
        SpecialFormatting special = ((EmpyreanStyle) style).getSpecialFormat();
        if(special != SpecialFormatting.NONE) {
            JsonObject obj = returnValue.getAsJsonObject();
            obj.addProperty("empyreanFormat", special.ordinal());
        }
    }
}
