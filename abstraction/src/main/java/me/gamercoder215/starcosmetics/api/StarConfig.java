package me.gamercoder215.starcosmetics.api;

import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import me.gamercoder215.starcosmetics.wrapper.commands.CommandWrapper;
import me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public interface StarConfig {

    static Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin("StarCosmetics");
    }

    static StarConfig getConfig() {
        return (StarConfig) getPlugin();
    }

    static void print(Throwable t) {
        getLogger().severe(t.getClass().getSimpleName());
        getLogger().severe("--------------------------");
        getLogger().severe(t.getMessage());
        for (StackTraceElement s : t.getStackTrace()) getLogger().severe(s.toString());
    }

    static FileConfiguration getConfiguration() {
        if (!getConfigurationFile().exists()) try {
            getConfigurationFile().createNewFile();
        } catch (IOException e) {
            print(e);
        }

        return getPlugin().getConfig();
    }

    static File getDataFolder() {
        return getPlugin().getDataFolder();
    }

    static Logger getLogger() {
        return getPlugin().getLogger();
    }

    static File getConfigurationFile() {
        return new File(getDataFolder(), "config.yml");
    }

    static FileConfiguration loadConfig() {
        FileConfiguration config = getConfiguration();

        if (!config.isString("language")) config.set("language", "en");

        try {
            config.save(getConfigurationFile());
        } catch (IOException e) {
            print(e);
        }

        return config;
    }

    String getLanguage();

    String get(String key);

    String getMessage(String key);

    static boolean isLegacy() {
        return ServerVersion.getCurrent() == ServerVersion.LEGACY;
    }

    static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
    }

    enum ServerVersion {
        LEGACY(1,
                "1_9_R1",
                "1_9_R2",
                "1_10_R1",
                "1_11_R1",
                "1_12_R1"),

        CONTEMPORARY(2,
                "1_13_R1",
                "1_13_R2",
                "1_14_R1",
                "1_15_R1",
                "1_16_R1",
                "1_16_R2",
                "1_16_R3",
                "1_17_R1",
                "1_18_R1",
                "1_18_R2",
                "1_19_R1"),

        UNKNOWN(1)
        ;

        private final String[] compatibleVersions;

        private final int cmdV;

        ServerVersion(int cmdV, String... compatibleVersions) {
            this.compatibleVersions = compatibleVersions;
            this.cmdV = cmdV;
        }

        public int getCommandVersion() {
            return this.cmdV;
        }

        public boolean isCompatible() {
            for (String version : compatibleVersions) if (version.equals(getServerVersion())) return true;
            return false;
        }

        public static boolean isAllCompatible() {
            for (ServerVersion version : values()) if (version.isCompatible()) return true;
            return false;
        }

        public static ServerVersion getByVersion(String version) {
            for (ServerVersion srvV : values())
                for (String v : srvV.compatibleVersions) if (v.equals(version)) return srvV;
            return UNKNOWN;
        }

        public static ServerVersion getCurrent() {
             return getByVersion(getServerVersion());
        }

    }

   static CommandWrapper getCommandWrapper() {
        int cmdV = ServerVersion.getCurrent().getCommandVersion();

        try {
            return (CommandWrapper) Class.forName("me.gamercoder215.starcosmetics.wrapper.CommandWrapperV" + cmdV)
                    .getConstructor(Plugin.class)
                    .newInstance(getPlugin());
        } catch (ReflectiveOperationException e) {
            print(e);
            return null;
        }
    }

    static CosmeticSelections getCosmeticSelections() {
        String cosmeticV = getServerVersion().split("_")[0] + "_" + getServerVersion().split("_")[1];
        try {
            return (CosmeticSelections) Class.forName("me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections" + cosmeticV)
                    .getConstructor()
                    .newInstance();
        } catch (ReflectiveOperationException e) {
            print(e);
            return null;
        }
    }

    static Wrapper getWrapper() {
        try {
            return (Wrapper) Class.forName("me.gamercoder215.starcosmetics.wrapper.Wrapper" + StarConfig.getServerVersion())
                    .getConstructor()
                    .newInstance();
        } catch (ReflectiveOperationException e) {
            print(e);
        } catch (ArrayIndexOutOfBoundsException ignored) {} // Using test server

        return null;
    }
}
