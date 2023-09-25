package me.gamercoder215.starcosmetics.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestStarSound {

    @Test
    @DisplayName("Test StarSound")
    public void testStarSound() {
        for (StarSound sound : StarSound.values())
            Assertions.assertTrue(sound.sounds.size() > 0);
    }

}
