package me.gamercoder215.starcosmetics.api.player;

import me.gamercoder215.starcosmetics.api.cosmetics.pet.Pet;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetType;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

import static me.gamercoder215.starcosmetics.util.Constants.r;

public final class StarPlayerUtil {

    public static void clearPets() {
        for (Player p : Bukkit.getOnlinePlayers()) removePet(p);
    }

    public static Map<UUID, Pet> getPets() {
        return StarPlayer.SPAWNED_PETS;
    }

    @NotNull
    public static Location createPetLocation(@NotNull Player p) {
        if (p == null) return null;

        Location loc = p.getEyeLocation();

        Location locClone = loc.clone();
        locClone.setPitch(0);
        Vector dir = locClone.getDirection();

        return loc
                .subtract(dir.multiply(2))
                .subtract(0, 0.9, 0)
                .add(0.1, r.nextInt(4) * 0.02, 0.1);
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
