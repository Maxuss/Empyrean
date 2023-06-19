package net.empyrean.mixin.client.text;

import com.google.common.util.concurrent.AtomicDouble;
import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.empyrean.chat.EmpyreanStyle;
import net.empyrean.chat.SpecialFormatting;
import net.empyrean.event.client.event.client.ComponentRenderEvent;
import net.empyrean.render.IFontAccessor;
import net.empyrean.render.effects.BakedBloom;
import net.empyrean.render.effects.BloomRenderer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Font.class)
public abstract class TextRendererMixin implements IFontAccessor {
    @Shadow
    abstract FontSet getFontSet(ResourceLocation resourceLocation);

    @Shadow
    public abstract void renderChar(BakedGlyph glyph, boolean bold, boolean italic, float boldOffset, float x, float y, Matrix4f matrix, VertexConsumer buffer, float red, float green, float blue, float alpha, int packedLight);

    @Inject(
            method = "drawInternal(Lnet/minecraft/util/FormattedCharSequence;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)I",
            at = @At("HEAD")
    )
    public void callPreRenderEvent(FormattedCharSequence formattedCharSequence, float f, float g, int i, boolean bl, Matrix4f matrix4f, MultiBufferSource multiBufferSource, Font.DisplayMode displayMode, int j, int k, CallbackInfoReturnable<Integer> cir) {
        // Rendering bloom effect
        float[] bloomTotalLength = new float[]{0};
        int[] empColor = new int[]{-1};

        formattedCharSequence.accept((unused1, style, charCode) -> {
            EmpyreanStyle empyrean = (EmpyreanStyle) style;
            if (empyrean.getSpecialFormat() != SpecialFormatting.NONE) {
                GlyphInfo info = this.getFontSet(style.getFont()).getGlyphInfo(charCode, true);
                bloomTotalLength[0] = bloomTotalLength[0] + info.getAdvance();
                empColor[0] = empyrean.getSpecialFormat().getBloomColor() == null ? -1 : empyrean.getSpecialFormat().getBloomColor().getAltValue();
            }
            return true;
        });

        if (empColor[0] != -1) {
            float spriteWidth = bloomTotalLength[0];
            BloomRenderer.enqueue(new BakedBloom(empColor[0], (int) spriteWidth, (int) f, (int) g));
        }

        AtomicDouble totalOffset = new AtomicDouble(0);
        formattedCharSequence.accept((unused1, style, charCode) -> {
            GlyphInfo info = this.getFontSet(style.getFont()).getGlyphInfo(charCode, true);
            EmpyreanStyle empyrean = (EmpyreanStyle) style;
            if (empyrean.getSpecialFormat() != SpecialFormatting.NONE) {
                ComponentRenderEvent.PRE.invoker().accept(style, charCode, new ComponentRenderEvent.RenderData(totalOffset.floatValue(), f + (float) totalOffset.get(), g, bl, matrix4f, multiBufferSource, displayMode, (Font) (Object) this, this.getFontSet(style.getFont())));
            }
            totalOffset.addAndGet(info.getAdvance());
            return true;
        });
    }

    @Inject(
            method = "drawInternal(Lnet/minecraft/util/FormattedCharSequence;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)I",
            at = @At("TAIL")
    )
    public void callPostRenderEvent(FormattedCharSequence formattedCharSequence, float f, float g, int i, boolean bl, Matrix4f matrix4f, MultiBufferSource multiBufferSource, Font.DisplayMode displayMode, int j, int k, CallbackInfoReturnable<Integer> cir) {
        AtomicDouble totalOffset = new AtomicDouble(0);
        formattedCharSequence.accept((unused1, style, charCode) -> {
            GlyphInfo info = this.getFontSet(style.getFont()).getGlyphInfo(charCode, true);
            EmpyreanStyle empyrean = (EmpyreanStyle) style;
            if (empyrean.getSpecialFormat() != SpecialFormatting.NONE) {
                ComponentRenderEvent.POST.invoker().accept(style, charCode, new ComponentRenderEvent.RenderData(totalOffset.floatValue(), f + (float) totalOffset.get(), g, bl, matrix4f, multiBufferSource, displayMode, (Font) (Object) this, this.getFontSet(style.getFont())));
            }
            totalOffset.addAndGet(info.getAdvance());
            return true;
        });
    }
}
