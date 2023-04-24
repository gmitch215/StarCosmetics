package me.gamercoder215.starcosmetics.wrapper.commands;

import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.*;
import me.gamercoder215.starcosmetics.api.cosmetics.hat.Hat;
import me.gamercoder215.starcosmetics.api.cosmetics.particle.ParticleShape;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetType;
import me.gamercoder215.starcosmetics.api.cosmetics.structure.StructureInfo;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.Trail;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.TrailType;
import me.gamercoder215.starcosmetics.api.player.PlayerSetting;
import me.gamercoder215.starcosmetics.api.player.SoundEventSelection;
import me.gamercoder215.starcosmetics.api.player.StarPlayer;
import me.gamercoder215.starcosmetics.api.player.StarPlayerUtil;
import me.gamercoder215.starcosmetics.util.*;
import me.gamercoder215.starcosmetics.util.inventory.InventorySelector;
import me.gamercoder215.starcosmetics.util.inventory.ItemBuilder;
import me.gamercoder215.starcosmetics.util.inventory.StarInventory;
import me.gamercoder215.starcosmetics.util.inventory.StarInventoryUtil;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static me.gamercoder215.starcosmetics.util.Generator.cw;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.*;
import static me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper.of;

public interface CommandWrapper {

    Map<String, List<String>> COMMANDS = ImmutableMap.<String, List<String>>builder()
            .put("starsettings", Arrays.asList("ssettings", "settings", "ss"))
            .put("starreload", Arrays.asList("sreload", "sr"))
            .put("starcosmetics", Arrays.asList("scosmetics", "sc", "cosmetics", "cs"))
            .put("starabout", Arrays.asList("sabout", "sa", "stara"))
            .put("starstructures", Arrays.asList("sstructures", "sstr"))
            .put("starpets", Arrays.asList("starp", "sp", "spets", "pets"))
            .put("starhelp", Collections.singletonList("shelp"))
            .build();

    Map<String, String> COMMAND_PERMISSION = ImmutableMap.<String, String>builder()
            .put("starsettings", "starcosmetics.user.settings")
            .put("starreload", "starcosmetics.admin.reloadconfig")
            .put("starcosmetics", "starcosmetics.user.cosmetics")
            .put("starstructures", "starcosmetics.user.cosmetics")
            .put("starpets", "starcosmetics.user.cosmetics")
            .build();

    Map<String, String> COMMAND_DESCRIPTION = ImmutableMap.<String, String>builder()
            .put("starsettings", "Opens the StarCosmetics settings menu.")
            .put("starreload", "Reloads the StarCosmetics configuration.")
            .put("starcosmetics", "Opens the StarCosmetics menu.")
            .put("starabout", "Displays information about StarCosmetics.")
            .put("starstructures", "Opens the StarCosmetics structures menu.")
            .put("starpets", "Opens the StarCosmetics pets menu.")
            .put("starhelp", "Displays help for StarCosmetics.")
            .build();

    Map<String, String> COMMAND_USAGE = ImmutableMap.<String, String>builder()
            .put("starsettings", "/starsettings")
            .put("starreload", "/starreload")
            .put("starcosmetics", "/starcosmetics")
            .put("starabout", "/starabout")
            .put("starstructures", "/starstructures [structure]")
            .put("starpets", "/starpets [remove|spawn]")
            .put("starhelp", "/starhelp")
            .build();

    // Command Methods

    default void settings(Player p) {
        p.openInventory(Generator.createSettingsInventory(p));
        StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
    }

    default void reloadConfig(CommandSender sender) {
        if (!sender.hasPermission("starcosmetics.admin.reloadconfig")) {
            sendError(sender, "error.permission");
            return;
        }

        sender.sendMessage(ChatColor.GOLD + get("command.reload.reloading"));
        Plugin plugin = StarConfig.getPlugin();

        plugin.reloadConfig();
        StarConfig.updateCache();

        Bukkit.getOnlinePlayers().forEach(StarPlayer::new);

        sender.sendMessage(ChatColor.YELLOW + get("command.reload.reloaded"));
        if (sender instanceof Player) StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess((Player) sender);
    }

    default void about(Player p) {
        StarInventory inv = Generator.genGUI("about", 27, get("menu.about"));
        inv.setCancelled();

        ItemStack head = Generator.generateHead(p);
        ItemMeta hMeta = head.getItemMeta();
        hMeta.setDisplayName(ChatColor.AQUA + get("menu.about.head"));
        head.setItemMeta(hMeta);
        inv.setItem(4, head);

        ItemStack cosmetics = StarMaterial.RED_CONCRETE.findStack();
        ItemMeta cMeta = cosmetics.getItemMeta();
        cMeta.setDisplayName(ChatColor.GOLD + get("menu.about.cosmetics"));

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + getWithArgs("menu.about.projectile_trail_count", comma(getCosmeticCount(TrailType.PROJECTILE)) ));
        lore.add(ChatColor.DARK_GREEN + getWithArgs("menu.about.ground_trail_count", comma(getCosmeticCount(TrailType.GROUND)) ));
        lore.add(ChatColor.AQUA + getWithArgs("menu.about.sound_trail_count", comma(getCosmeticCount(TrailType.PROJECTILE_SOUND)) ));

        lore.add(" ");

        lore.add(ChatColor.DARK_PURPLE + getWithArgs("menu.about.particle_shape_count", comma(getCosmeticCount(ParticleShape.class)) ));
        lore.add(ChatColor.DARK_AQUA + getWithArgs("menu.about.structure_count", comma(StarConfig.getRegistry().getAvailableStructures().size()) ));
        lore.add(ChatColor.LIGHT_PURPLE + getWithArgs("menu.about.pet_count", comma(PetType.values().length)));
        lore.add(ChatColor.GREEN + getWithArgs("menu.about.hat_count", comma(getCosmeticCount(Hat.class))));

        lore.add(" ");

        lore.add(ChatColor.RED + getWithArgs("menu.about.total_cosmetic_count", comma(getCosmeticCount()) ));
        cMeta.setLore(lore);

        cosmetics.setItemMeta(cMeta);
        inv.setItem(10, cosmetics);

        inv.setItem(12,
                ItemBuilder.ofHead("github")
                        .name(ChatColor.DARK_GRAY + "GitHub")
                        .id("about_link")
                        .nbt(nbt -> {
                            nbt.set("link", "https://github.com/GamerCoder215/StarCosmetics");
                            nbt.set("message_id", "menu.about.message.github");
                            nbt.set("color", "555555");
                        })
                        .build());

        inv.setItem(13,
                ItemBuilder.ofHead("discord")
                        .name(StarChat.hexMessage("5865F2", "Discord"))
                        .id("about_link")
                        .nbt(nbt -> {
                            nbt.set("link", "https://discord.gg/WVFNWEvuqX");
                            nbt.set("message_id", "menu.about.message.discord");
                            nbt.set("color", "5865F2");
                        })
                        .build());

        inv.setItem(14,
                ItemBuilder.ofHead("youtube")
                        .name(ChatColor.RED + "YouTube")
                        .id("about_link")
                        .nbt(nbt -> {
                            nbt.set("link", "https://www.youtube.com/@GamerCoder215");
                            nbt.set("message_id", "menu.about.message.youtube");
                            nbt.set("color", "FF5555");
                        })
                        .build());

        inv.setItem(15,
                ItemBuilder.ofHead("twitter")
                        .name(StarChat.hexMessage("1DA1F2", "Twitter"))
                        .id("about_link")
                        .nbt(nbt -> {
                            nbt.set("link", "https://twitter.com/TeamInceptus");
                            nbt.set("message_id", "menu.about.message.twitter");
                            nbt.set("color", "1DA1F2");
                        })
                        .build());

        inv.setItem(16,
                ItemBuilder.ofHead("instagram")
                        .name(ChatColor.LIGHT_PURPLE + "Instagram")
                        .id("about_link")
                        .nbt(nbt -> {
                            nbt.set("link", "https://www.instagram.com/teaminceptus");
                            nbt.set("message_id", "menu.about.message.instagram");
                            nbt.set("color", "FF55FF");
                        })
                        .build());

        p.openInventory(inv);
    }

    default void cosmetics(Player p) {
        StarInventory inv = Generator.genGUI("cosmetics_parent_menu", 54, get("menu.cosmetics"));
        inv.setCancelled();

        for (CosmeticParent parent : CosmeticParent.values()) {
            ItemStack item = new ItemStack(parent.getIcon());
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.YELLOW + get(parent.getDisplayKey()));
            item.setItemMeta(meta);

            NBTWrapper nbt = of(item);
            nbt.setID("cosmetic:selection:parent");
            nbt.set("parent", parent.name());
            item = nbt.getItem();

            inv.setItem(parent.getPlace(), item);
        }

        ItemStack hats = StarInventoryUtil.toItemStack(BaseHat.NORMAL);
        inv.setItem(22, hats);

        List<CosmeticLocation<?>> sel = Wrapper.allFor(BaseShape.class);
        inv.setAttribute("collections:custom:particle", sel);
        inv.setAttribute("items_display:particle", "menu.cosmetics.shape");

        ItemStack particles = StarMaterial.FIREWORK_STAR.findStack();
        FireworkEffectMeta pMeta = (FireworkEffectMeta) particles.getItemMeta();
        pMeta.setDisplayName(ChatColor.YELLOW + get("menu.cosmetics.shape"));
        pMeta.setEffect(FireworkEffect.builder()
                .withColor(Color.fromRGB(r.nextInt(16777216)))
                .build());

        pMeta.addItemFlags(ItemFlag.values());

        particles.setItemMeta(pMeta);
        NBTWrapper pnbt = of(particles);
        pnbt.setID("cosmetic:selection:custom");
        pnbt.set("type", "particle");
        pnbt.set("custom_id", "particle");
        particles = pnbt.getItem();
        inv.setItem(24, particles);
        
        ItemStack soundEvents = new ItemStack(Material.NOTE_BLOCK);
        ItemMeta sMeta = soundEvents.getItemMeta();
        sMeta.setDisplayName(ChatColor.YELLOW + get("menu.cosmetics.choose.sound"));

        soundEvents.setItemMeta(sMeta);
        NBTWrapper snbt = of(soundEvents);
        snbt.setID("cosmetic:selection:custom_inventory");
        snbt.set("inventory_key", "sound_events");
        soundEvents = snbt.getItem();
        inv.setItem(31, soundEvents);
        inv.setAttribute("sound_events", Generator.createSelectionInventory(p));

        try {
            StarInventory structureInv = Generator.createStructureInventory(p);

            ItemStack structures = new ItemStack(Material.STRUCTURE_BLOCK);
            ItemMeta stMeta = structures.getItemMeta();
            stMeta.setDisplayName(ChatColor.YELLOW + get("menu.cosmetics.choose.structure"));

            structures.setItemMeta(stMeta);
            NBTWrapper stnbt = of(structures);
            stnbt.setID("cosmetic:selection:custom_inventory");
            stnbt.set("inventory_key", "structures");
            stnbt.set("cooldown", "structures");
            structures = stnbt.getItem();
            inv.setItem(30, structures);
            inv.setAttribute("structures", structureInv);
        } catch (IllegalArgumentException ignored) {
            inv.setItem(30, ItemBuilder.LOCKED);
        }

        ItemStack pets = StarInventoryUtil.getHead("rabbit_pet");
        ItemMeta petMeta = pets.getItemMeta();
        petMeta.setDisplayName(ChatColor.YELLOW + get("menu.cosmetics.choose.pet"));
        pets.setItemMeta(petMeta);

        NBTWrapper petNBT = of(pets);
        petNBT.setID("cosmetic:selection:custom_inventory");
        petNBT.set("inventory_key", "pets");
        pets = petNBT.getItem();
        inv.setItem(32, pets);
        inv.setAttribute("pets", Generator.createPetInventory(p));

        ItemStack settings = ItemBuilder.of(Material.NETHER_STAR)
                .id("player_settings")
                .name(ChatColor.YELLOW + get("menu.cosmetics.settings"))
                .nbt(nbt -> nbt.set("back", "cosmetics"))
                .build();

        inv.setItem(52, settings);

        p.openInventory(inv);
    }

    default void help(CommandSender sender) {
        List<String> info = new ArrayList<>();
        for (String name : COMMANDS.keySet()) {
            PluginCommand cmd = Bukkit.getPluginCommand(name);

            if (!sender.isOp() && COMMAND_PERMISSION.get(name) != null && !(sender.hasPermission(COMMAND_PERMISSION.get(name)))) continue;

            if (sender.isOp())
                info.add(ChatColor.AQUA + "/" + cmd.getName() + ChatColor.WHITE + " - " + ChatColor.BLUE + COMMAND_DESCRIPTION.get(name) + ChatColor.WHITE + " | " + ChatColor.GOLD + (COMMAND_PERMISSION.get(name) == null ? "No Permissions" : COMMAND_PERMISSION.get(name)));
            else
                info.add(ChatColor.AQUA + "/" + cmd.getName() + ChatColor.WHITE + " - " + ChatColor.BLUE + COMMAND_DESCRIPTION.get(name));
        }

        String msg = ChatColor.DARK_AQUA + "" + ChatColor.UNDERLINE + get("constants.commands") + "\n\n" + String.join("\n", info.toArray(new String[]{}));
        sender.sendMessage(msg);
    }

    default void trails(Player p) {
        StarInventory parentInv = Generator.createParentInventory(CosmeticParent.TRAILS);
        p.openInventory(parentInv);
        StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
    }

    default void structures(Player p, @Nullable String structure) {
        if (structure == null) {
            try {
                p.openInventory(Generator.createStructureInventory(p));
                StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
            } catch (IllegalArgumentException e) {
                sendError(p, "error.cosmetics.no_structures");
                StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
            }
            return;
        }

        if (StarCooldowns.checkCooldown("structure", p)) return;

        StarPlayer sp = new StarPlayer(p);

        StructureInfo info = StarConfig.getRegistry().getAvailableStructures()
                .stream()
                .filter(inf -> inf.getLocalizedName().equalsIgnoreCase(structure))
                .findFirst().orElse(null);

        if (info == null) {
            sendError(p, "error.cosmetics.structure_not_found");
            return;
        }

        if (sp.getSetting(PlayerSetting.STRUCTURE_VELOCITY) != PlayerSetting.VelocityPower.NONE) {
            PlayerSetting.VelocityPower power = sp.getSetting(PlayerSetting.STRUCTURE_VELOCITY);

            p.setMetadata("immune_fall", new FixedMetadataValue(StarConfig.getPlugin(), true));
            p.setVelocity(p.getLocation().getDirection().multiply(-power.getLaunchPower()));
        }

        info.getStructure().placeAndRemove(p.getLocation().add(p.getLocation().getDirection()), 200);
        StarCooldowns.set("structure", p, 100);

        StarRunnable.syncLater(() -> {
            if (p.getVelocity().getY() < 0.1) p.removeMetadata("immune_fall", StarConfig.getPlugin());
        }, 5);
        StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
    }

    default void pets(Player p, String[] args) {
        if (args == null || args.length < 1) {
            p.openInventory(Generator.createPetInventory(p));
            StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
            return;
        }

        StarPlayer sp = new StarPlayer(p);
        switch (args[0].toLowerCase()) {
            case "remove": {
                if (sp.getSpawnedPet() == null) {
                    sendError(p, "error.cosmetics.no_pet");
                    return;
                }

                StarPlayerUtil.removePet(p);
                sp.sendNotification(ChatColor.GREEN + get("success.cosmetics.pet_removed"));
                break;
            }
            default: {
                sendError(p, "error.argument");
                break;
            }
        }
    }

    default void shapes(Player p) {
        List<CosmeticLocation<?>> sel = Wrapper.allFor(BaseShape.class);
        List<StarInventory> invs = Generator.createSelectionInventory(p, sel, get("menu.cosmetics.shape"));
        invs.forEach(inv -> StarInventoryUtil.setBack(inv, cw::cosmetics));

        ItemStack cancel = new ItemStack(Material.BARRIER);
        ItemMeta meta = cancel.getItemMeta();
        meta.setDisplayName(ChatColor.RED + get("menu.cosmetics.particle.reset"));
        cancel.setItemMeta(meta);

        NBTWrapper n = of(cancel);
        n.setID("cancel:particle");
        cancel = n.getItem();

        ItemStack cancelF = cancel;
        invs.forEach(i -> i.setItem(18, cancelF));

        p.openInventory(invs.get(0));
        StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
    }

    default void soundSelection(Player p, String... args) {
        if (args == null || args.length < 1) {
            p.openInventory(Generator.createSelectionInventory(p));
            StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
            return;
        }

        StarPlayer sp = new StarPlayer(p);

        switch (args[0].toLowerCase()) {
            case "add": {
                if (sp.getSelectionLimit() <= sp.getSoundSelections().size()) {
                    sendError(p, "error.cosmetics.selection_limit");
                    StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                    return;
                }

                InventorySelector.createSelection(p);
                break;
            }
            default: {
                sendError(p, "error.argument");
                break;
            }
        }

        StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
    }

    default void hats(Player p) {
        List<CosmeticLocation<?>> sel = Wrapper.allFor(BaseHat.class);
        List<StarInventory> invs = Generator.createSelectionInventory(p, sel, get("menu.cosmetics.hat"));
        invs.forEach(inv -> StarInventoryUtil.setBack(inv, cw::cosmetics));

        p.openInventory(invs.get(0));
        StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
    }

    // Utilities

    static long getCosmeticCount(TrailType t) {
        return Wrapper.getCosmeticSelections().getAllSelections()
                .entrySet()
                .stream()
                .filter(e -> e.getKey() instanceof Trail)
                .filter(e -> ((Trail<?>) e.getKey()).getType() == t)
                .mapToLong(e -> e.getValue().size())
                .sum();
    }

    static long getCosmeticCount(Class<? extends Cosmetic> c) {
        return Wrapper.getCosmeticSelections().getAllSelections()
                .entrySet()
                .stream()
                .filter(e -> c.isInstance(e.getKey()))
                .mapToLong(e -> e.getValue().size())
                .sum();
    }

    static long getCosmeticCount() {
        long count = 0;
        count += Wrapper.getCosmeticSelections().getAllSelections()
                .values()
                .stream()
                .mapToLong(List::size)
                .sum();

        count += SoundEventSelection.AVAILABLE_EVENTS.size();
        count += PetType.values().length;
        count += StarConfig.getRegistry().getAvailableStructures().size();

        return count;
    }

    static String comma(long l) {
        return String.format("%,.0f", (double) l);
    }

}
