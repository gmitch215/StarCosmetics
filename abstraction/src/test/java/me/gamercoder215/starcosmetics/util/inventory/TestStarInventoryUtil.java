package me.gamercoder215.starcosmetics.util.inventory;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestStarInventoryUtil {

    @Test
    @DisplayName("Test Sound-Material Conversion")
    public void testSoundToMaterial() {
        Assertions.assertEquals(Material.LEATHER_CHESTPLATE, StarInventoryUtil.toMaterial(Sound.ENTITY_GENERIC_BURN));
        Assertions.assertEquals(Material.FISHING_ROD, StarInventoryUtil.toMaterial(Sound.ENTITY_BOBBER_SPLASH));
    }

}
