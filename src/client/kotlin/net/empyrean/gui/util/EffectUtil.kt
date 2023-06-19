package net.empyrean.gui.util

import net.empyrean.util.text.mutable
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.util.FormattedCharSequence
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffectUtil
import kotlin.math.roundToInt

object EffectUtil {
    @JvmStatic
    fun formatDuration(effect: MobEffectInstance): Component {
        if (effect.isInfiniteDuration)
            return Component.translatable("effect.duration.infinite")
        val seconds = effect.duration / 20f
        return if (seconds > 120) {
            val minutes = seconds / 60f
            Component.literal("${minutes.roundToInt()}m")
        } else {
            Component.literal("${seconds.roundToInt()}s")
        }
    }

    @JvmStatic
    fun buildTooltip(effect: MobEffectInstance): List<FormattedCharSequence> {
        val list = mutableListOf<FormattedCharSequence>()
        list.add(
            Component.translatable(effect.descriptionId).append(CommonComponents.SPACE)
                .append(Component.translatable("enchantment.level.${effect.amplifier + 1}")).visualOrderText
        )
        val split =
            Minecraft.getInstance().font.split(Component.translatable("${effect.descriptionId}.description"), 200)
        list.addAll(split)
        list.add(
            MobEffectUtil.formatDuration(
                effect,
                1f
            ).mutable.withStyle(Style.EMPTY.withColor(0x8e8e8e)).visualOrderText
        )
        return list
    }
}