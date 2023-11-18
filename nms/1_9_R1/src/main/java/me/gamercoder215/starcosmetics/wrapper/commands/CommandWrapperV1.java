package me.gamercoder215.starcosmetics.wrapper.commands;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.TrailType;
import me.gamercoder215.starcosmetics.util.StarSound;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static me.gamercoder215.starcosmetics.wrapper.Wrapper.sendError;

@SuppressWarnings("unchecked")
final class CommandWrapperV1 implements CommandWrapper, CommandExecutor {

    private final Plugin plugin;

    public CommandWrapperV1(Plugin plugin) {
        this.plugin = plugin;
        loadCommands();
        plugin.getLogger().info("Loaded Command Wrapper V1 (1.9+)");
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
            case "cosmetics": {
                if (!(sender instanceof Player)) return false;
                Player p = (Player) sender;

                if (args.length < 1) {
                    cosmetics(p);
                    StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
                    return true;
                }

                switch (args[0].toLowerCase()) {
                    case "pet":
                    case "pets": pets(p, Arrays.copyOfRange(args, 1, args.length)); break;

                    case "trails": trails(p); break;

                    case "particleshapes":
                    case "particles":
                    case "shapes": shapes(p); break;

                    case "structures":
                    case "structure": {
                        StringBuilder structure = new StringBuilder();
                        for (String arg : args) structure.append(arg).append(" ");

                        structures(p, structure.toString());
                        break;
                    }
                    case "customsounds":
                    case "sounds": soundSelection(p, args); break;

                    case "hat":
                    case "hats": hats(p); break;
                    case "info":
                    case "equipped": {
                        if (args.length < 2) {
                            sendError(p, "error.argument.cosmetic");
                            return true;
                        }

                        Object o = PARENTS_INFO.get(args[1]);
                        if (o == null) {
                            sendError(p, "error.argument.cosmetic");
                            return true;
                        }

                        if (o instanceof TrailType) cosmeticInfo(p, (TrailType) o);
                        else cosmeticInfo(p, (Class<? extends Cosmetic>) o);
                    }
                    case "hologram": {
                        hologramInfo(p);
                        break;
                    }
                    default: {
                        sendError(p, "error.argument");
                        break;
                    }
                }

                break;
            }
            case "starrabout": {
                if (!(sender instanceof Player)) return false;
                Player p = (Player) sender;

                about(p);
                break;
            }
            case "starstructures": {
                if (!(sender instanceof Player)) return false;
                Player p = (Player) sender;

                StringBuilder structure = new StringBuilder();
                for (String arg : args) structure.append(arg).append(" ");

                structures(p, structure.toString());
                break;
            }
            case "starpets": {
                if (!(sender instanceof Player)) return false;
                Player p = (Player) sender;

                pets(p, args);
                break;
            }
            case "starcosmeticinfo": {
                if (!(sender instanceof Player)) return false;
                Player p = (Player) sender;

                if (args.length < 1) {
                    sendError(p, "error.argument.cosmetic");
                    return true;
                }

                Object o = PARENTS_INFO.get(args[0]);
                if (o == null) {
                    sendError(p, "error.argument.cosmetic");
                    return true;
                }

                if (o instanceof TrailType) cosmeticInfo(p, (TrailType) o);
                else cosmeticInfo(p, (Class<? extends Cosmetic>) o);
            }
            case "starhologram": {
                if (!(sender instanceof Player)) return false;
                hologramInfo((Player) sender);
                break;
            }
            case "starcapes": {
                if (!(sender instanceof Player)) return false;
                capes((Player) sender, args.length == 0 ? null : args[0]);
                break;
            }
            case "staremote": {
                if (!(sender instanceof Player)) return false;
                emote((Player) sender, args.length == 0 ? null : args[0]);
                break;
            }
        }

        return true;
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
            pcmd.setDescription(desc);
            if (COMMAND_PERMISSION.get(cmd) != null) pcmd.setPermission(COMMAND_PERMISSION.get(cmd));

            register(pcmd);
        }
    }

}
