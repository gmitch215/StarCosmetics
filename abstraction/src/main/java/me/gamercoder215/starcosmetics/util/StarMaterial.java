package me.gamercoder215.starcosmetics.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;

public enum StarMaterial {

    POPPY("red_flower"),
    TOTEM("totem"),
    MAGMA_BLOCK("magma"),
    OAK_LOG("log"),

    END_STONE("ender_stone"),

    COMMAND_BLOCK("command"),

    CHAIN_COMMAND_BLOCK("command_chain"),

    REPEATING_COMMAND_BLOCK("command_repeating"),

    GRASS_BLOCK("grass")
    ;

    private final List<String> names = new ArrayList<>();

    StarMaterial(String... allnames) {
        this.names.addAll(Arrays.asList(allnames));
        this.names.add(name());
    }

    public Material find() {
        for (String s : names) {
            Material m = Material.matchMaterial(s);
            if (m != null) return m;
        }

        throw new AssertionError("No material found for " + this.name());
    }

}
