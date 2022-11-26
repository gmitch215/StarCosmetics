package me.gamercoder215.starcosmetics;

import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestStarCosmetics {

    @Test
    @DisplayName("Test Registry Checks")
    public void testRegistry() {
        for (PetType type : PetType.values()) {
            if (type == PetType.HEAD) continue;
            if (StarCosmetics.PET_MAP.get(type) == null) Assertions.fail("Missing PetType: " + type.name());
        }
    }


}
