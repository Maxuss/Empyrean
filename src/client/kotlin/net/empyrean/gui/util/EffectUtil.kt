package net.empyrean.gui.util

import com.google.common.collect.ImmutableList
import net.empyrean.util.text.mutable
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffectUtil
import kotlin.math.roundToInt

object EffectUtil {
    @JvmStatic
    fun formatDuration(effect: MobEffectInstance): Component {
        if(effect.isInfiniteDuration)
            return Component.translatable("effect.duration.infinite")
        val seconds = effect.duration / 20f
        return if(seconds > 120) {
            val minutes = seconds / 60f
            Component.literal("${minutes.roundToInt()}m")
        } else {
            Component.literal("${seconds.roundToInt()}s")
        }
    }

    @JvmStatic
    fun buildTooltip(effect: MobEffectInstance): List<Component> {
        return ImmutableList.of(
            Component.translatable(effect.descriptionId).append(CommonComponents.SPACE).append(Component.translatable("enchantment.level.${effect.amplifier + 1}")),
            MobEffectUtil.formatDuration(effect, 1f).mutable.withStyle(Style.EMPTY.withColor(0x8e8e8e))
        )
    }
}