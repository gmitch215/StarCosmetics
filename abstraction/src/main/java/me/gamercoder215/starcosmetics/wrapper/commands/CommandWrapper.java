package me.gamercoder215.starcosmetics.wrapper.commands;

import com.google.common.collect.ImmutableMap;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface CommandWrapper {

    Map<String, List<String>> COMMANDS = ImmutableMap.<String, List<String>>builder()
            .put("starsettings", Arrays.asList("ssettings", "settings", "ss"))
            .put("starreload", Arrays.asList("sreload", "sr"))
            .build();

    Map<String, String> COMMAND_PERMISSION = ImmutableMap.<String, String>builder()
            .put("starsettings", "starcosmetics.user.settings")
            .put("starreload", "starcosmetics.admin.reloadconfig")
            .build();

    Map<String, String> COMMAND_DESCRIPTION = ImmutableMap.<String, String>builder()
            .put("starsettings", "Opens the StarCosmetics settings menu.")
            .put("starreload", "Reloads the StarCosmetics configuration.")
            .build();

    Map<String, String> COMMAND_USAGE = ImmutableMap.<String, String>builder()
            .put("starsettings", "/starsettings")
            .put("starreload", "/starreload")
            .build();

    // Command Methods

    default void settings(Player p) {

    }

    default void reloadConfig(Player p) {

    }

}
