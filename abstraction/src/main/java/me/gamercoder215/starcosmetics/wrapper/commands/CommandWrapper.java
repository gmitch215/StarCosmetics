package me.gamercoder215.starcosmetics.wrapper.commands;

import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.util.StarSound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static me.gamercoder215.starcosmetics.wrapper.Wrapper.send;

public interface CommandWrapper {

    Map<String, List<String>> COMMANDS = ImmutableMap.<String, List<String>>builder()
            .put("starsettings", Arrays.asList("ssettings", "settings", "ss"))
            .put("starreload", Arrays.asList("sreload", "sr"))
            .put("starcosmetics", Arrays.asList("scosmetics", "sc"))
            .build();

    Map<String, String> COMMAND_PERMISSION = ImmutableMap.<String, String>builder()
            .put("starsettings", "starcosmetics.user.settings")
            .put("starreload", "starcosmetics.admin.reloadconfig")
            .put("starcosmetics", "starcosmetics.user.cosmetics")
            .build();

    Map<String, String> COMMAND_DESCRIPTION = ImmutableMap.<String, String>builder()
            .put("starsettings", "Opens the StarCosmetics settings menu.")
            .put("starreload", "Reloads the StarCosmetics configuration.")
            .put("starcosmetics", "Opens the StarCosmetics menu.")
            .build();

    Map<String, String> COMMAND_USAGE = ImmutableMap.<String, String>builder()
            .put("starsettings", "/starsettings")
            .put("starreload", "/starreload")
            .put("starcosmetics", "/starcosmetics")
            .build();

    // Command Methods

    default void settings(Player p) {

    }

    default void reloadConfig(CommandSender sender) {
        if (!sender.hasPermission("starcosmetics.admin.reloadconfig")) {
            send(sender, "error.permission");
            return;
        }

        send(sender, "command.reload.reloading");
        Plugin plugin = StarConfig.getPlugin();

        plugin.reloadConfig();

        send(sender, "command.reload.reloaded");
        if (sender instanceof Player) StarSound.ENTITY_ARROW_HIT_PLAYER.play((Player) sender, 3F, 2F);
    }

    default void openCosmetics(Player p) {

    }

}
