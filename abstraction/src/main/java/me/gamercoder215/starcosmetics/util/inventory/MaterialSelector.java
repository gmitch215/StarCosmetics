package me.gamercoder215.starcosmetics.util.inventory;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;

public final class MaterialSelector {

    private static final Wrapper w = Wrapper.getWrapper();
    
    private MaterialSelector() { throw new UnsupportedOperationException(); }

    @NotNull
    public static Material toMaterial(Class<? extends Event> eventClass) {
        if (Modifier.isAbstract(eventClass.getModifiers())) throw new IllegalArgumentException("Using Abstract Class");

        String n = eventClass.getSimpleName().toLowerCase();
        Material chosen = null;

        if (n.contains("player")) chosen = Material.IRON_SWORD;
        if (chosen == null && n.contains("block")) chosen = Material.DIRT;
        if (chosen == null && n.contains("server")) chosen = StarMaterial.OBSERVER.find();
        if (chosen == null && n.contains("inventory")) chosen = StarMaterial.CRAFTING_TABLE.find();
        if (chosen == null && n.contains("weather")) chosen = Material.BUCKET;
        if (chosen == null && n.contains("vehicle")) chosen = Material.MINECART;

        switch (n) {
            case "asyncplayerchatevent": return StarMaterial.COMMAND_BLOCK.find();
            case "playerjoinevent": return StarMaterial.GRASS_BLOCK.find();
            case "playerrespawnevent": return Material.BEACON;
        }

        if (chosen == null) chosen = Material.REDSTONE;
        return chosen;
    }

    @NotNull
    public static Material toMaterial(Sound s) {
        String n = s.name();
        Material chosen = null;

        for (Material m : Arrays.stream(Material.values()).filter(w::isItem).collect(Collectors.toList())) {
            if (n.equals(m.name())) return m; // Takes Absolute Priority
            
            if (n.contains(m.name())) {
                chosen = m;
                break;
            }
        }
        
        if (chosen == null && n.startsWith("ENTITY")) chosen = StarMaterial.POPPY.find();   
        if (chosen == null && n.contains("GENERIC")) chosen = Material.LEATHER_CHESTPLATE;
        if (chosen == null && n.contains("VILLAGER")) chosen = Material.EMERALD;

        if (n.startsWith("MUSIC")) {
            if (n.contains("OVERWORLD")) chosen = StarMaterial.GRASS_BLOCK.find();
            else if (n.contains("NETHER")) chosen = Material.NETHERRACK;
            else chosen = Material.NOTE_BLOCK;
        }

        if (chosen == null && n.startsWith("RECORD")) chosen = Material.JUKEBOX;
        if (chosen == null && n.startsWith("UI")) chosen = Material.REDSTONE_BLOCK;
        if (chosen == null && n.startsWith("WEATHER")) chosen = Material.BUCKET;

        return chosen == null ? Material.STONE : chosen;
    }

}
