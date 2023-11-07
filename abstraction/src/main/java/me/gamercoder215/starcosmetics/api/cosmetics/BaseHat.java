package me.gamercoder215.starcosmetics.api.cosmetics;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.hat.Hat;
import me.gamercoder215.starcosmetics.util.Constants;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import static me.gamercoder215.starcosmetics.util.inventory.StarInventoryUtil.itemBuilder;

public enum BaseHat implements Hat {

    NORMAL,
    ANIMATED

    ;

    BaseHat() {
        Constants.PARENTS.add(this);
    }

    @Override
    public @NotNull String getNamespace() {
        return "hat/" + name().toLowerCase();
    }

    @Override
    public @NotNull String getDisplayName() {
        return StarConfig.getConfig().get("menu.cosmetics.hats." + name().toLowerCase());
    }

    @Override
    public @NotNull Material getIcon() {
        return this == NORMAL ? Material.LEATHER_HELMET : Material.DIAMOND_HELMET;
    }

    @Override
    public void run(@NotNull Player p, CosmeticLocation<?> loc) {
        if (!ItemStack.class.isAssignableFrom(loc.getInputType()) && !AnimatedItem.class.isAssignableFrom(loc.getInputType())) return;

        boolean normal = this == NORMAL;
        if (p.getEquipment().getHelmet() != null) {
            ItemStack helmet = p.getEquipment().getHelmet();
            NBTWrapper nbt = NBTWrapper.of(helmet);

            boolean hat = nbt.getBoolean("hat");

            if (!hat) {
                if (p.getInventory().firstEmpty() == -1) p.getWorld().dropItemNaturally(p.getLocation(), helmet);
                else p.getInventory().addItem(helmet);

                p.getEquipment().setHelmet(null);
            }

            if (hat && normal) return;
        }

        if (normal) {
            ItemStack item = (ItemStack) loc.getInput();
            p.getEquipment().setHelmet(itemBuilder(item, meta -> meta.setDisplayName(" ")));
        } else {
            AnimatedItem data = (AnimatedItem) loc.getInput();
            data.tryStart(p);
        }
    }

    @Override
    public @NotNull State getType() {
        return State.valueOf(name());
    }
}
