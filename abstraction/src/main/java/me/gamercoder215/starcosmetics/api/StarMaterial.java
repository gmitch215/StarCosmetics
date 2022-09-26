package me.gamercoder215.starcosmetics.api;

import org.bukkit.Material;

public enum StarMaterial {

    POPPY("red_flower", "poppy"),
    TOTEM("totem", "totem_of_undying"),
    MAGMA_BLOCK("magma", "magma_block"),
    OAK_LOG("log", "oak_log"),
    ;

    private final String[] names;

    StarMaterial(String... allnames) {
        this.names = allnames;
    }

    public Material find() {
        for (String s : names) {
            Material m = Material.matchMaterial(s);
            if (m != null) return m;
        }

        throw new AssertionError("No material found for " + this.name());
    }

}
