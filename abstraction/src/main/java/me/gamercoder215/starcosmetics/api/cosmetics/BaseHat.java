package me.gamercoder215.starcosmetics.api.cosmetics;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.hat.AnimatedHatData;
import me.gamercoder215.starcosmetics.api.cosmetics.hat.Hat;
import me.gamercoder215.starcosmetics.api.cosmetics.hat.HatType;
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
        return StarConfig.getConfig().get("menu.cosmetics.hat");
    }

    @Override
    public @NotNull Material getIcon() {
        return Material.LEATHER_HELMET;
    }

    @Override
    public void run(@NotNull Player p, CosmeticLocation<?> loc) {
        if (!ItemStack.class.isAssignableFrom(loc.getInputType()) && !AnimatedHatData.class.isAssignableFrom(loc.getInputType())) return;

        if (p.getEquipment().getHelmet() != null) {
            ItemStack helmet = p.getEquipment().getHelmet();
            NBTWrapper nbt = NBTWrapper.of(helmet);

            if (!nbt.getBoolean("hat")) {

                if (p.getInventory().firstEmpty() == -1) p.getWorld().dropItemNaturally(p.getLocation(), helmet);
                else p.getInventory().addItem(helmet);

                p.getEquipment().setHelmet(null);
            }
        }

        if (this == NORMAL) {
            ItemStack item = (ItemStack) loc.getInput();
            p.getEquipment().setHelmet(itemBuilder(item, meta -> meta.setDisplayName(" ")));
        } else {
            AnimatedHatData data = (AnimatedHatData) loc.getInput();
            data.tryStart(p);
        }
    }

    @Override
    public @NotNull HatType getType() {
        return HatType.valueOf(name());
    }
}
