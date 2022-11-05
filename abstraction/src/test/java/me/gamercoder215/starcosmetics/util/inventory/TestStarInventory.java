package me.gamercoder215.starcosmetics.util.inventory;

import org.bukkit.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class TestStarInventory {

    private static final SecureRandom r = new SecureRandom();

    @Test
    @DisplayName("Test Set Attribute")
    public void testSetAttribute() {
        StarInventory inv = new MockStarInventory("mockup");
        Assertions.assertEquals("mockup", inv.getKey());

        inv.setCancelled(true);
        Assertions.assertTrue(inv.isCancelled());

        inv.setAttribute("test", (BooleanSupplier) () -> true);
        Assertions.assertTrue(inv.getAttribute("test", BooleanSupplier.class).getAsBoolean());
    }

    @Test
    @DisplayName("Test Fetch Attributes")
    public void testFetchAttribute() {
        StarInventory inv = new MockStarInventory("mockup");
        Assertions.assertEquals("mockup", inv.getKey());

        inv.setAttribute("consumer1", (Consumer<Object>) o -> Assertions.assertEquals(o.hashCode(), 3));
        inv.setAttribute("map", new HashMap<>());
        inv.setAttribute("list", new ArrayList<>());

        inv.getAttribute("consumer1", Consumer.class).accept(new Object() {
            @Override
            public int hashCode() {
                return 3;
            }
        });

        Assertions.assertEquals(inv.getAttribute("map"), new HashMap<>());
        Assertions.assertEquals(inv.getAttribute("list"), new ArrayList<>());
    }

    @Test
    @DisplayName("Test Random Colors")
    public void testRandomColors() {
        for (int i = 0; i < 100; i++) Color.fromRGB(r.nextInt(16777216));
    }

}
