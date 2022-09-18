package me.gamercoder215.starcosmetics.wrapper;

import me.gamercoder215.starcosmetics.api.StarConfig;
import org.bukkit.plugin.Plugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;

import java.util.Locale;

public class CommandWrapperV2 implements CommandWrapper {

    private static BukkitCommandHandler handler;

    public CommandWrapperV2(Plugin plugin) {
        if (handler != null) return;



        handler.register(this);
        handler.registerBrigadier();
        handler.setLocale(new Locale(StarConfig.getConfig().getLanguage()));

        plugin.getLogger().info("Loaded Command Wrapper V2 (1.13+)");
    }

}
