package me.gamercoder215.starcosmetics.wrapper.cosmetics.pet;

import me.gamercoder215.starcosmetics.api.cosmetics.pet.GolemPet;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class GolemPet1_15_R1 extends EntityIronGolem implements GolemPet {

    private final Player owner;
    private final double walkSpeed = 0.5F;

    protected boolean hasRider = false;
    protected boolean isFlying = false;

    public GolemPet1_15_R1(Player owner, Location loc) {
        super(EntityTypes.IRON_GOLEM, ((CraftWorld) loc.getWorld()).getHandle());

        this.owner = owner;

        this.setPosition(loc.getX(), loc.getY(), loc.getZ());
        this.setCanPickupLoot(false);
        this.setCustomNameVisible(true);
        this.setCustomName(new ChatComponentText(getPetName()));
        this.setInvulnerable(true);

        addScoreboardTag(PET_TAG);
    }

    @Override
    protected void initPathfinder() {
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderPet1_15_R1(this));
        this.goalSelector.a(2, new PathfinderGoalRandomLookaround(this));
        this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityLiving.class, 6.0F));
        this.goalSelector.a(4, new PathfinderGoalRandomStroll(this, 0.6D));
        this.goalSelector.a(5, new PathfinderGoalRandomSwim(this, 1.0D, 10));
    }


    // Other Impl

    @Override
    @NotNull
    public Player getOwner() {
        return owner;
    }

    @Override
    public IronGolem getEntity() {
        return (IronGolem) getBukkitEntity();
    }

}
