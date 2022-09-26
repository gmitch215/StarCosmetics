package me.gamercoder215.starcosmetics.wrapper.commands;

import me.gamercoder215.starcosmetics.api.StarConfig;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Usage;
import revxrsal.commands.bukkit.BukkitCommandHandler;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.util.Locale;

public class CommandWrapperV2 implements CommandWrapper {

    private static BukkitCommandHandler handler;

    public CommandWrapperV2(Plugin plugin) {
        if (handler != null) return;
        handler = BukkitCommandHandler.create(plugin);

        handler.register(this);
        handler.registerBrigadier();
        handler.setLocale(new Locale(StarConfig.getConfig().getLanguage()));

        plugin.getLogger().info("Loaded Command Wrapper V2 (1.13+)");
    }

    @Override
    @Command({"starsettings", "ssettings", "settings", "ss"})
    @Description("Opens the StarCosmetics settings menu.")
    @Usage("/starsettings")
    @CommandPermission("starcosmetics.user.settings")
    public void settings(Player p) {
        CommandWrapper.super.settings(p);
    }

    @Override
    @Command({"starreload", "sreload", "sr"})
    @Description("Reloads the StarCosmetics configuration.")
    @Usage("/starreload")
    @CommandPermission("starcosmetics.admin.reloadconfig")
    public void reloadConfig(Player p) {
        CommandWrapper.super.reloadConfig(p);
    }
}