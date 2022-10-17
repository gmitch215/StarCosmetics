package me.gamercoder215.starcosmetics.api.cosmetics.custom;

import java.util.function.Consumer;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.starcosmetics.api.cosmetics.registry.CosmeticLocation;

/**
 * Represents a custom cosmetic, activated when an item is clicked from the inventory.
 */
public interface CustomLocation extends CosmeticLocation<PlayerInteractEvent>, Consumer<PlayerInteractEvent> {

    /**
     * Fetches the item, that upon clicked, will cause this custom cosmetic to run. 
     * @return ItemStack used to initiate this CustomLocation
     */
    @NotNull
    ItemStack getClickItem();

}
