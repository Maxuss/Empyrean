package net.empyrean.mixin.client;

import com.mojang.blaze3d.font.GlyphInfo;
import net.empyrean.chat.EmpyreanStyle;
import net.empyrean.chat.SpecialFormatting;
import net.empyrean.render.effects.EmpyreanEffectRenderer;
import net.empyrean.render.effects.OutlinedFontRenderer;
import net.empyrean.util.text.Text;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Objects;

@Mixin(targets = "net.minecraft.client.gui.Font$StringRenderOutput")
public class StringRenderOutputMixin {
    @Shadow
    public float x;

    @Shadow
    public float y;

    @Shadow @Final private Matrix4f pose;

    @Shadow @Final
    MultiBufferSource bufferSource;

    @Inject(
            method = "accept",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT,
            cancellable = true
    )
    public void renderOutlined(
            int i,
            Style style,
            int charCode,
            CallbackInfoReturnable<Boolean> cir,
            FontSet fontSet,
            GlyphInfo glyphInfo,
            BakedGlyph bakedGlyph,
            boolean isBold,
            float r,
            float g,
            float b,
            float alpha,
            TextColor baseColor,
            float boldOffset,
            float shadowOffset) {
        EmpyreanStyle empyrean = (EmpyreanStyle) style;
        SpecialFormatting special = empyrean.getSpecialFormat();
        if(special != SpecialFormatting.NONE) {
            // Injecting custom Empyrean character rendering here
            if(special.getDrawOutlined()) {
                new OutlinedFontRenderer(
                        Objects.requireNonNull(style.getColor()),
                        TextColor.fromRgb(Text.multiplyColor(special.getSelfColor().getColorValue(), 0.06f, false))
                ) // prev. 0x071138
                .renderChar(new EmpyreanEffectRenderer.EffectRenderPayload(
                        x, y, fontSet, style, charCode, glyphInfo, bakedGlyph, baseColor, pose, bufferSource, alpha
                ));
            }
            float advance = glyphInfo.getAdvance();
            x += advance;
            cir.setReturnValue(true);
        }
    }
}
