package me.gamercoder215.starcosmetics.api;

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
    }

    @Test
    @DisplayName("Test CompletionCriteria#toPlural")
    public void testToPlural() {
        Assertions.assertEquals(CompletionCriteria.toPlural("test"), "tests");
        Assertions.assertEquals(CompletionCriteria.toPlural("coral"), "coral");
        Assertions.assertEquals(CompletionCriteria.toPlural("tree"), "trees");
        Assertions.assertEquals(CompletionCriteria.toPlural("enderman"), "endermen");
        Assertions.assertEquals(CompletionCriteria.toPlural("blaze"), "blazes");
        Assertions.assertEquals(CompletionCriteria.toPlural("zombie"), "zombies");
        Assertions.assertEquals(CompletionCriteria.toPlural("droh"), "drohes");
        Assertions.assertEquals(CompletionCriteria.toPlural("cry"), "cries");
        Assertions.assertEquals(CompletionCriteria.toPlural("cactus"), "cacti");
        Assertions.assertEquals(CompletionCriteria.toPlural("copper"), "copper");
    }

}
