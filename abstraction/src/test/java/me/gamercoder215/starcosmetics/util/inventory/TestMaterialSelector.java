package me.gamercoder215.starcosmetics.util.inventory;

import org.bukkit.Sound;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

public class TestMaterialSelector {

    private static SecureRandom r;

    @BeforeAll
    public static void setUp() {
        r = new SecureRandom();
    }

    @Test
    @DisplayName("Test Sound to ItemStack")
    public void testSoundItem() {
        for (int i = 0; i < 10; i++)
            Assertions.assertNotNull(MaterialSelector.toMaterial(Sound.values()[r.nextInt(Sound.values().length)]));
    }

    @Test
    @DisplayName("Test Event to ItemStack")
    public void testEventItem() {
        for (int i = 0; i < 10; i++)
            Assertions.assertNotNull(MaterialSelector.toMaterial(MaterialSelector.PLAYER_CLASSES.get(r.nextInt(MaterialSelector.PLAYER_CLASSES.size()))));
    }

}
