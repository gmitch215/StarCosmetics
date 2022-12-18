package me.gamercoder215.starcosmetics.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TestStarUtil {

    @Test
    @DisplayName("Test Levenshtein Distance")
    public void testLevenshteinDistance() {
        Assertions.assertEquals(StarUtil.levenshteinDistance("test", "test"), 0);
        Assertions.assertEquals(StarUtil.levenshteinDistance("test", "test1"), 1);
        Assertions.assertEquals(StarUtil.levenshteinDistance("test", "est"), 1);

        // Advanced

        String s1 = "superlight";
        List<String> l1 = Arrays.asList("light", "superlight", "super");
        l1.sort(Comparator.comparingInt(s -> StarUtil.levenshteinDistance(s1, s)));
        Assertions.assertEquals(l1.get(0), "superlight");

        String s2 = "ultravioletrain";
        List<String> l2 = Arrays.asList("ultraviolet", "violet", "rain", "ultrarain");
        l2.sort(Comparator.comparingInt(s -> StarUtil.levenshteinDistance(s2, s)));
        Assertions.assertEquals(l2.get(0), "ultraviolet");

        Assertions.assertEquals(-1, StarUtil.levenshteinDistance("test", null));
        Assertions.assertEquals(-1, StarUtil.levenshteinDistance(null, "test"));
    }

}
