package me.gamercoder215.starcosmetics.wrapper;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;

// Wrapper Used for Legacy-Specific Actions (e.g. BlockData vs MaterialData)
public interface DataWrapper {
    
    void blockDataParticle(Particle p, Location loc, int count, Material m, double speed);

}
