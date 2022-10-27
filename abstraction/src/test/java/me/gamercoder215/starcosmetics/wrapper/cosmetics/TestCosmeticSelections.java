package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class TestCosmeticSelections {

    @Test
    @DisplayName("Test List Join")
    public void testListJoin() {
        List<String> l1 = Collections.singletonList("Hello");
        List<String> l2 = Collections.singletonList("World");
        List<String> joined = CosmeticSelections.join(l1, l2);

        Assertions.assertEquals(2, joined.size());
        Assertions.assertEquals("Hello", joined.get(0));
        Assertions.assertEquals("World", joined.get(1));
    }

}
