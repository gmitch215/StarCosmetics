package me.gamercoder215.starcosmetics;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TestStarCosmetics {

    private static StarCosmetics plugin;
    @TempDir
    private static File dataFolder;

    @BeforeAll
    public static void setup() {
        MockServer server = new MockServer();
        Bukkit.setServer(server);
        plugin = new StarCosmetics(
                new JavaPluginLoader(server),
                new PluginDescriptionFile("StarCosmetics", "1.0", "me.gamercoder215.starcosmetics.StarCosmetics"),
                dataFolder,
                null
        );

        File config = new File(dataFolder, "config.yml");
        StarCosmetics.config = YamlConfiguration.loadConfiguration(config);
    }

    @AfterAll
    public static void shutdown() throws Exception{
        Field server = Bukkit.class.getDeclaredField("server");
        server.setAccessible(true);
        server.set(null, null);
    }

    @Test
    @DisplayName("Test StarConfig#get")
    public void testGet() {
        Assertions.assertEquals("Commands", plugin.get("constants.commands"));
        Assertions.assertEquals("Hologram Format", plugin.get("settings.hologram_format"));
    }

    private static <T> void configTest(T newValue, Supplier<T> getter, Consumer<T> setter) {
        setter.accept(newValue);
        Assertions.assertEquals(newValue, getter.get());
    }

    @Test
    @DisplayName("Test Configuration")
    public void testConfiguration() {
        plugin.setLanguage("es");
        Assertions.assertEquals("es", plugin.getLanguage());
        Assertions.assertEquals("Comandos", plugin.get("constants.commands"));

        plugin.setLanguage("it");
        Assertions.assertEquals("it", plugin.getLanguage());
        Assertions.assertEquals(Locale.ITALIAN, plugin.getLocale());

        configTest(10, plugin::getMaxHologramLimit, plugin::setMaxHologramLimit);
        configTest(5L, plugin::getBlockDisappearTime, plugin::setBlockDisappearTime);
        configTest(6L, plugin::getEntityDisappearTime, plugin::setEntityDisappearTime);
        configTest(11L, plugin::getItemDisappearTime, plugin::setItemDisappearTime);
        configTest(2.5, plugin::getRequirementMultiplier, plugin::setRequirementMultiplier);
        configTest(false, plugin::isAmbientPetSoundEnabled, plugin::setAmbientPetSoundEnabled);
    }

}
