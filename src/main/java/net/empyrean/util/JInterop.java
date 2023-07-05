package net.empyrean.util;

import net.empyrean.client.PlayerClientData;
import net.empyrean.client.PlayerClientDataAccessor;
import net.empyrean.components.EmpyreanComponentsKt;
import net.empyrean.components.PlayerDataComponent;
import net.empyrean.player.Stats;
import net.minecraft.world.entity.player.Player;

public class JInterop {
    public static Stats getStats(Object forPlayer) {
        return EmpyreanComponentsKt.getData((Player) forPlayer).getStatistics();
    }

    public static PlayerClientData getClientData(Object forPlayer) {
        return ((PlayerClientDataAccessor) forPlayer).getClientData();
    }

    public static PlayerDataComponent getPlayerData(Object forPlayer) {
        return EmpyreanComponentsKt.getData((Player) forPlayer);
    }
}
