package net.empyrean.util

import net.empyrean.player.Stats

fun Iterable<Stats>.merged(): Stats = Stats.empty().mergeMany(this)