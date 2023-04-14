package me.gamercoder215.starcosmetics.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.gamercoder215.starcosmetics.StarCosmetics;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.TrailType;
import me.gamercoder215.starcosmetics.api.player.StarPlayer;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class StarPlaceholders extends PlaceholderExpansion {

    private final StarCosmetics plugin;

    public StarPlaceholders(StarCosmetics plugin) {
        this.plugin = plugin;
        register();
    }

    private static final Map<String, Function<StarPlayer, String>> OFFLINE_PH = new HashMap<String, Function<StarPlayer, String>>() {{
        put("name", StarPlayer::getName);
        put("uuid", p -> p.getUniqueId().toString());
        put("projectile_trail", p -> p.getSelectedTrail(TrailType.PROJECTILE).getFullKey());
        put("ground_trail", p -> p.getSelectedTrail(TrailType.GROUND).getFullKey());
        put("sound_trail", p -> p.getSelectedTrail(TrailType.PROJECTILE_SOUND).getFullKey());
    }};

    @Override
    public @NotNull String getIdentifier() {
        return plugin.getName().toLowerCase();
    }

    @Override
    public @NotNull String getAuthor() {
        return "gamercoder215";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.1.1";
    }

    // Impl

    @Override
    public List<String> getPlaceholders() {
        return new ArrayList<>(OFFLINE_PH.keySet());
    }

    @Override
    public String onRequest(OfflinePlayer p, String arg) {
        if (OFFLINE_PH.containsKey(arg)) return OFFLINE_PH.get(arg).apply(new StarPlayer(p));
        return null;
    }

}
