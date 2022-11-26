package me.gamercoder215.starcosmetics.wrapper.cosmetics.pet;

import me.gamercoder215.starcosmetics.api.cosmetics.pet.Pet;
import me.gamercoder215.starcosmetics.util.Constants;
import net.minecraft.server.v1_15_R1.EntityInsentient;
import net.minecraft.server.v1_15_R1.EnumMoveType;
import net.minecraft.server.v1_15_R1.PathfinderGoal;
import net.minecraft.server.v1_15_R1.Vec3D;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftMob;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;

public class PathfinderPet1_15_R1 extends PathfinderGoal {

    private static final SecureRandom r = Constants.r;

    private final Pet pet;
    private final Player owner;
    private final EntityInsentient nmsEntity;

    public PathfinderPet1_15_R1(@NotNull Pet pet) {
        this.pet = pet;
        this.owner = pet.getOwner();
        this.nmsEntity = ((CraftMob) pet.getEntity()).getHandle();
    }

    @Override
    public boolean a() {
        return !pet.getEntity().isDead();
    }

    @Override
    public void e() {
        if (!owner.isOnline()) {
            pet.getEntity().remove();
            d();
            return;
        }

        if (owner.getLocation().distanceSquared(pet.getEntity().getLocation()) > 100) pet.getEntity().teleport(owner);

        if (r.nextInt(10000) < 25) // Every ~20 seconds
            nmsEntity.move(EnumMoveType.SELF, new Vec3D(owner.getLocation().getX() - pet.getEntity().getLocation().getX(),
                    owner.getLocation().getY() - pet.getEntity().getLocation().getY(),
                    owner.getLocation().getZ() - pet.getEntity().getLocation().getZ()));
    }


}
