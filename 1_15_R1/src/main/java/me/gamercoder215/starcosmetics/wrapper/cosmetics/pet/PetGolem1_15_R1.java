package me.gamercoder215.starcosmetics.wrapper.cosmetics.pet;

import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetGolem;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class PetGolem1_15_R1 extends EntityIronGolem implements PetGolem {

    private final Player owner;
    private final double walkSpeed = 0.5F;

    protected boolean hasRider = false;
    protected boolean isFlying = false;

    public PetGolem1_15_R1(Player owner, Location loc) {
        super(EntityTypes.IRON_GOLEM, ((CraftWorld) loc.getWorld()).getHandle());

        this.owner = owner;

        this.setPosition(loc.getX(), loc.getY(), loc.getZ());
        this.setCanPickupLoot(false);
        this.setCustomNameVisible(true);
        this.setCustomName(new ChatComponentText(getPetName()));
    }

    @Override
    @NotNull
    public Player getOwner() {
        return owner;
    }

    @Override
    public IronGolem getEntity() {
        return (IronGolem) getBukkitEntity();
    }

    // Moveability

    @Override
    public void e(Vec3D vec3d) {
        if (!this.isVehicle()) {
            super.e(vec3d);
            return;
        }

        if (this.onGround && this.isFlying) {
            isFlying = false;
            this.fallDistance = 0;
        }

        EntityLiving passenger = (EntityLiving) this.getPassengers().get(0);

        if (this.a(TagsFluid.WATER)) this.setMot(this.getMot().add(0, 0.4, 0));

        this.lastYaw = (this.yaw = passenger.yaw);
        this.pitch = passenger.pitch * 0.5F;
        setYawPitch(this.yaw, this.pitch);
        this.aK = (this.aI = this.yaw);

        double motionSideways = passenger.aZ * walkSpeed;
        double motionForward = passenger.bb;

        if (motionForward <= 0.0F) motionForward *= 0.25F;
        motionSideways *= 0.85F;

        float speed = 0.22222F * (1F + (5));
        ride(motionSideways, motionForward, vec3d.y, speed);

        super.a(vec3d);
    }

    private void ride(double side, double forward, double up, float mod) {
        double locY;
        float f2;
        float speed;
        float swimSpeed;

        if (this.b(TagsFluid.WATER)) {
            locY = this.locY();
            speed = 0.8F;
            swimSpeed = 0.02F;

            this.a(swimSpeed, new Vec3D(side, up, forward));
            this.move(EnumMoveType.SELF, this.getMot());
            double motX = this.getMot().x * speed;
            double motY = this.getMot().y * WATER_Y_MOVEMENT_MODIFIER;
            double motZ = this.getMot().z * speed;
            motY -= 0.02D;
            if (this.positionChanged && this.e(this.getMot().x, this.getMot().y + Y_COLLISION_MODIFIER - this.locY() + locY, this.getMot().z))
                motY = BASE_Y_MOTION;
            this.setMot(motX, motY, motZ);
        } else if (this.b(TagsFluid.LAVA)) {
            locY = this.locY();
            this.a(0.02F, new Vec3D(side, up, forward));
            this.move(EnumMoveType.SELF, this.getMot());
            double motX = this.getMot().x * 0.5D;
            double motY = this.getMot().y * 0.5D;
            double motZ = this.getMot().z * 0.5D;
            motY -= 0.02D;
            if (this.positionChanged && this.e(this.getMot().x, this.getMot().y + Y_COLLISION_MODIFIER - this.locY() + locY, this.getMot().z))
                motY = BASE_Y_MOTION;
            this.setMot(motX, motY, motZ);
        } else {
            speed = mod * (0.16277136F / (FRICTION * FRICTION * FRICTION));

            this.a(speed, new Vec3D(side, up, forward));

            double motX = this.getMot().x;
            double motY = this.getMot().y;
            double motZ = this.getMot().z;

            if (this.isClimbing()) {
                swimSpeed = 0.15F;
                motX = MathHelper.a(motX, -swimSpeed, swimSpeed);
                motZ = MathHelper.a(motZ, -swimSpeed, swimSpeed);
                this.fallDistance = 0.0F;
                if (motY < -0.15D) motY = -0.15D;
            }

            Vec3D mot = new Vec3D(motX, motY, motZ);

            this.move(EnumMoveType.SELF, mot);
            if (this.positionChanged && this.isClimbing()) motY = 0.2D;

            motY -= 0.08D;

            motY *= 0.9800000190734863D;
            motX *= FRICTION;
            motZ *= FRICTION;

            this.setMot(motX, motY, motZ);
        }

        this.aC = this.aD;
        locY = this.locX() - this.lastX;
        double d1 = this.locZ() - this.lastZ;
        f2 = MathHelper.sqrt(locY * locY + d1 * d1) * 4.0F;
        if (f2 > 1.0F) f2 = 1.0F;

        this.aD += (f2 - this.aD) * 0.4F;
        this.aE += this.aD;
    }

    // Other Impl

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
