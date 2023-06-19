package net.empyrean.player

import net.empyrean.components.data
import net.minecraft.world.entity.player.Player

var Player.eHealth
    get() = data.currentHealth
    set(hp) {
        data.currentHealth = hp
    }
var Player.mana
    get() = data.currentMana
    set(mana) {
        data.currentMana = mana
    }
var Player.stats
    get() = data.statistics
    set(stats) {
        data.statistics = stats
    }