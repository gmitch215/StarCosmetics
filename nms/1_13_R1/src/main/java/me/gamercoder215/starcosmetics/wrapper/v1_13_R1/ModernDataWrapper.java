package me.gamercoder215.starcosmetics.wrapper.v1_13_R1;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;

import me.gamercoder215.starcosmetics.wrapper.DataWrapper;

final class ModernDataWrapper implements DataWrapper {

    @Override
    public void blockDataParticle(Particle p, Location loc, int count, Material m) {
        loc.getWorld().spawnParticle(p, loc, count, 0, 0, 0, 0, m.createBlockData());
    }
    
}
