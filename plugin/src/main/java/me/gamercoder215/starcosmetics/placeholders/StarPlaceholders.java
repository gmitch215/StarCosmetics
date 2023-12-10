package me.gamercoder215.starcosmetics.placeholders;

import com.google.common.collect.ImmutableMap;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.gamercoder215.starcosmetics.StarCosmetics;
import me.gamercoder215.starcosmetics.api.cosmetics.capes.Cape;
import me.gamercoder215.starcosmetics.api.cosmetics.gadget.Gadget;
import me.gamercoder215.starcosmetics.api.cosmetics.hat.Hat;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.TrailType;
import me.gamercoder215.starcosmetics.api.player.StarPlayer;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class StarPlaceholders extends PlaceholderExpansion {

    private final StarCosmetics plugin;

    public StarPlaceholders(StarCosmetics plugin) {
        this.plugin = plugin;
        register();
    }

    private static final Map<String, Function<StarPlayer, Object>> OFFLINE_PH = ImmutableMap.<String, Function<StarPlayer, Object>>builder()
            .put("name", StarPlayer::getName)
            .put("uuid", StarPlayer::getUniqueId)
            .put("projectile_trail", p -> p.getSelectedTrail(TrailType.PROJECTILE).getFullKey())
            .put("ground_trail", p -> p.getSelectedTrail(TrailType.GROUND).getFullKey())
            .put("sound_trail", p -> p.getSelectedTrail(TrailType.PROJECTILE_SOUND).getFullKey())
            .put("hat", p -> p.getSelectedCosmetic(Hat.class).getFullKey())
            .put("hologram", StarPlayer::getHologramMessage)
            .put("cape", p -> p.getSelectedCosmetic(Cape.class).getFullKey())
            .put("gadget", p -> p.getSelectedCosmetic(Gadget.class).getFullKey())
            .put("selection_limit", StarPlayer::getSelectionLimit)
            .build();

    @Override
    public @NotNull String getIdentifier() {
        return plugin.getName().toLowerCase();
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().get(0);
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    // Impl

    @Override
    public List<String> getPlaceholders() {
        return new ArrayList<>(OFFLINE_PH.keySet());
    }

    @Override
    public String onRequest(OfflinePlayer p, String arg) {
        if (OFFLINE_PH.containsKey(arg)) return String.valueOf(OFFLINE_PH.get(arg).apply(new StarPlayer(p)));
        return null;
    }

}
