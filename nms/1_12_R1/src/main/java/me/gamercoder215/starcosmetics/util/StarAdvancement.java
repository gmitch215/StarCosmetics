package me.gamercoder215.starcosmetics.util;


import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;

public enum StarAdvancement {

    ADVENTURING_TIME("adventure/adventuring_time"),

    
    ;

    private final String advancementId;

    StarAdvancement(String id) {
        this.advancementId = id;
    }

    public String getAdvancementId() {
        return advancementId;
    }

    public Advancement getAdvancement() {
        return Bukkit.getAdvancement(NamespacedKey.minecraft(advancementId));
    }

}
