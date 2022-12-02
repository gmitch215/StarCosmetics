package me.gamercoder215.starcosmetics.wrapper;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.material.MaterialData;

public final class LegacyDataWrapper implements DataWrapper {

    @Override
    public void blockDataParticle(Particle p, Location loc, int count, Material m) {
        loc.getWorld().spawnParticle(p, loc, count, new MaterialData(m));
    }
    
}
