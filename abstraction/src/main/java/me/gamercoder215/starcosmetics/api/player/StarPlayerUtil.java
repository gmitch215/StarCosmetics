package me.gamercoder215.starcosmetics.api.player;

import me.gamercoder215.starcosmetics.api.cosmetics.pet.Pet;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetType;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.gamercoder215.starcosmetics.wrapper.Wrapper.getWrapper;

public final class StarPlayerUtil {

    private static final Wrapper w = getWrapper();

    public static Pet spawnPet(@NotNull Player p, PetType type) {
        Location spawnLocation = p.getLocation().add(1, 1, 0);

        Pet pet = Wrapper.createPet(type, p, spawnLocation);
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
