package me.gamercoder215.starcosmetics.api.cosmetics;

import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.player.PlayerCompletion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestCompletionCriteria {

    @Test
    @DisplayName("Test Basic Criteria")
    public void testBasicCriteria() {
        CompletionCriteria criteria = CompletionCriteria.fromCompletion(PlayerCompletion.NETHER_ROOF);
        Assertions.assertNotNull(criteria);
        Assertions.assertNotNull(criteria.getDisplayMessage());
    }

}
