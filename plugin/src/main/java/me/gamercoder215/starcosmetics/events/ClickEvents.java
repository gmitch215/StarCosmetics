package me.gamercoder215.starcosmetics.events;

import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.StarCosmetics;
import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticLocation;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticParent;
import me.gamercoder215.starcosmetics.api.cosmetics.capes.Cape;
import me.gamercoder215.starcosmetics.api.cosmetics.gadget.Gadget;
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
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.GadgetSelection;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static me.gamercoder215.starcosmetics.util.Constants.w;
import static me.gamercoder215.starcosmetics.util.Generator.cw;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.*;
import static me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper.builder;
import static me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper.of;

@SuppressWarnings("unchecked")
public final class ClickEvents implements Listener {

    private static StarCosmetics plugin;


    public ClickEvents(StarCosmetics plugin) {
        ClickEvents.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private static final Map<String, BiConsumer<StarInventory, InventoryClickEvent>> CLICK_ITEMS = ImmutableMap.<String, BiConsumer<StarInventory, InventoryClickEvent>>builder()
            .put("scroll_up", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();

                int row = inv.getAttribute("row_count", Integer.class);
                Map<Integer, List<ItemStack>> rows = inv.getAttribute("rows", Map.class);

                if (row == 0) {
                   StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                   return;
                }

                int newRow = row - 1;
                inv.setAttribute("row_count", newRow);

                StarInventoryUtil.setRows(inv, rows, newRow);
                p.updateInventory();
                StarSound.ENTITY_ARROW_HIT_PLAYER.playFailure(p);
            })
            .put("scroll_down", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();

                int row = inv.getAttribute("row_count", Integer.class);
                Map<Integer, List<ItemStack>> rows = inv.getAttribute("rows", Map.class);

                if (row >= rows.size() - 4) {
                    StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                    return;
                }

                int newRow = row + 1;
                inv.setAttribute("row_count", newRow);

                StarInventoryUtil.setRows(inv, rows, newRow);
                p.updateInventory();
                StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
            })
            .put("next_page", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
            
                List<StarInventory> pages = inv.getAttribute("pages", List.class);
                int page = inv.getAttribute("page", Integer.class);

                if (page >= pages.size() - 1) {
                    StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                    return;
                }

                p.openInventory(pages.get(page + 1));
                StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
            })
            .put("previous_page", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();

                List<StarInventory> pages = inv.getAttribute("pages", List.class);
                int page = inv.getAttribute("current_page", Integer.class);

                if (page == 0) {
                    StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                    return;
                }

                p.openInventory(pages.get(page - 1));
                StarSound.ENTITY_ARROW_HIT_PLAYER.playFailure(p);
            })
            .put("back", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                Consumer<Player> backAction = inv.getAttribute("back_inventory_action", Consumer.class);
                boolean sound = inv.getAttribute("back_inventory_sound", true, Boolean.class);
                if (backAction == null) return;

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        backAction.accept(p);
                        if (sound) StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                    }
                }.runTask(plugin);
            })
            .put("cosmetic:selection:parent", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                ItemStack item = e.getCurrentItem();
                NBTWrapper iNBT = of(item);
                CosmeticParent parent = CosmeticParent.valueOf(iNBT.getString("parent"));

                String cooldown = iNBT.getString("cooldown");
                if (StarCooldowns.checkCooldown(cooldown, p)) return;

                StarInventory parentInv = Generator.createParentInventory(parent);
                p.openInventory(parentInv);
                StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
            })
            .put("cancel:cosmetic:all", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                ItemStack item = e.getCurrentItem();

                NBTWrapper nbt = of(item);
                CosmeticParent parent = CosmeticParent.valueOf(nbt.getString("parent"));

                InventorySelector.confirm(p, () -> {
                    StarPlayer sp = new StarPlayer(p);
                    switch (parent) {
                        case TRAILS: {
                            for (TrailType t : TrailType.values()) sp.setSelectedTrail(t, null);
                            break;
                        }
                    }

                    StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
                    cw.cosmetics(p);
                }, () -> {
                    StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                    inv.getAttribute("selection_back", Consumer.class).accept(p);
                });
            })
            .put("choose:cosmetic", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                ItemStack item = e.getCurrentItem();
                NBTWrapper nbt = of(item);
                CosmeticLocation<?> loc = nbt.getCosmeticLocation("location");

                CompletionCriteria c = loc.getCompletionCriteria();

                if (!c.isUnlocked(p)) {
                    StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                    p.sendMessage(get("plugin.prefix") + ChatColor.RED + c.getDisplayMessage());
                    return;
                }

                StarPlayer sp = new StarPlayer(p);

                if (loc instanceof GadgetSelection) {
                    GadgetSelection sel = (GadgetSelection) loc;

                    p.getInventory().removeItem(Arrays.stream(p.getInventory().getContents())
                            .filter(Objects::nonNull)
                            .filter(i -> {
                                NBTWrapper n = of(i);
                                return n.hasString("gadget");
                            }).toArray(ItemStack[]::new));

                    if (p.getInventory().firstEmpty() != -1)
                        p.getInventory().addItem(sel.createGadgetItem());
                    else {
                        StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                        return;
                    }
                }

                if (Trail.class.isAssignableFrom(loc.getParent().getClass()))
                    sp.setSelectedTrail(TrailType.valueOf(inv.getAttribute("trail_type", String.class)), loc);
                else sp.setSelectedCosmetic(loc.getParent().getClass(), loc);

                inv.setItem(e.getSlot(), StarInventoryUtil.toItemStack(p, loc));

                StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
                updateCache(p);
            })
            .put("cosmetic:selection", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                ItemStack item = e.getCurrentItem();
                NBTWrapper nbt = of(item);

                String cooldown = nbt.getString("cooldown");
                if (StarCooldowns.checkCooldown(cooldown, p)) return;

                Cosmetic c = plugin.getByNamespace(nbt.getString("cosmetic"));
                String display = nbt.getString("display");

                List<CosmeticLocation<?>> selections = plugin.getAllFor(c);
                List<StarInventory> invs = Generator.createSelectionInventory(p, selections, display);

                for (StarInventory sel : invs) {
                    if (inv.hasAttribute("selection_back"))
                        StarInventoryUtil.setBack(sel, inv.getAttribute("selection_back", Consumer.class));
                    else StarInventoryUtil.setBack(sel, cw::cosmetics);

                    if (nbt.hasString("trail_type")) sel.setAttribute("trail_type", nbt.getString("trail_type"));
                }

                p.openInventory(invs.get(0));
                StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
            })
            .put("cosmetic:selection:custom", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                ItemStack item = e.getCurrentItem();
                NBTWrapper nbt = of(item);

                String cooldown = nbt.getString("cooldown");
                if (StarCooldowns.checkCooldown(cooldown, p)) return;

                String customId = nbt.getString("custom_id");

                List<CosmeticSelection<?>> selections = inv.getAttribute("collections:custom:" + customId, List.class);
                List<StarInventory> invs = Generator.createSelectionInventory(p, selections, get(inv.getAttribute("items_display:" + customId, String.class)));

                for (StarInventory sel : invs)
                    if (inv.hasAttribute("selection_back"))
                        StarInventoryUtil.setBack(sel, inv.getAttribute("selection_back", Consumer.class));
                    else StarInventoryUtil.setBack(sel, cw::cosmetics);

                switch (nbt.getString("type")) {
                    case "particle": {
                        ItemStack cancel = new ItemStack(Material.BARRIER);
                        ItemMeta meta = cancel.getItemMeta();
                        meta.setDisplayName(ChatColor.RED + get("menu.cosmetics.particle.reset"));
                        cancel.setItemMeta(meta);

                        NBTWrapper n = of(cancel);
                        n.setID("cancel:particle");
                        cancel = n.getItem();

                        ItemStack cancelF = cancel;
                        invs.forEach(i -> i.setItem(18, cancelF));
                        break;
                    }
                }

                p.openInventory(invs.get(0));
                StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
            })
            .put("cancel:particle", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                StarPlayer sp = new StarPlayer(p);

                sp.setSelectedCosmetic(ParticleShape.class, null);
                StarSound.ENTITY_ARROW_HIT_PLAYER.playFailure(p);
                updateCache(p);
            })
            .put("cancel:cosmetic", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                StarPlayer sp = new StarPlayer(p);

                ItemStack item = e.getCurrentItem();
                NBTWrapper nbt = of(item);
                Class<? extends Cosmetic> clazz = nbt.getClass("cosmetic", Cosmetic.class);

                if (Gadget.class.isAssignableFrom(clazz)) {
                    p.getInventory().removeItem(Arrays.stream(p.getInventory().getContents())
                            .filter(Objects::nonNull)
                            .filter(i -> {
                                NBTWrapper n = of(i);
                                return n.hasString("gadget");
                            }).toArray(ItemStack[]::new));
                }

                sp.setSelectedCosmetic(clazz, null);
                StarSound.ENTITY_ARROW_HIT_PLAYER.playFailure(p);
                updateCache(p);
            })
            .put("cancel:cosmetic:trail", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                StarPlayer sp = new StarPlayer(p);

                ItemStack item = e.getCurrentItem();
                NBTWrapper nbt = of(item);
                TrailType type = TrailType.valueOf(nbt.getString("trail"));

                sp.setSelectedTrail(type, null);
                StarSound.ENTITY_ARROW_HIT_PLAYER.playFailure(p);
                updateCache(p);
            })
            .put("cosmetic:selection:custom_inventory", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                ItemStack item = e.getCurrentItem();
                NBTWrapper nbt = of(item);
                String key = nbt.getString("inventory_key");

                StarInventory toOpen = inv.getAttribute(key, StarInventory.class);
                p.openInventory(toOpen);
                StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
            })
            .put("manage:soundevent", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                ItemStack item = e.getCurrentItem();

                NBTWrapper nbt = of(item);
                final SoundEventSelection selection = nbt.getSoundEventSelection("selection");

                switch (e.getClick()) {
                    case LEFT:
                    case SHIFT_LEFT: {
                        InventorySelector.editSelection(p, selection, sel -> {
                            StarPlayer sp = new StarPlayer(p);
                            sp.removeSelection(selection);
                            sp.addSelection(sel);

                            StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
                            p.openInventory(Generator.createSelectionInventory(p));

                            updateCache(p);
                        });
                        break;
                    }
                    case RIGHT:
                    case SHIFT_RIGHT: {
                        InventorySelector.confirm(p, () -> {
                            StarPlayer sp = new StarPlayer(p);
                            sp.removeSelection(selection);

                            sp.sendNotification(prefix() + ChatColor.GREEN + getWithArgs("success.cosmetics.remove_selection", selection.getEvent().getSimpleName()));
                            StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);

                            p.openInventory(Generator.createSelectionInventory(p));

                            updateCache(p);
                        });
                        break;
                    }
                    default: {
                        StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                        break;
                    }
                }
            })
            .put("add:soundevent", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                InventorySelector.createSelection(p);
            })
            .put("stop_sound", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                w.stopSound(p);
            })
            .put("spawn:structure", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                StarPlayer sp = new StarPlayer(p);

                ItemStack item = e.getCurrentItem();
                NBTWrapper nbt = of(item);

                StructureInfo info = nbt.getStructureInfo("info");
                if (info == null) {
                    StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                    return;
                }

                StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
                p.closeInventory();

                if (sp.getSetting(PlayerSetting.STRUCTURE_VELOCITY) != PlayerSetting.VelocityPower.NONE) {
                    PlayerSetting.VelocityPower power = sp.getSetting(PlayerSetting.STRUCTURE_VELOCITY);

                    p.setMetadata("immune_fall", new FixedMetadataValue(plugin, true));
                    p.setVelocity(p.getLocation().getDirection().multiply(-power.getLaunchPower()));
                }

                info.getStructure().placeAndRemove(p.getLocation().add(p.getLocation().getDirection()), 200);

                StarRunnable.syncLater(() -> {
                    if (p.getVelocity().getY() < 0.1) p.removeMetadata("immune_fall", plugin);
                }, 5);
                StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
            })
            .put("toggle:setting:boolean", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                StarPlayer sp = new StarPlayer(p);

                ItemStack item = e.getCurrentItem();
                NBTWrapper nbt = of(item);

                PlayerSetting<Boolean> setting = (PlayerSetting<Boolean>) PlayerSetting.byId(nbt.getString("setting"));
                if (setting == null) {
                    StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                    return;
                }

                boolean value = !sp.getSetting(setting);
                sp.setSetting(setting, value);

                ItemStack newItem = Generator.generateSetting(p, setting);
                inv.setItem(e.getSlot(), newItem);
                StarSound.ENTITY_ARROW_HIT_PLAYER.play(p, 1F, value ? 2F : 0F);
                STAR_PLAYER_CACHE.remove(p.getUniqueId());
            })
            .put("choose:pet", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                StarPlayer sp = new StarPlayer(p);

                if (sp.getSpawnedPet() != null) {
                    p.sendMessage(ChatColor.RED + get("error.cosmetics.pet_spawned"));
                    StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                    return;
                }

                ItemStack item = e.getCurrentItem();
                NBTWrapper nbt = of(item);

                PetType type = PetType.valueOf(nbt.getString("pet").toUpperCase());
                if (type == null) {
                    StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                    return;
                }

                CompletionCriteria c = type.getInfo().getCriteria();
                if (!c.isUnlocked(p)) {
                    StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                    p.sendMessage(get("plugin.prefix") + ChatColor.RED + c.getDisplayMessage());
                    return;
                }

                StarPlayerUtil.spawnPet(p, type);
                p.sendMessage(ChatColor.GREEN + getWithArgs("success.cosmetics.pet_spawned", type.getInfo().getName()));
                StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
            })
            .put("toggle:setting:enum", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                StarPlayer sp = new StarPlayer(p);

                ItemStack item = e.getCurrentItem();
                NBTWrapper nbt = of(item);

                PlayerSetting<Enum<?>> setting = (PlayerSetting<Enum<?>>) PlayerSetting.byId(nbt.getString("setting"));
                if (setting == null) {
                    StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                    return;
                }
                
                Class<Enum<?>> clazz = setting.getType();
                Enum<?> value = sp.getSetting(setting);

                Enum<?>[] values = clazz.getEnumConstants();
                int index = Arrays.asList(values).indexOf(value);

                int newIndex = index + (e.getClick().isRightClick() ? -1 : 1);
                if (newIndex < 0) newIndex = values.length - 1;

                Enum<?> next = values[newIndex % values.length];
                sp.setSetting(setting, next);

                ItemStack newItem = Generator.generateSetting(p, setting);
                inv.setItem(e.getSlot(), newItem);

                float pitch = 2F * (next.ordinal() / (values.length - 1F));
                StarSound.ENTITY_ARROW_HIT_PLAYER.play(p, 1F, pitch);
                STAR_PLAYER_CACHE.remove(p.getUniqueId());

                if (setting.equals(PlayerSetting.HOLOGRAM_FORMAT))
                    StarPlayerUtil.getHologram(p);
            })
            .put("toggle:setting", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                StarPlayer sp = new StarPlayer(p);

                ItemStack item = e.getCurrentItem();
                NBTWrapper nbt = of(item);

                @SuppressWarnings({"rawtypes"})
                PlayerSetting setting = PlayerSetting.byId(nbt.getString("setting"));
                if (setting == null) {
                    StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                    return;
                }

                Object value = sp.getSetting(setting);
                int index = setting.getPossibleValues().indexOf(value);
                Object next = setting.getPossibleValues().get((index + 1) % setting.getPossibleValues().size());

                sp.setSetting(setting, next);

                ItemStack newItem = Generator.generateSetting(p, setting);
                inv.setItem(e.getSlot(), newItem);
                StarSound.ENTITY_ARROW_HIT_PLAYER.playFailure(p);
            })
            .put("about_link", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                p.closeInventory();

                ItemStack item = e.getCurrentItem();
                NBTWrapper nbt = of(item);

                String color = nbt.getString("color");
                String messageS = StarConfig.getConfig().get(nbt.getString("message_id"));
                TextComponent message = new TextComponent(prefix() + StarChat.hexMessage(color, messageS) + "\n");

                String linkS = nbt.getString("link");
                TextComponent link = new TextComponent(prefix() + StarChat.hexMessage(color, linkS.substring(8)));
                link.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[] {new TextComponent(ChatColor.AQUA + linkS)}));
                link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, linkS));

                try {
                    p.spigot().sendMessage(message, link);
                } catch (UnsupportedOperationException ignored) {
                    p.sendMessage(new String[] {message.getText(), link.getText()});
                }
            })
            .put("cancel:pet", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                StarPlayer sp = new StarPlayer(p);

                if (sp.getSpawnedPet() == null) {
                    StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                    return;
                }

                StarPlayerUtil.removePet(p);
                StarSound.ENTITY_ARROW_HIT_PLAYER.playFailure(p);
                sp.sendNotification(ChatColor.GREEN + get("success.cosmetics.pet_removed"));
            })
            .put("player_settings", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                ItemStack item = e.getCurrentItem();
                NBTWrapper nbt = of(item);

                StarInventory settings = Generator.createSettingsInventory(p);
                switch (nbt.getString("back")) {
                    case "cosmetics": {
                        StarInventoryUtil.setBack(settings, cw::cosmetics);
                        break;
                    }
                }

                p.openInventory(settings);
                StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
            })
            .put("cancel:cosmetic:hat", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                StarPlayer sp = new StarPlayer(p);

                sp.setSelectedCosmetic(Hat.class, null);
                StarSound.ENTITY_ARROW_HIT_PLAYER.playFailure(p);
                updateCache(p);

                p.getEquipment().setHelmet(null);
            })
            .put("cosmetic:hologram:set", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                StarPlayer sp = new StarPlayer(p);

                w.sendSign(p, lines -> {
                    String messageL = String.join(" ", lines).trim();
                    final String message = messageL.substring(0, Math.min(messageL.length(), StarConfig.getConfig().getMaxHologramLimit()));

                    if (message.isEmpty()) {
                        StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                        return;
                    }

                    StarInventory confirm = InventorySelector.confirm(p, () -> {
                        sp.setHologramMessage(message);
                        StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);

                        STAR_PLAYER_CACHE.remove(p.getUniqueId());
                        StarPlayerUtil.getHologram(p);
                        cw.hologramInfo(p);
                    });
                    confirm.setItem(13, ItemBuilder.of(StarMaterial.OAK_SIGN)
                            .name(ChatColor.YELLOW + "\"" + message + "\"")
                            .build()
                    );
                });
            })
            .put("cosmetic:hologram:reset", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                StarPlayer sp = new StarPlayer(p);

                InventorySelector.confirm(p, () -> {
                    sp.setHologramMessage(null);
                    StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);

                    STAR_PLAYER_CACHE.remove(p.getUniqueId());
                    StarPlayerUtil.getHologram(p);
                    cw.hologramInfo(p);
                });
            })
            .put("cancel:cosmetic:cape", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                StarPlayer sp = new StarPlayer(p);

                sp.setSelectedCosmetic(Cape.class, null);
                StarSound.ENTITY_ARROW_HIT_PLAYER.playFailure(p);
                updateCache(p);

                StarPlayerUtil.removeCape(p);
            })

            .build();

    private static void updateCache(Player p) {
        STAR_PLAYER_CACHE.remove(p.getUniqueId());
    }

    private static final Map<String, BiConsumer<StarInventory, InventoryClickEvent>> CLICK_INVENTORY = ImmutableMap.<String, BiConsumer<StarInventory, InventoryClickEvent>>builder()
            .put("choose:event_inv", (inv, e) -> {
                ItemStack item = e.getCurrentItem();
                if (checkMenuItem(item)) return;
                NBTWrapper nbt = of(item);

                Class<? extends Event> clazz = nbt.getClass("event", Event.class);

                Consumer<Class<? extends Event>> action = inv.getAttribute("chosen_action", Consumer.class);
                action.accept(clazz);
            })
            .put("choose:sound_inv", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                ItemStack item = e.getCurrentItem();
                if (checkMenuItem(item)) return;
                NBTWrapper nbt = of(item);

                Sound s = Sound.valueOf(nbt.getString("sound"));

                switch (e.getClick()) {
                    case LEFT:
                    case SHIFT_LEFT: {
                        Consumer<Sound> action = inv.getAttribute("chosen_action", Consumer.class);
                        action.accept(s);
                        break;
                    }
                    case RIGHT:
                    case SHIFT_RIGHT: {
                        p.getWorld().playSound(p.getLocation(), s, 2F, 1F);
                        break;
                    }
                    default: {
                        StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                        break;
                    }
                }

            })
            .put("confirm_inv", (inv, e) -> {
                ItemStack item = e.getCurrentItem();
                NBTWrapper nbt = of(item);

                Runnable confirmR = inv.getAttribute("confirm_action", Runnable.class);
                Runnable cancelR = inv.getAttribute("cancel_action", Runnable.class);

                switch (nbt.getString("item")) {
                    case "confirm": {
                        confirmR.run();
                        return;
                    }
                    case "cancel": {
                        cancelR.run();
                        return;
                    }
                    default: {
                        throw new AssertionError("Unknown Confirm Inventory Item: " + nbt.getString("item"));
                    }
                }
            })
            .put("edit:soundevent", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                ItemStack item = e.getCurrentItem();

                SoundEventSelection initial = inv.getAttribute("current_event", SoundEventSelection.class);
                Consumer<SoundEventSelection> action = inv.getAttribute("chosen_action", Consumer.class);

                NBTWrapper nbt = of(item);
                switch (nbt.getString("item")) {
                    case "sound": {
                        InventorySelector.chooseSound(p, sound -> {
                            SoundEventSelection sel = initial.cloneTo(sound);
                            inv.setAttribute("current_event", sel);
                            
                            InventorySelector.editSelection(p, sel, action);
                            StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
                        }, pl -> pl.openInventory(inv));
                        break;
                    }
                    case "event": {
                        InventorySelector.chooseEvent(p, event -> {
                            SoundEventSelection sel = initial.cloneTo(event);
                            inv.setAttribute("current_event", sel);
                            
                            InventorySelector.editSelection(p, sel, action);
                            StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
                        }, pl -> pl.openInventory(inv));
                        break;
                    }
                    case "pitch_volume": {
                        InventorySelector.choosePitchVolume(p, initial.getSound(), (pitch, volume) -> {
                            SoundEventSelection sel = initial.cloneTo(initial.getSound(), volume, pitch);
                            inv.setAttribute("current_event", sel);

                            InventorySelector.editSelection(p, sel, action);
                            StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
                        }, pl -> pl.openInventory(inv));
                        break;
                    }
                    case "save": {
                        action.accept(initial);
                        break;
                    }
                }
            })
            .put("choose:pitch_volume_inv", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                ItemStack item = e.getCurrentItem();

                NBTWrapper nbt = of(item);
                if (nbt.getID().startsWith("scroll")) return;
                if (nbt.getID().startsWith("back")) return;

                String type = nbt.getString("item");

                switch (type) {
                    case "save": {
                        float pitch = NBTWrapper.getFloat(inv.getItem(11), "value");
                        float volume = NBTWrapper.getFloat(inv.getItem(15), "value");

                        BiConsumer<Float, Float> action = inv.getAttribute("chosen_action", BiConsumer.class);
                        action.accept(pitch, volume);
                        break;
                    }
                    case "test": {
                        Sound sound = inv.getAttribute("sound", Sound.class);

                        float pitch = NBTWrapper.getFloat(inv.getItem(11), "value");
                        float volume = NBTWrapper.getFloat(inv.getItem(15), "value");

                        p.getWorld().playSound(p.getLocation(), sound, volume, pitch);
                        break;
                    }
                    case "volume":
                    case "pitch": {
                        ClickType click = e.getClick();

                        if (!click.isLeftClick() && !click.isRightClick()) {
                            StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                            return;
                        }

                        float value = nbt.getFloat("value");
                        float min = nbt.getFloat("min");
                        float max = nbt.getFloat("max");

                        float newV = click.isRightClick() ? value + 0.1f : value - 0.1f;

                        if (min > newV || max < newV) {
                            StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                            return;
                        }

                        ItemStack newItem = builder(item.clone(),
                                meta -> meta.setLore(Arrays.asList(
                                        ChatColor.GREEN + String.format("%,.1f", newV),
                                        " ",
                                        ChatColor.YELLOW + get("constants.menu.right_click_up"),
                                        ChatColor.YELLOW + get("constants.menu.left_click_down")
                                )),
                                newNBT -> {
                                    newNBT.set("value", newV);
                                    newNBT.set("min", min);
                                    newNBT.set("max", max);
                                    newNBT.set("item", type);
                                }
                        );

                        inv.setItem(e.getSlot(), newItem);
                        if (click.isRightClick()) StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
                        else StarSound.ENTITY_ARROW_HIT_PLAYER.playFailure(p);
                        break;
                    }
                }
            })
            .build();

    private static boolean checkMenuItem(ItemStack item) {
        if (ItemBuilder.GUI_BACKGROUND.isSimilar(item)) return true;

        NBTWrapper nbt = of(item);
        String id = nbt.getID();

        if (id.startsWith("scroll")) return true;
        if (id.startsWith("back")) return true;
        if (id.equalsIgnoreCase("stop_sound")) return true;

        return id.contains("page");
    }

    @EventHandler
    public void click(InventoryClickEvent e) {
        if (e.isCancelled()) return;
        if (!(e.getClickedInventory() instanceof StarInventory)) return;
        if (!(e.getWhoClicked() instanceof Player)) return;
        if (e.getCurrentItem() == null) return;

        ItemStack item = e.getCurrentItem();
        if (item.isSimilar(ItemBuilder.GUI_BACKGROUND)) {
            e.setCancelled(true);
            return;
        }

        StarInventory inv = (StarInventory) e.getClickedInventory();
        if (inv.hasAttribute("cancel")) e.setCancelled(true);

        if (CLICK_INVENTORY.containsKey(inv.getKey())) CLICK_INVENTORY.get(inv.getKey()).accept(inv, e);

        NBTWrapper w = of(item);

        if (w.hasID()) {
            String id = w.getID();
            if (CLICK_ITEMS.containsKey(id)) {
                e.setCancelled(true);
                CLICK_ITEMS.get(id).accept(inv, e);
            }
        }
    }

    @EventHandler
    public void drag(InventoryDragEvent e) {
        Inventory inv = e.getView().getTopInventory();
        if (inv == null) return;
        if (inv instanceof PlayerInventory) return;

        if (inv instanceof StarInventory) {
            StarInventory sinv = (StarInventory) inv;
            if (sinv.hasAttribute("cancel")) e.setCancelled(true);
        }

        for (ItemStack item : e.getNewItems().values()) {
            if (item == null) return;
            if (item.isSimilar(ItemBuilder.GUI_BACKGROUND)) e.setCancelled(true);
            if (of(item).hasString("gadget")) e.setCancelled(true);
        }
    }

}
