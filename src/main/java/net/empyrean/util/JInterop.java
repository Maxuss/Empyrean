package net.empyrean.util;

import net.empyrean.components.EmpyreanComponentsKt;
import net.empyrean.player.Stats;
import net.minecraft.world.entity.player.Player;

public class JInterop {
    public static Stats getStats(Object forPlayer) {
        return EmpyreanComponentsKt.getData((Player) forPlayer).getStatistics();
    }
}
