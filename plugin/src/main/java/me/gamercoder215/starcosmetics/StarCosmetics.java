package me.gamercoder215.starcosmetics;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.wrapper.CommandWrapper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class StarCosmetics extends JavaPlugin implements StarConfig {

    private boolean checkCompatible() {
        if (!ServerVersion.isAllCompatible()) {
            getLogger().severe("StarCosmetics is not compatible with: " + Bukkit.getBukkitVersion());
            Bukkit.getPluginManager().disablePlugin(this);
            return false;
        }

        return true;
    }

    private static FileConfiguration config;

    @Override
    public void onEnable() {
        if (!checkCompatible()) return;
        saveDefaultConfig();

        config = StarConfig.loadConfig();
        getLogger().info("Loaded Files...");

        getCommandWrapper();

        getLogger().info("Done!");
    }

    // Config Implementation

    @Override
    public String getLanguage() {
        return config.getString("language", "en");
    }

    @Override
    public String get(String key) {
        Properties p = new Properties();
        String lang = getLanguage().equalsIgnoreCase("en") ? "" : getLanguage();

        try (InputStream str = getClass().getResourceAsStream("/starcosmetics" + lang + ".properties")) {
            p.load(str);
            str.close();
            return ChatColor.translateAlternateColorCodes('&', p.getProperty(key, "Unknown Value"));
        } catch (IOException e) {
            StarConfig.print(e);
            return "Unknown Value";
        }
    }

    @Override
    public String getMessage(String key) {
        return get("plugin.prefix") + get(key);
    }

    // Other Utilities

    public static void print(Throwable t) {
        StarConfig.print(t);
    }

    public static CommandWrapper getCommandWrapper() {
        StarCosmetics plugin = StarCosmetics.getPlugin(StarCosmetics.class);
        int cmdV = ServerVersion.getCurrent().getCommandVersion();

        try {
            return (CommandWrapper) Class.forName("me.gamercoder215.starcosmetics.wrapper.CommandWrapperV" + cmdV).getConstructor(Plugin.class).newInstance(plugin);
        } catch (ReflectiveOperationException e) {
            print(e);
            return null;
        }
    }



}
