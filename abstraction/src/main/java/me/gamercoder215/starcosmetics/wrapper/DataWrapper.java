package me.gamercoder215.starcosmetics.wrapper;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Material;

// Wrapper Used for Legacy-Specific Actions (e.g. BlockData vs MaterialData)
public interface DataWrapper {
    
    void blockDataParticle(Particle p, Location loc, int count, Material m);

}
