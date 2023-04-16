package me.gamercoder215.starcosmetics.api.cosmetics.particle;

import me.gamercoder215.starcosmetics.api.cosmetics.BaseShape;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestParticleShape {

    @Test
    @DisplayName("Test ParticleSizes")
    public void testParticleSizes() {
        for (ParticleSize size : ParticleSize.values())
            Assertions.assertDoesNotThrow(() -> BaseShape.class.getDeclaredField(size.name()));
    }

}
