package me.gamercoder215.starcosmetics.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestStarMaterial {

    @Test
    @DisplayName("Test StarMaterial")
    public void testStarMaterial() {
        for (StarMaterial material : StarMaterial.values())
            Assertions.assertTrue(material.names.size() > 1 || material.defaultV != null);
    }

}
