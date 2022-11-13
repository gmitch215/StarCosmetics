package me.gamercoder215.starcosmetics.wrapper.commands;

import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseShape;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticParent;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.Trail;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.TrailType;
import me.gamercoder215.starcosmetics.util.Generator;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.util.StarSound;
import me.gamercoder215.starcosmetics.util.inventory.StarInventory;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static me.gamercoder215.starcosmetics.wrapper.Wrapper.*;
import static me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper.of;

public interface CommandWrapper {

    Map<String, List<String>> COMMANDS = ImmutableMap.<String, List<String>>builder()
            .put("starsettings", Arrays.asList("ssettings", "settings", "ss"))
            .put("starreload", Arrays.asList("sreload", "sr"))
            .put("starcosmetics", Arrays.asList("scosmetics", "sc", "cosmetics", "cs"))
            .put("starabout", Arrays.asList("sabout", "sa", "stara"))
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
            .put("starabout", "Displays information about StarCosmetics.")
            .build();

    Map<String, String> COMMAND_USAGE = ImmutableMap.<String, String>builder()
            .put("starsettings", "/starsettings")
            .put("starreload", "/starreload")
            .put("starcosmetics", "/starcosmetics")
            .put("starabout", "/starabout")
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
        if (sender instanceof Player) StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess((Player) sender);
    }

    default void about(Player p) {
        StarInventory inv = Generator.genGUI("about", 27, get("menu.about"));
        inv.setAttribute("cancel", true);

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

        lore.add(" ");
        lore.add(ChatColor.RED + getWithArgs("menu.about.total_cosmetic_count", comma(getCosmeticCount()) ));
        cMeta.setLore(lore);

        cosmetics.setItemMeta(cMeta);
        inv.setItem(10, cosmetics);

        p.openInventory(inv);
    }

    default void cosmetics(Player p) {
        StarInventory inv = Generator.genGUI("cosmetics_parent_menu", 54, get("menu.cosmetics"));

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

        List<CosmeticSelection<?>> sel = Wrapper.allFor(BaseShape.class);
        inv.setAttribute("collections:custom", sel);
        inv.setAttribute("items_display", "menu.cosmetics.shape");

        ItemStack particles = StarMaterial.FIREWORK_STAR.findStack();
        FireworkEffectMeta meta = (FireworkEffectMeta) particles.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + get("menu.cosmetics.shape"));
        meta.setEffect(FireworkEffect.builder()
                .withColor(Color.fromRGB(r.nextInt(16777216)))
                .build());

        meta.addItemFlags(ItemFlag.values());

        particles.setItemMeta(meta);
        NBTWrapper pnbt = of(particles);
        pnbt.setID("cosmetic:selection:custom");
        pnbt.set("type", "particle");
        particles = pnbt.getItem();
        inv.setItem(24, particles);
        
        // TODO Create the rest of the cosmetics menus

        p.openInventory(inv);
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
        return Wrapper.getCosmeticSelections().getAllSelections()
                .values()
                .stream()
                .mapToLong(List::size)
                .sum();
    }

    static String comma(long l) {
        return String.format("%,.0f", (double) l);
    }

}
