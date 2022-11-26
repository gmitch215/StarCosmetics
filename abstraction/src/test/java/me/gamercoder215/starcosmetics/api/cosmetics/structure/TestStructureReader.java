package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestStructureReader {

    @Test
    @DisplayName("Test Raw Points")
    public void testRawPoints() {
        Assertions.assertEquals(new StructurePoint(1, 2, 3), StructureReader.readRawPoint("[1,2,3]"));
        Assertions.assertEquals(new StructurePoint(-2, 5, 8), StructureReader.readRawPoint("[ -2, 5, 8 ]"));
        Assertions.assertEquals(new StructurePoint(0, 0, 0), StructureReader.readRawPoint("[0, 0, 0]"));

        Assertions.assertThrows(MalformedStructureException.class, () -> StructureReader.readRawPoint("[1,2,3"));
        Assertions.assertThrows(MalformedStructureException.class, () -> StructureReader.readRawPoint("1,2,3]"));

        Assertions.assertEquals(new StructurePoint(3, 2, 1), StructureReader.readRawPoint("[3,2,1];"));
        Assertions.assertEquals(new StructurePoint(3, 2, 1), StructureReader.readRawPoint("[3,2,1]; "));
    }

    @Test
    @DisplayName("Test Multiple Raw Points")
    public void testMultipleRawPoints() {
        Assertions.assertEquals(
                Lists.newArrayList(new StructurePoint(1, 2, 3), new StructurePoint(-2, 5, 8)),
                StructureReader.readPoints("[1,2,3]*[-2, 5, 8]")
        );
        Assertions.assertEquals(
                Lists.newArrayList(new StructurePoint(1, 2, 3), new StructurePoint(-3, -9, 12), new StructurePoint(0, 0, 0)),
                StructureReader.readPoints("[1,2,3]*[-3, -9, 12]*[ 0, 0, 0 ]")
        );

        Assertions.assertThrows(MalformedStructureException.class, () -> StructureReader.readPoints("[1,2,3]*[-2, 5, 8"));
        Assertions.assertThrows(MalformedStructureException.class, () -> StructureReader.readPoints("[1,2,3*[-2, 5, 8]"));

        Assertions.assertEquals(
                Lists.newArrayList(new StructurePoint(1, 2, 3), new StructurePoint(-2, 5, 8)),
                StructureReader.readPoints("[1,2,3]*[-2, 5, 8];")
        );
        Assertions.assertEquals(
                Lists.newArrayList(new StructurePoint(1, 2, 3), new StructurePoint(-2, 5, 8)),
                StructureReader.readPoints("[1,2,3]*[-2, 5, 8]; ")
        );
    }

    @Test
    @DisplayName("Test Cuboid Points")
    public void testCuboidPoints() {
        Assertions.assertEquals(9, StructureReader.readPoints("(1,-1,0,0,1,-1)^[1, 2, 3]").size());
        Assertions.assertEquals(18, StructureReader.readPoints("(1,-1,0,0,1,-1)^[1, 2, 3]*(1,-1,0,0,1,-1)^[-3,5,7]").size());
    }

}
