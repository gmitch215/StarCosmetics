package me.gamercoder215.starcosmetics.wrapper.cosmetics.pet;

import me.gamercoder215.starcosmetics.api.cosmetics.pet.Pet;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftMob;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;

public class PathfinderPet1_19_R1 extends Goal {

    private static final SecureRandom r = new SecureRandom();

    private final Pet pet;
    private final Player owner;
    private final Mob nmsEntity;

    public PathfinderPet1_19_R1(@NotNull Pet pet) {
        this.pet = pet;
        this.owner = pet.getOwner();
        this.nmsEntity = ((CraftMob) pet.getEntity()).getHandle();
    }

    @Override
    public boolean canUse() {
        return !pet.getEntity().isDead();
    }

    @Override
    public void tick() {
        if (!owner.isOnline()) {
            pet.getEntity().remove();
            stop();
            return;
        }

        if (owner.getLocation().distanceSquared(pet.getEntity().getLocation()) > 100) pet.getEntity().teleport(owner);

        if (r.nextInt(100) < 5)
            nmsEntity.move(MoverType.SELF, new Vec3(owner.getLocation().getX() - pet.getEntity().getLocation().getX(),
                    owner.getLocation().getY() - pet.getEntity().getLocation().getY(),
                    owner.getLocation().getZ() - pet.getEntity().getLocation().getZ()));
    }
}
