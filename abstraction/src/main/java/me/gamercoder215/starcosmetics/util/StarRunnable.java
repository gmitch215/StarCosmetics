package me.gamercoder215.starcosmetics.util;

import me.gamercoder215.starcosmetics.api.StarConfig;
import org.bukkit.scheduler.BukkitRunnable;

public final class StarRunnable {

    public static void async(Runnable run) {
        new BukkitRunnable() {
            @Override
            public void run() {
                run.run();
            }
        }.runTaskAsynchronously(StarConfig.getPlugin());
    }

    public static void sync(Runnable run) {
        new BukkitRunnable() {
            @Override
            public void run() {
                run.run();
            }
        }.runTask(StarConfig.getPlugin());
    }

    public static void syncLater(Runnable run, long delay) {
        new BukkitRunnable() {
            @Override
            public void run() {
                run.run();
            }
        }.runTaskLater(StarConfig.getPlugin(), delay);
    }

    public static void asyncLater(Runnable run, long delay) {
        new BukkitRunnable() {
            @Override
            public void run() {
                run.run();
            }
        }.runTaskLaterAsynchronously(StarConfig.getPlugin(), delay);
    }

}
