package me.gamercoder215.starcosmetics.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum StarMaterial {

    POPPY("red_flower", "red_rose"),

    TOTEM_OF_UNDYING(Material.AIR,"totem"),

    MAGMA_BLOCK(Material.STONE, "magma"),

    PLAYER_HEAD(3, "skull_item"),

    OAK_LOG("log"),

    END_STONE("ender_stone"),

    COMMAND_BLOCK("command"),

    CHAIN_COMMAND_BLOCK("command_chain"),

    REPEATING_COMMAND_BLOCK("command_repeating"),

    GRASS_BLOCK("grass"),

    CRAFTING_TABLE("workbench"),

    BLACK_STAINED_GLASS_PANE(15, "stained_glass_pane"),

    RED_STAINED_GLASS_PANE(14, "stained_glass_pane"),

    LIME_STAINED_GLASS_PANE(5, "stained_glass_pane"),

    LIME_WOOL(5, "wool"),

    RED_WOOL(14, "wool"),

    WHITE_WOOL(0, "wool"),

    LIGHT_BLUE_WOOL(3, "wool"),

    LEAD("leash"),

    LILY_PAD("water_lily"),

    FIREWORK_STAR("firework_charge"),

    OBSERVER(Material.OBSIDIAN),

    ENDER_EYE("eye_of_ender"),

    FIREWORK_ROCKET("firework"),

    RED_CONCRETE(StarMaterial.RED_WOOL.find()),

    LIGHT_BLUE_CONCRETE(StarMaterial.LIGHT_BLUE_WOOL.find()),

    LIME_CONCRETE(StarMaterial.LIME_WOOL.find()),

    WITHER_SKELETON_SKULL(1, "skull_item"),

    EXPERIENCE_BOTTLE("exp_bottle"),

    ENCHANTING_TABLE("enchantment_table")
    ;

    private final Material defaultV;
    private final short data;
    private final boolean dataOnlyLegacy;
    private final List<String> names = new ArrayList<>();

    StarMaterial(Material defaultV, int data, boolean dataOnlyLegacy, String... allnames) {
        this.names.add(name());
        this.names.addAll(Arrays.asList(allnames));

        this.defaultV = defaultV;
        this.dataOnlyLegacy = dataOnlyLegacy;
        this.data = (short) data;
    }

    StarMaterial(Material defaultV, String... allnames) {
        this(defaultV, 0, true, allnames);
    }

    StarMaterial(int data, String... allnames) {
        this(null, data, true, allnames);
    }

    StarMaterial(Material defaultV, int data, String... allnames) {
        this(defaultV, data, true, allnames);
    }

    StarMaterial(String... allnames) {
        this(null, 0, true, allnames);
    }

    @NotNull
    public Material find() {
        for (String s : names) {
            Material m = Material.matchMaterial(s);
            if (m != null) return m;
        }

        if (defaultV != null) return defaultV;

        throw new AssertionError("No material found for " + name());
    }

    public ItemStack findStack() {
        return findStack(1);
    }

    public ItemStack findStack(int amount) {
        if (dataOnlyLegacy) return new ItemStack(find());
        else return new ItemStack(find(), amount, data);
    }



}
