package me.gamercoder215.starcosmetics.util.selection;

import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseGadget;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.util.inventory.StarInventoryUtil;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.function.Consumer;

import static me.gamercoder215.starcosmetics.wrapper.Wrapper.get;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.getWithArgs;

public final class GadgetSelection extends CosmeticSelection<Consumer<Location>> {

    private final String name;
    private final Material icon;

    public GadgetSelection(String name, Material icon, Consumer<Location> action, CompletionCriteria criteria, Rarity rarity) {
        super(action, criteria, rarity);

        this.name = name;
        this.icon = icon;
    }

    @Override
    public @NotNull String getKey() { return name; }

    @Override
    public @NotNull String getDisplayName() {
        return getWithArgs("constants.gadget", get("cosmetics.gadgets." + name, StarInventoryUtil.toInputString(icon)));
    }

    @Override
    public @NotNull Cosmetic getParent() { return BaseGadget.INSTANCE; }

    @Override
    public Class<? extends Cosmetic> getParentClass() { return BaseGadget.class; }

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull Class<? extends Consumer<Location>> getInputType() { return (Class<? extends Consumer<Location>>) getInput().getClass(); }

    public Material getIcon() { return icon; }

    public ItemStack createGadgetItem() {
        return NBTWrapper.builder(icon,
                meta -> {
                    meta.setDisplayName(rarity.getPrefix() + (rarity.hasInvisibleRequirements() ? ChatColor.BOLD : "") + getDisplayName());
                    meta.setLore(Collections.singletonList(
                            ChatColor.GRAY + get("constants.right_click_use")
                    ));
                }, nbt -> nbt.set("gadget", getFullKey())
        );
    }

}
