package me.gamercoder215.starcosmetics.api.cosmetics.pet;

import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class HeadInfo extends PetInfo {

    private final Consumer<ArmorStand> actions;

    public HeadInfo(String name, Rarity rarity, ItemStack icon, CompletionCriteria criteria, Consumer<ArmorStand> actions) {
        super(rarity, criteria, icon, name);
        this.actions = actions == null ? s -> {} : actions;
    }

    @NotNull
    public static PetInfo of(Rarity rarity, CompletionCriteria criteria, ItemStack icon, String name) {
        return new PetInfo(rarity, criteria, icon, name);
    }

    @NotNull
    public static HeadInfo of(String name, Rarity rarity, ItemStack icon, CompletionCriteria crtieria) {
        return of(name, rarity, icon, crtieria, null);
    }

    @NotNull
    public static HeadInfo of(String name, Rarity rarity, ItemStack icon, CompletionCriteria criteria, Consumer<ArmorStand> actions) {
        return new HeadInfo(name, rarity, icon, criteria, actions);
    }

    public Consumer<ArmorStand> getAction() {
        return actions;
    }

    public void tick(ArmorStand entity) {
        actions.accept(entity);
    }
}
