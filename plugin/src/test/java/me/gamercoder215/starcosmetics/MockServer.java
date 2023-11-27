package me.gamercoder215.starcosmetics;

import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.*;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.*;
import org.bukkit.loot.LootTable;
import org.bukkit.map.MapView;
import org.bukkit.packs.DataPackManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.structure.StructureManager;
import org.bukkit.util.CachedServerIcon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.InetAddress;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Logger;

@SuppressWarnings({"unchecked", "UnstableApiUsage"})
public class MockServer implements Server {
    @Override
    public String getName() {
        return "TestServer";
    }

    @Override
    public String getVersion() {
        return "1.20.2";
    }

    @Override
    public String getBukkitVersion() {
        return "1.20.2-R0.1-SNAPSHOT";
    }

    @Override
    public Collection<? extends Player> getOnlinePlayers() {
        return null;
    }

    @Override
    public int getMaxPlayers() {
        return 0;
    }

    @Override
    public void setMaxPlayers(int maxPlayers) {}

    @Override
    public int getPort() {
        return 0;
    }

    @Override
    public int getViewDistance() {
        return 0;
    }

    @Override
    public int getSimulationDistance() {
        return 0;
    }

    @Override
    public String getIp() {
        return null;
    }

    @Override
    public String getWorldType() {
        return null;
    }

    @Override
    public boolean getGenerateStructures() {
        return false;
    }

    @Override
    public int getMaxWorldSize() {
        return 0;
    }

    @Override
    public boolean getAllowEnd() {
        return false;
    }

    @Override
    public boolean getAllowNether() {
        return false;
    }

    @Override
    public List<String> getInitialEnabledPacks() {
        return null;
    }

    @Override
    public List<String> getInitialDisabledPacks() {
        return null;
    }

    @Override
    public DataPackManager getDataPackManager() {return null;}

    @Override
    public String getResourcePack() {
        return null;
    }

    @Override
    public String getResourcePackHash() {
        return null;
    }

    @Override
    public String getResourcePackPrompt() {
        return null;
    }

    @Override
    public boolean isResourcePackRequired() {
        return false;
    }

    @Override
    public boolean hasWhitelist() {
        return false;
    }

    @Override
    public void setWhitelist(boolean value) {

    }

    @Override
    public boolean isWhitelistEnforced() {
        return false;
    }

    @Override
    public void setWhitelistEnforced(boolean value) {

    }

    @Override
    public Set<OfflinePlayer> getWhitelistedPlayers() {
        return null;
    }

    @Override
    public void reloadWhitelist() {

    }

    @Override
    public int broadcastMessage(String message) {
        return 0;
    }

    @Override
    public String getUpdateFolder() {
        return null;
    }

    @Override
    public File getUpdateFolderFile() {
        return null;
    }

    @Override
    public long getConnectionThrottle() {
        return 0;
    }

    @Override
    public int getTicksPerAnimalSpawns() {
        return 0;
    }

    @Override
    public int getTicksPerMonsterSpawns() {
        return 0;
    }

    @Override
    public int getTicksPerWaterSpawns() {
        return 0;
    }

    @Override
    public int getTicksPerWaterAmbientSpawns() {
        return 0;
    }

    @Override
    public int getTicksPerWaterUndergroundCreatureSpawns() {
        return 0;
    }

    @Override
    public int getTicksPerAmbientSpawns() {
        return 0;
    }

    @Override
    public int getTicksPerSpawns(SpawnCategory spawnCategory) {
        return 0;
    }

    @Override
    public Player getPlayer(String name) {
        return null;
    }

    @Override
    public Player getPlayerExact(String name) {
        return null;
    }

    @Override
    public List<Player> matchPlayer(String name) {
        return null;
    }

    @Override
    public Player getPlayer(UUID id) {
        return null;
    }

    @Override
    public PluginManager getPluginManager() {
        return null;
    }

    @Override
    public BukkitScheduler getScheduler() {
        return null;
    }

    @Override
    public ServicesManager getServicesManager() {
        return null;
    }

    @Override
    public List<World> getWorlds() {
        return null;
    }

    @Override
    public World createWorld(WorldCreator creator) {
        return null;
    }

    @Override
    public boolean unloadWorld(String name, boolean save) {
        return false;
    }

    @Override
    public boolean unloadWorld(World world, boolean save) {
        return false;
    }

    @Override
    public World getWorld(String name) {
        return null;
    }

    @Override
    public World getWorld(UUID uid) {
        return null;
    }

    @Override
    public WorldBorder createWorldBorder() {
        return null;
    }

    @Override
    public MapView getMap(int id) {
        return null;
    }

    @Override
    public MapView createMap(World world) {
        return null;
    }

    @Override
    public ItemStack createExplorerMap(World world, Location location, StructureType structureType) {
        return null;
    }

    @Override
    public ItemStack createExplorerMap(World world, Location location, StructureType structureType, int radius, boolean findUnexplored) {
        return null;
    }

    @Override
    public void reload() {

    }

    @Override
    public void reloadData() {

    }

    @Override
    public Logger getLogger() {
        return Logger.getLogger("Test Server");
    }

    @Override
    public PluginCommand getPluginCommand(String name) {
        return null;
    }

    @Override
    public void savePlayers() {}

    @Override
    public boolean dispatchCommand(CommandSender sender, String commandLine) throws CommandException {
        return false;
    }

    @Override
    public boolean addRecipe(Recipe recipe) {
        return false;
    }

    @Override
    public List<Recipe> getRecipesFor(ItemStack result) {
        return null;
    }

    @Override
    public Recipe getRecipe(NamespacedKey recipeKey) {
        return null;
    }

    @Override
    public Recipe getCraftingRecipe(ItemStack[] craftingMatrix, World world) {
        return null;
    }

    @Override
    public ItemStack craftItem(ItemStack[] craftingMatrix, World world, Player player) {
        return null;
    }

    @Override
    public ItemStack craftItem(ItemStack[] itemStacks, World world) {
        return null;
    }

    @Override
    public ItemCraftResult craftItemResult(ItemStack[] itemStacks, World world, Player player) {
        return null;
    }

    @Override
    public ItemCraftResult craftItemResult(ItemStack[] itemStacks, World world) {
        return null;
    }

    @Override
    public Iterator<Recipe> recipeIterator() {
        return null;
    }

    @Override
    public void clearRecipes() {

    }

    @Override
    public void resetRecipes() {

    }

    @Override
    public boolean removeRecipe(NamespacedKey key) {
        return false;
    }

    @Override
    public Map<String, String[]> getCommandAliases() {
        return null;
    }

    @Override
    public int getSpawnRadius() {
        return 0;
    }

    @Override
    public void setSpawnRadius(int value) {

    }

    @Override
    public boolean shouldSendChatPreviews() {
        return false;
    }

    @Override
    public boolean isEnforcingSecureProfiles() {
        return false;
    }

    @Override
    public boolean getHideOnlinePlayers() {
        return false;
    }

    @Override
    public boolean getOnlineMode() {
        return false;
    }

    @Override
    public boolean getAllowFlight() {
        return false;
    }

    @Override
    public boolean isHardcore() {
        return false;
    }

    @Override
    public void shutdown() {

    }

    @Override
    public int broadcast(String message, String permission) {
        return 0;
    }

    @Override
    public OfflinePlayer getOfflinePlayer(String name) {
        return null;
    }

    @Override
    public OfflinePlayer getOfflinePlayer(UUID id) {
        return null;
    }

    @Override
    public PlayerProfile createPlayerProfile(UUID uniqueId, String name) {
        return null;
    }

    @Override
    public PlayerProfile createPlayerProfile(UUID uniqueId) {
        return null;
    }

    @Override
    public PlayerProfile createPlayerProfile(String name) {
        return null;
    }

    @Override
    public Set<String> getIPBans() {
        return null;
    }

    @Override
    public void banIP(String address) {

    }

    @Override
    public void unbanIP(String address) {

    }

    @Override
    public void banIP(InetAddress address) {

    }

    @Override
    public void unbanIP(InetAddress address) {

    }

    @Override
    public Set<OfflinePlayer> getBannedPlayers() {
        return null;
    }

    @Override
    public BanList<?> getBanList(BanList.Type type) {
        return null;
    }

    @Override
    public Set<OfflinePlayer> getOperators() {
        return null;
    }

    @Override
    public GameMode getDefaultGameMode() {
        return null;
    }

    @Override
    public void setDefaultGameMode(GameMode mode) {

    }

    @Override
    public ConsoleCommandSender getConsoleSender() {
        return null;
    }

    @Override
    public File getWorldContainer() {
        return null;
    }

    @Override
    public OfflinePlayer[] getOfflinePlayers() {
        return new OfflinePlayer[0];
    }

    @Override
    public Messenger getMessenger() {
        return null;
    }

    @Override
    public HelpMap getHelpMap() {
        return null;
    }

    @Override
    public Inventory createInventory(InventoryHolder owner, InventoryType type) {
        return null;
    }

    @Override
    public Inventory createInventory(InventoryHolder owner, InventoryType type, String title) {
        return null;
    }

    @Override
    public Inventory createInventory(InventoryHolder owner, int size) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Inventory createInventory(InventoryHolder owner, int size, String title) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Merchant createMerchant(String title) {
        return null;
    }

    @Override
    public int getMaxChainedNeighborUpdates() {
        return 0;
    }

    @Override
    public int getMonsterSpawnLimit() {
        return 0;
    }

    @Override
    public int getAnimalSpawnLimit() {
        return 0;
    }

    @Override
    public int getWaterAnimalSpawnLimit() {
        return 0;
    }

    @Override
    public int getWaterAmbientSpawnLimit() {
        return 0;
    }

    @Override
    public int getWaterUndergroundCreatureSpawnLimit() {
        return 0;
    }

    @Override
    public int getAmbientSpawnLimit() {
        return 0;
    }

    @Override
    public int getSpawnLimit(SpawnCategory spawnCategory) {
        return 0;
    }

    @Override
    public boolean isPrimaryThread() {
        return false;
    }

    @Override
    public String getMotd() {
        return null;
    }

    @Override
    public void setMotd(String motd) {

    }

    @Override
    public String getShutdownMessage() {
        return null;
    }

    @Override
    public Warning.WarningState getWarningState() {
        return null;
    }

    @Override
    public ItemFactory getItemFactory() {
        return null;
    }

    @Override
    public ScoreboardManager getScoreboardManager() {
        return null;
    }

    @Override
    public Criteria getScoreboardCriteria(String name) {
        return null;
    }

    @Override
    public CachedServerIcon getServerIcon() {
        return null;
    }

    @Override
    public CachedServerIcon loadServerIcon(File file) {
        return null;
    }

    @Override
    public CachedServerIcon loadServerIcon(BufferedImage image) {
        return null;
    }

    @Override
    public void setIdleTimeout(int threshold) {}

    @Override
    public int getIdleTimeout() {
        return 0;
    }

    @Override
    public ChunkGenerator.ChunkData createChunkData(World world) {
        return null;
    }

    @Override
    public BossBar createBossBar(String title, BarColor color, BarStyle style, BarFlag... flags) {
        return null;
    }

    @Override
    public KeyedBossBar createBossBar(NamespacedKey key, String title, BarColor color, BarStyle style, BarFlag... flags) {
        return null;
    }

    @Override
    public Iterator<KeyedBossBar> getBossBars() {
        return null;
    }

    @Override
    public KeyedBossBar getBossBar(NamespacedKey key) {
        return null;
    }

    @Override
    public boolean removeBossBar(NamespacedKey key) {
        return false;
    }

    @Override
    public Entity getEntity(UUID uuid) {
        return null;
    }

    @Override
    public Advancement getAdvancement(NamespacedKey key) {
        return null;
    }

    @Override
    public Iterator<Advancement> advancementIterator() {
        return null;
    }

    @Override
    public BlockData createBlockData(Material material) {
        return null;
    }

    @Override
    public BlockData createBlockData(Material material, Consumer<? super BlockData> consumer) {
        return null;
    }

    @Override
    public BlockData createBlockData(String data) throws IllegalArgumentException {
        return null;
    }

    @Override
    public BlockData createBlockData(Material material, String data) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends Keyed> Tag<T> getTag(String registry, NamespacedKey tag, Class<T> clazz) {
        return null;
    }

    @Override
    public <T extends Keyed> Iterable<Tag<T>> getTags(String registry, Class<T> clazz) {
        return null;
    }

    @Override
    public LootTable getLootTable(NamespacedKey key) {
        return null;
    }

    @Override
    public List<Entity> selectEntities(CommandSender sender, String selector) throws IllegalArgumentException {
        return null;
    }

    @Override
    public StructureManager getStructureManager() {
        return null;
    }

    @Override
    public <T extends Keyed> Registry<T> getRegistry(Class<T> tClass) {
        return null;
    }

    @Override
    public UnsafeValues getUnsafe() {
        return null;
    }

    @Override
    public Spigot spigot() {
        return null;
    }

    @Override
    public void sendPluginMessage(Plugin source, String channel, byte[] message) {}

    @Override
    public Set<String> getListeningPluginChannels() {
        return null;
    }
}
