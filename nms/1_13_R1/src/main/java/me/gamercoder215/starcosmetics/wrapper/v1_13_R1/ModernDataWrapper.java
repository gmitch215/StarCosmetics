package me.gamercoder215.starcosmetics.wrapper.v1_13_R1;

import me.gamercoder215.starcosmetics.wrapper.DataWrapper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;

final class ModernDataWrapper implements DataWrapper {

    @Override
    public void blockDataParticle(Particle p, Location loc, int count, Material m, double speed) {
        loc.getWorld().spawnParticle(p, loc, count, 0, 0, 0, speed, m.createBlockData());
    }
    
}
