package me.gamercoder215.starcosmetics.util.inventory;

import me.gamercoder215.starcosmetics.api.player.cosmetics.SoundEventSelection;
import me.gamercoder215.starcosmetics.util.Constants;
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
        r = Constants.r;
    }

    @Test
    @DisplayName("Test Sound to ItemStack")
    public void testSoundItem() {
        for (int i = 0; i < 10; i++)
            Assertions.assertNotNull(StarInventoryUtil.toMaterial(Sound.values()[r.nextInt(Sound.values().length)]));
    }

    @Test
    @DisplayName("Test Event to ItemStack")
    public void testEventItem() {
        for (int i = 0; i < 10; i++)
            Assertions.assertNotNull(StarInventoryUtil.toMaterial(SoundEventSelection.AVAILABLE_EVENTS.get(r.nextInt(SoundEventSelection.AVAILABLE_EVENTS.size()))));
    }

}
