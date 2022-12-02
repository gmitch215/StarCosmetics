package me.gamercoder215.starcosmetics.wrapper;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;

public final class ModernDataWrapper implements DataWrapper {

    @Override
    public void blockDataParticle(Particle p, Location loc, int count, Material m) {
        loc.getWorld().spawnParticle(p, loc, 1, m.createBlockData());
    }
    
}
