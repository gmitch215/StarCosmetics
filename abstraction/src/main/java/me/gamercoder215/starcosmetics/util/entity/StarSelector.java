package me.gamercoder215.starcosmetics.util.entity;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public final class StarSelector {

    private StarSelector() { throw new UnsupportedOperationException(); }

    public static boolean isStopped(@NotNull Entity en) {
        return en.isOnGround() || en.isDead() || !en.isValid() || en.hasMetadata("stopped");
    }

}
