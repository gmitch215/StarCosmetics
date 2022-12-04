package me.gamercoder215.starcosmetics.util;

import me.gamercoder215.starcosmetics.api.StarConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static me.gamercoder215.starcosmetics.wrapper.Wrapper.getWithArgs;

public final class StarCooldowns {

    private static final Map<String, Map<UUID, Long>> COOLDOWNS = new HashMap<>();

    public static String formatTime(long ticks) {
        double seconds = (double) ticks / 20D;

        if (seconds < 60) return String.format(StarConfig.getConfig().get("constants.time.seconds"), String.format("%,.0f", seconds));

        double minutes = seconds / 60D;
        if (minutes < 60) return String.format(StarConfig.getConfig().get("constants.time.minutes"), String.format("%,.0f", minutes));

        double hours = minutes / 60D;
        if (hours < 24) return String.format(StarConfig.getConfig().get("constants.time.hours"), String.format("%,.0f", hours));

        double days = hours / 24D;
        return String.format(StarConfig.getConfig().get("constants.time.days"), String.format("%,.0f", days));
    }

    public static Map<UUID, Long> create(String key) {
        Map<UUID, Long> map = new HashMap<>();
        COOLDOWNS.put(key, map);
        return map;
    }

    public static boolean checkCooldown(String cooldown, Player p) {
        if (COOLDOWNS.containsKey(cooldown)) {
            Map<UUID, Long> map = COOLDOWNS.get(cooldown);
            if (map.containsKey(p.getUniqueId())) {
                long time = map.get(p.getUniqueId());
                p.sendMessage(ChatColor.RED + getWithArgs("constants.cooldown_wait", formatTime(time)));
                return true;
            }
        }

        return false;
    }

    public static void set(String cooldown, Player p, long duration) {
        if (!COOLDOWNS.containsKey(cooldown)) create(cooldown);

        COOLDOWNS.get(cooldown).put(p.getUniqueId(), duration);
        new BukkitRunnable() {
            @Override
            public void run() {
                long value = COOLDOWNS.get(cooldown).get(p.getUniqueId());
                if (value <= 0) {
                    COOLDOWNS.get(cooldown).remove(p.getUniqueId());
                    cancel();
                } else COOLDOWNS.get(cooldown).put(p.getUniqueId(), value - 1);
            }
        }.runTaskTimer(StarConfig.getPlugin(), 0, 1);
    }

}
