package me.gamercoder215.starcosmetics.api.player;

import me.gamercoder215.starcosmetics.api.cosmetics.pet.Pet;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetType;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.gamercoder215.starcosmetics.wrapper.Wrapper.getWrapper;

public final class StarPlayerUtil {

    private static final Wrapper w = getWrapper();

    public static void clearPets() {
        for (Player p : Bukkit.getOnlinePlayers()) removePet(p);
    }

    @NotNull
    public static Location createPetLocation(@NotNull Player p) {
        if (p == null) return null;
        Location loc = p.getEyeLocation();

        loc.subtract(loc.getDirection().setY(0).multiply(1.2));

        return loc;
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
