package me.gamercoder215.starcosmetics.util;

import org.bukkit.ChatColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestStarChat {

    @Test
    @DisplayName("Text Hex Message")
    public void testHexMessage() {
        Assertions.assertEquals(StarChat.hexMessage("ff0000", ""), ChatColor.translateAlternateColorCodes('&', "&x&f&f&0&0&0&0"));
    }

}
