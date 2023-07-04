package net.empyrean.gui.hud

import net.empyrean.EmpyreanModClient
import net.empyrean.components.data
import net.empyrean.player.PlayerStat
import net.empyrean.player.mana
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.player.LocalPlayer
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import net.minecraft.util.RandomSource
import kotlin.math.roundToInt


object ManaRenderer {
    private val MANA_STAR_LOCATION = ResourceLocation(EmpyreanModClient.MODID, "textures/gui/hud/mana_stars.png")
    private val random = RandomSource.createNewThreadLocalInstance()

    @JvmStatic
    fun renderMana(ticks: Int, player: LocalPlayer, graphics: GuiGraphics, startX: Int, startY: Int) {
        // TODO: mana star blinking when it regenerates
        Minecraft.getInstance().profiler.push("mana")
        val maxMana = player.data.statistics[PlayerStat.MAX_MANA]
        val currentMana = Mth.clamp(player.mana, 0f, maxMana)
        val manaRatio = currentMana / maxMana
        var x = startX
        for(manaIdx in 0 until 8) {
            var individualY = startY
            val individualX = x
            if(manaRatio <= 0.05f) {
                individualY = random.nextInt(2)
            } else if(manaRatio < 0.6f && ticks % ((manaRatio * 8).roundToInt() * 15 + 2) == 0) {
                individualY += random.nextInt(4) - 1
            }
            if(manaIdx + 1 <= manaRatio * 8) {
                renderSingleStar(graphics, individualX, individualY, ManaStarState.FULL)
            } else if(manaIdx + 1 != (manaRatio * 8).roundToInt())
                renderSingleStar(graphics, individualX, individualY, ManaStarState.EMPTY)
            else {
                renderSingleStar(graphics, individualX, individualY, ManaStarState.PARTIAL)
            }
            x += 10
        }
        Minecraft.getInstance().profiler.pop()
    }

    private fun renderSingleStar(graphics: GuiGraphics, x: Int, y: Int, star: ManaStarState) {
        graphics.blit(MANA_STAR_LOCATION, x, y, star.offset.toFloat(), 0f, 10, 9, 31, 9)
    }

    enum class ManaStarState(val offset: Int) {
        FULL(0),
        PARTIAL(10),
        EMPTY(20);

        operator fun times(items: Int): Array<ManaStarState> = if(items <= 0) arrayOf() else Array(items) { this }
    }
}