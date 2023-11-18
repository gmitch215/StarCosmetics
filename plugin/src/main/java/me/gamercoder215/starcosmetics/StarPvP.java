package me.gamercoder215.starcosmetics;

import me.NoChance.PvPManager.PvPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

final class StarPvP {

    static boolean isInPvP(@NotNull Player p) {
        PvPlayer pl = PvPlayer.get(p);
        return pl.isInCombat();
    }

}
