package me.gamercoder215.starcosmetics;

import me.gamercoder215.starcosmetics.api.StarConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class TestStarCosmetics {

    @Test
    @DisplayName("Abstraction Test")
    public void testAbstraction() {
        Assertions.assertEquals(StarConfig.ServerVersion.UNKNOWN, StarConfig.ServerVersion.getByVersion("1_8_R1"));
        Assertions.assertEquals(StarConfig.ServerVersion.UNKNOWN, StarConfig.ServerVersion.getByVersion("1_8_R2"));
        Assertions.assertEquals(StarConfig.ServerVersion.UNKNOWN, StarConfig.ServerVersion.getByVersion("1_8_R3"));
        
        Assertions.assertEquals(StarConfig.ServerVersion.CONTEMPORARY, StarConfig.ServerVersion.getByVersion("1_19_R1"));
    }

}
