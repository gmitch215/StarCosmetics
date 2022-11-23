package me.gamercoder215.starcosmetics.wrapper.cosmetics.pet;

import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetGolem;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PetGolem1_19_R1 extends IronGolem implements PetGolem {

    private final Player owner;
    private final double walkSpeed = 0.5F;

    protected boolean hasRider = false;
    protected boolean isFlying = false;

    public PetGolem1_19_R1(Player owner, Location loc) {
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

    // Moveability

    @Override
    public void travel(Vec3 vec3d) {
        if (!this.isVehicle()) {
            super.travel(vec3d);
            return;
        }

        if (this.onGround && this.isFlying) {
            isFlying = false;
            this.fallDistance = 0;
        }

        LivingEntity passenger = (LivingEntity) this.getPassengers().get(0);

        if (this.isEyeInFluid(FluidTags.WATER)) this.setDeltaMovement(this.getDeltaMovement().add(0, 0.4, 0));

        this.yRotO = passenger.getYRot();
        this.setYRot(passenger.getYRot());

        this.setXRot(passenger.getXRot() * 0.5F);
        setRot(this.getYRot(), this.getXRot());

        this.yHeadRot = getYRot();
        this.yBodyRot = getYRot();

        double side = passenger.xxa * walkSpeed;
        double forward = passenger.zza;

        if (forward <= 0.0F) forward *= 0.25F;
        side *= 0.85F;

        float speed = 0.22222F * (1F + (5));
        ride(side, forward, vec3d.y, speed);

        super.travel(vec3d);
    }

    private void ride(double side, double forward, double up, float mod) {
        double locY;
        float f2;
        float speed;
        float swimSpeed;

        if (this.updateFluidHeightAndDoFluidPushing(FluidTags.WATER, 1)) {
            locY = this.getY();
            speed = 0.8F;
            swimSpeed = 0.02F;

            this.moveRelative(swimSpeed, new Vec3(side, up, forward));
            this.move(MoverType.SELF, this.getDeltaMovement());
            double motX = this.getDeltaMovement().x * speed;
            double motY = this.getDeltaMovement().y * WATER_Y_MOVEMENT_MODIFIER;
            double motZ = this.getDeltaMovement().z * speed;

            motY -= 0.02D;
            if (this.horizontalCollision && this.isFree(this.getDeltaMovement().x, this.getDeltaMovement().y + Y_COLLISION_MODIFIER - this.getY() + locY, this.getDeltaMovement().z))
                motY = BASE_Y_MOTION;
            
            this.setDeltaMovement(motX, motY, motZ);
        } else if (this.updateFluidHeightAndDoFluidPushing(FluidTags.LAVA, 1)) {
            locY = this.getY();
            this.moveRelative(0.02F, new Vec3(side, up, forward));
            this.move(MoverType.SELF, this.getDeltaMovement());
            double motX = this.getDeltaMovement().x * 0.5D;
            double motY = this.getDeltaMovement().y * 0.5D;
            double motZ = this.getDeltaMovement().z * 0.5D;
            motY -= 0.02D;
            if (this.horizontalCollision && this.isFree(this.getDeltaMovement().x, this.getDeltaMovement().y + Y_COLLISION_MODIFIER - this.getY() + locY, this.getDeltaMovement().z))
                motY = BASE_Y_MOTION;

            this.setDeltaMovement(motX, motY, motZ);
        } else {
            speed = mod * (0.16277136F / (FRICTION * FRICTION * FRICTION));

            this.moveRelative(speed, new Vec3(side, up, forward));

            double motX = this.getDeltaMovement().x;
            double motY = this.getDeltaMovement().y;
            double motZ = this.getDeltaMovement().z;

            if (this.onClimbable()) {
                swimSpeed = 0.15F;
                motX = Mth.clamp(motX, -swimSpeed, swimSpeed);
                motZ = Mth.clamp(motZ, -swimSpeed, swimSpeed);
                this.fallDistance = 0.0F;
                if (motY < -0.15D) motY = -0.15D;
            }

            Vec3 mot = new Vec3(motX, motY, motZ);

            this.move(MoverType.SELF, mot);
            if (this.horizontalCollision && this.onClimbable()) motY = 0.2D;

            motY -= 0.08D;

            motY *= 0.9800000190734863D;
            motX *= FRICTION;
            motZ *= FRICTION;

            this.setDeltaMovement(motX, motY, motZ);
        }

        this.animationSpeedOld = this.animationSpeed;
        locY = this.getX() - this.xo;
        double d = this.getZ() - this.zo;
        f2 = (float) Math.sqrt(locY * locY + d * d) * 4.0F;
        if (f2 > 1.0F) f2 = 1.0F;

        this.animationSpeed += (f2 - this.animationSpeed) * 0.4F;
        this.animationPosition += this.animationSpeed;
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

    @Override
    public double getWalkSpeed() {
        return walkSpeed;
    }

    @Override
    public boolean hasRider() {
        return hasRider;
    }

    @Override
    public boolean isFlying() {
        return isFlying;
    }
}
