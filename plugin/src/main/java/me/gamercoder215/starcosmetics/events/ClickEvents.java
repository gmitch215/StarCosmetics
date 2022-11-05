package me.gamercoder215.starcosmetics.events;

import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.StarCosmetics;
import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticParent;
import me.gamercoder215.starcosmetics.api.cosmetics.registry.CosmeticLocation;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.Trail;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.TrailType;
import me.gamercoder215.starcosmetics.api.player.StarPlayer;
import me.gamercoder215.starcosmetics.util.Generator;
import me.gamercoder215.starcosmetics.util.StarSound;
import me.gamercoder215.starcosmetics.util.inventory.ItemBuilder;
import me.gamercoder215.starcosmetics.util.inventory.StarInventory;
import me.gamercoder215.starcosmetics.util.inventory.StarInventoryUtil;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static me.gamercoder215.starcosmetics.StarCosmetics.cw;
import static me.gamercoder215.starcosmetics.util.Generator.genGUI;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.get;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.getWrapper;
import static me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper.of;

@SuppressWarnings("unchecked")
public final class ClickEvents implements Listener {

    private static StarCosmetics plugin;
    private static final Wrapper w = getWrapper();

    public ClickEvents(StarCosmetics plugin) {
        ClickEvents.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private static final Map<String, BiConsumer<StarInventory, InventoryClickEvent>> CLICK_ITEMS = ImmutableMap.<String, BiConsumer<StarInventory, InventoryClickEvent>>builder()
            .put("scroll_up", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                ItemStack item = e.getCurrentItem();

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
                ItemStack item = e.getCurrentItem();

                int row = inv.getAttribute("row_count", Integer.class);
                Map<Integer, List<ItemStack>> rows = inv.getAttribute("rows", Map.class);

                if (row >= rows.size() - 1) {
                    StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                    return;
                }

                int newRow = row + 1;
                inv.setAttribute("row_count", newRow);

                StarInventoryUtil.setRows(inv, rows, newRow);
                p.updateInventory();
                StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
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

                int size = parent.getChildren().size() > 7 ? 45 : 27;

                StarInventory parentInv = genGUI("cosmetics_menu:" + parent.name(), size, get("menu.cosmetics." + parent.name().toLowerCase()));
                List<Integer> places = StarInventoryUtil.getGUIPlacements(size, parent.getChildren().size());

                for (int i = 0; i < parent.getChildren().size(); i++) {
                    Cosmetic c = parent.getChildren().get(i);
                    int place = places.get(i);
                    ItemStack cItem = StarInventoryUtil.toItemStack(c);
                    parentInv.setItem(place, cItem);
                }

                StarInventoryUtil.setBack(parentInv, cw::cosmetics);
                inv.setAttribute("selection_back", (Consumer<Player>) pl -> pl.openInventory(parentInv));
                inv.setAttribute("back_inventory_sound", false);

                p.openInventory(parentInv);
                StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
            })
            .put("choose:cosmetic", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                ItemStack item = e.getCurrentItem();
                NBTWrapper nbt = of(item);
                CosmeticLocation<?> loc = nbt.getCosmeticLocation("location");

                CompletionCriteria c = loc.getCompletionCriteria();

                if (!c.getCriteria().test(p)) {
                    StarSound.BLOCK_NOTE_BLOCK_PLING.playFailure(p);
                    p.sendMessage(c.getDisplayMessage());
                    return;
                }

                StarPlayer sp = new StarPlayer(p);

                if (Trail.class.isAssignableFrom(loc.getParent().getClass()))
                    sp.setSelectedTrail(TrailType.valueOf(inv.getAttribute("trail_type", String.class)), loc);
                else sp.setSelectedCosmetic(loc.getParent().getClass(), loc);
                StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
            })
            .put("cosmetic:selection", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                ItemStack item = e.getCurrentItem();
                NBTWrapper nbt = of(item);
                Cosmetic c = plugin.getByNamespace(nbt.getString("cosmetic"));
                String display = nbt.getString("display");

                List<CosmeticLocation<?>> selections = plugin.getAllFor(c);
                StarInventory sel = Generator.createSelectionInventory(selections, display);

                if (inv.hasAttribute("selection_back"))
                    StarInventoryUtil.setBack(sel, inv.getAttribute("selection_back", Consumer.class));
                else {
                    StarInventoryUtil.setBack(sel, cw::cosmetics);
                    sel.setAttribute("back_inventory_sound", false);
                }

                if (nbt.hasString("trail_type")) sel.setAttribute("trail_type", nbt.getString("trail_type"));

                p.openInventory(sel);
                StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
            })
            .put("cosmetic:selection:custom", (inv, e) -> {
                Player p = (Player) e.getWhoClicked();
                ItemStack item = e.getCurrentItem();

                List<CosmeticSelection<?>> selections = inv.getAttribute("collections:custom", List.class);
                StarInventory shapeInv = Generator.createSelectionInventory(selections, get(inv.getAttribute("items_display", String.class)));
                p.openInventory(shapeInv);
                StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p);
            })

            .build();

    private static final Map<String, BiConsumer<StarInventory, InventoryClickEvent>> CLICK_INVENTORY = ImmutableMap.<String, BiConsumer<StarInventory, InventoryClickEvent>>builder()
            .put("choose:event_inv", (inv, e) -> {
                ItemStack item = e.getCurrentItem();
                NBTWrapper nbt = of(item);
                if (nbt.getID().startsWith("scroll")) return;

                Class<? extends Event> clazz = nbt.getClass("event", Event.class);

                Consumer<Class<? extends Event>> action = inv.getAttribute("chosen_action", Consumer.class);
                action.accept(clazz);
            })
            .put("choose:sound_inv", (inv, e) -> {
                ItemStack item = e.getCurrentItem();
                NBTWrapper nbt = of(item);
                if (nbt.getID().startsWith("scroll")) return;

                Sound s = Sound.valueOf(nbt.getString("sound"));

                Consumer<Sound> action = inv.getAttribute("chosen_action", Consumer.class);
                action.accept(s);
            })
            .build();

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
        }
    }

}
