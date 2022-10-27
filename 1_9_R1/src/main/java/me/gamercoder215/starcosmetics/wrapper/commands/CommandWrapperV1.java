package me.gamercoder215.starcosmetics.wrapper.commands;

import me.gamercoder215.starcosmetics.api.StarConfig;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CommandWrapperV1 implements CommandWrapper, TabExecutor {

    private final Plugin plugin;

    public CommandWrapperV1(Plugin plugin) {
        this.plugin = plugin;
        loadCommands();
        plugin.getLogger().info("Loaded Command Wrapper V1 (1.9+)");
    }

    private PluginCommand createCommand(String name, String... aliases) {
        try {
            Constructor<PluginCommand> p = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            p.setAccessible(true);

            PluginCommand cmd = p.newInstance(name, plugin);
            if (aliases != null && aliases.length > 0) cmd.setAliases(Arrays.asList(aliases));
            return cmd;
        } catch (Exception e) {
            StarConfig.print(e);
            return null;
        }
    }

    private void register(PluginCommand cmd) {
        try {
            Server srv = Bukkit.getServer();
            Field bukkitmap = srv.getClass().getDeclaredField("commandMap");
            bukkitmap.setAccessible(true);

            CommandMap map = (CommandMap) bukkitmap.get(srv);
            map.register(cmd.getName(), cmd);
        } catch (Exception e) {
            StarConfig.print(e);
        }
    }

    public void loadCommands() {
        for (String cmd : COMMANDS.keySet()) {
            List<String> aliases = COMMANDS.get(cmd);
            String desc = COMMAND_DESCRIPTION.get(cmd);
            String usage = COMMAND_USAGE.get(cmd);

            PluginCommand pcmd = createCommand(cmd, aliases.toArray(new String[0]));

            if (pcmd == null) {
                StarConfig.getLogger().severe("Error loading command: " + cmd + " ; !! PLEASE REPORT !!");
                continue;
            }

            pcmd.setExecutor(this);
            pcmd.setUsage(usage);
            pcmd.setTabCompleter(this);
            pcmd.setDescription(desc);
            if (COMMAND_PERMISSION.get(cmd) != null) pcmd.setPermission(COMMAND_PERMISSION.get(cmd));

            register(pcmd);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        switch (cmd.getName()) {
            case "starreload": {
                reloadConfig(sender);
                break;
            }
            case "starsettings": {
                if (!(sender instanceof Player)) return false;
                Player p = (Player) sender;

                settings(p);
                break;
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();

        switch (cmd.getName()) {

        }

        return suggestions;
    }

}
