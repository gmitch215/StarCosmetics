package me.gamercoder215.starcosmetics.wrapper.cosmetics.pet;

import me.gamercoder215.starcosmetics.api.cosmetics.pet.GolemPet;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.IronGolem;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GolemPet1_19_R1 extends IronGolem implements GolemPet {

    private final Player owner;
    private final double walkSpeed = 0.5F;

    protected boolean hasRider = false;
    protected boolean isFlying = false;

    public GolemPet1_19_R1(Player owner, Location loc) {
        super(EntityType.IRON_GOLEM, ((CraftWorld) loc.getWorld()).getHandle());

        this.owner = owner;

        this.setPos(loc.getX(), loc.getY(), loc.getZ());
        this.setCanPickUpLoot(false);
        this.setCustomNameVisible(true);
        this.setCustomName(Component.literal(getPetName()));
        this.setInvulnerable(true);

        addTag(PET_TAG);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PathfinderPet1_19_R1(this));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, LivingEntity.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(5, new RandomSwimmingGoal(this, 1.0D, 10));
    }

    // Other Impl

    @Override
    @NotNull
    public Player getOwner() {
        return owner;
    }

    @Override
    public org.bukkit.entity.IronGolem getEntity() {
        return (org.bukkit.entity.IronGolem) getBukkitEntity();
    }
}
