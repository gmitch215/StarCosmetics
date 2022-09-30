package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import java.util.Map;

import org.bukkit.block.Block;

public class Structure {
    
    private Map<StructurePoint, Block> points;

    public Structure(Map<StructurePoint, Block> points)  {
        this.points = points;
    }

    public Map<StructurePoint, Block> getPoints() {
        return points;
    }

}
