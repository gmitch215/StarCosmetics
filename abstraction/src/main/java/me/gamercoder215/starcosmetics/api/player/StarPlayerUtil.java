package me.gamercoder215.starcosmetics.api.player;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.Pet;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetType;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static me.gamercoder215.starcosmetics.wrapper.Wrapper.STAR_PLAYER_CACHE;

public final class StarPlayerUtil {

    private static final Map<UUID, ArmorStand> HOLOGRAMS = new HashMap<>();

    public static void onDisable() {
        // Remove Pets
        for (Player p : Bukkit.getOnlinePlayers()) removePet(p);

        // Remove Holograms
        for (ArmorStand as : HOLOGRAMS.values()) as.remove();
    }

    public static Map<UUID, Pet> getPets() {
        return StarPlayer.SPAWNED_PETS;
    }

    @Nullable
    public static ArmorStand getHologram(Player p) {
        if (!STAR_PLAYER_CACHE.containsKey(p.getUniqueId())) {
            StarPlayer sp = new StarPlayer(p);
            STAR_PLAYER_CACHE.put(p.getUniqueId(), sp);

            if (HOLOGRAMS.containsKey(p.getUniqueId()))
                HOLOGRAMS.get(p.getUniqueId()).setCustomName(sp.getSetting(PlayerSetting.HOLOGRAM_FORMAT).toString() + sp.getHologramMessage());
        }

        StarPlayer sp = STAR_PLAYER_CACHE.get(p.getUniqueId());
        if (sp.getHologramMessage().isEmpty()) {
            if (HOLOGRAMS.get(p.getUniqueId()) != null) {
                HOLOGRAMS.get(p.getUniqueId()).remove();
                HOLOGRAMS.remove(p.getUniqueId());
            }

            return null;
        }

        if (HOLOGRAMS.get(p.getUniqueId()) != null) return HOLOGRAMS.get(p.getUniqueId());

        ArmorStand hologram = p.getWorld().spawn(p.getEyeLocation().add(0, 0.3, 0), ArmorStand.class);
        hologram.setInvulnerable(true);
        hologram.setGravity(false);
        hologram.setVisible(false);
        hologram.setMarker(true);

        hologram.setCustomNameVisible(true);
        hologram.setCustomName(sp.getSetting(PlayerSetting.HOLOGRAM_FORMAT).toString() + sp.getHologramMessage());
        hologram.setMetadata("starcosmetics:nointeract", new FixedMetadataValue(StarConfig.getPlugin(), true));

        HOLOGRAMS.put(p.getUniqueId(), hologram);
        return hologram;
    }

    @NotNull
    public static Location createPetLocation(@NotNull Player p) {
        if (p == null) return null;

        if (!STAR_PLAYER_CACHE.containsKey(p.getUniqueId()))
            STAR_PLAYER_CACHE.put(p.getUniqueId(), new StarPlayer(p));

        StarPlayer sp = STAR_PLAYER_CACHE.get(p.getUniqueId());

        return sp.getSetting(PlayerSetting.PET_POSITION).apply(p);
    }

    public static Pet spawnPet(@NotNull Player p, PetType type) {
        Pet pet = Wrapper.createPet(type, p, createPetLocation(p));
        StarPlayer.SPAWNED_PETS.put(p.getUniqueId(), pet);

        return pet;
    }

    public static void removePet(@NotNull Player p) {
        Pet active = StarPlayer.SPAWNED_PETS.get(p.getUniqueId());
        if (active == null) return;

        active.getEntity().remove();
        StarPlayer.SPAWNED_PETS.remove(p.getUniqueId());
    }

}
