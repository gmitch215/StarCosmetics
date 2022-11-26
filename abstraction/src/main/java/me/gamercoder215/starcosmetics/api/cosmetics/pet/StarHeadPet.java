package me.gamercoder215.starcosmetics.api.cosmetics.pet;

import me.gamercoder215.starcosmetics.api.cosmetics.pet.custom.HeadPet;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class StarHeadPet implements HeadPet {

    private final Player owner;
    private final ArmorStand entity;
    private final HeadInfo info;

    private final PetType type;

    public StarHeadPet(@NotNull Player owner, Location loc, PetType type, HeadInfo info) {
        this.owner = owner;
        this.type = type;
        this.entity = loc.getWorld().spawn(loc, ArmorStand.class);
        this.info = info;
        entity.setGravity(false);
        entity.setInvulnerable(true);
        entity.setMarker(true);
        entity.setVisible(false);
        entity.setMarker(true);

        entity.getEquipment().setItemInMainHand(getInfo().getIcon());
        if (info.getAction() != null ) info.getAction().accept(entity);
    }

    @Override
    @NotNull
    public ArmorStand getEntity() {
        return entity;
    }

    @Override
    public @NotNull PetInfo getInfo() {
        return info;
    }

    @Override
    @NotNull
    public PetType getPetType() {
        return type;
    }

    @Override
    @NotNull
    public Player getOwner() {
        return owner;
    }

}
