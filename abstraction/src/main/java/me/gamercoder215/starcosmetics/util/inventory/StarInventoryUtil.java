package me.gamercoder215.starcosmetics.util.inventory;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.sun.tools.javac.jvm.Items;
import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticLocation;
import me.gamercoder215.starcosmetics.api.cosmetics.hat.Hat;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.Pet;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetInfo;
import me.gamercoder215.starcosmetics.api.cosmetics.structure.StructureInfo;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.Trail;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.TrailType;
import me.gamercoder215.starcosmetics.api.player.SoundEventSelection;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.util.StarSound;
import me.gamercoder215.starcosmetics.util.StarUtil;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.ChatPaginator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static me.gamercoder215.starcosmetics.util.Constants.w;
import static me.gamercoder215.starcosmetics.util.StarMaterial.*;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.get;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.getWithArgs;
import static me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper.of;
import static org.bukkit.Material.*;

public final class StarInventoryUtil {

    public static ItemStack itemBuilder(ItemStack original, Consumer<ItemMeta> metaConsumer) {
        ItemStack item = original.clone();
        ItemMeta meta = item.getItemMeta();
        metaConsumer.accept(meta);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack itemBuilder(Material material, Consumer<ItemMeta> metaConsumer) {
        return itemBuilder(new ItemStack(material), metaConsumer);
    }

    @NotNull
    public static Material toMaterial(@NotNull Class<? extends Event> eventClass) {
        if (Modifier.isAbstract(eventClass.getModifiers())) throw new IllegalArgumentException("Using Abstract Class");

        String n = eventClass.getSimpleName().toLowerCase();
        Material chosen = null;

        if (n.contains("player")) chosen = IRON_SWORD;
        if (chosen == null && n.contains("block")) chosen = DIRT;
        if (chosen == null && n.contains("server")) chosen = OBSERVER.find();
        if (chosen == null && n.contains("inventory")) chosen = CRAFTING_TABLE.find();
        if (chosen == null && n.contains("weather")) chosen = BUCKET;
        if (chosen == null && n.contains("vehicle")) chosen = MINECART;

        switch (n) {
            case "playerjoinevent": 
            case "playergamemodechangeevent": return GRASS_BLOCK.find();
            
            case "playerfishevent": return FISHING_ROD;
            case "playerrespawnevent": return BEACON;
            case "signchangeevent": return OAK_SIGN.find();
            case "playerriptideevent": return matchMaterial("TRIDENT");
            case "playerdeathevent": return WITHER_SKELETON_SKULL.find();
            case "furnaceextractevent": return FURNACE;
            case "playerbedenterevent": return RED_BED.find();
            case "playerexpchangeevent": return EXPERIENCE_BOTTLE.find();
            case "playereggthrowevent": return EGG;
            case "blockbreakevent": return IRON_PICKAXE;
            case "sheepdyewoolevent": return WHITE_WOOL.find();
            case "playeritembreakevent": return DIAMOND_PICKAXE;
            case "playeritemconsumeevent": return BREAD;    
        }

        if (chosen == null) chosen = REDSTONE;
        return chosen;
    }

    @NotNull
    public static ItemStack toItemStack(@NotNull SoundEventSelection s) {
        ItemStack item = new ItemStack(toMaterial(s.getEvent()));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + s.getEvent().getSimpleName());

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + get("constants.menu.sound") + " " + ChatColor.GOLD + getFriendlyName(s.getSound()));
        lore.add(ChatColor.AQUA + getWithArgs("constants.menu.pitch", ChatColor.GOLD + String.format("%,.1f", s.getPitch())));
        lore.add(ChatColor.AQUA + getWithArgs("constants.menu.volume", ChatColor.GOLD + String.format("%,.1f", s.getVolume())));
        lore.add(" ");

        DateTimeFormatter f = DateTimeFormatter.ofPattern("EEEE, LLL d, yyyy 'at' h:mm:ss a")
                .withLocale(StarConfig.getConfig().getLocale())
                .withZone(ZoneId.systemDefault());

        lore.add(ChatColor.GREEN + getWithArgs("constants.menu.created_at", ChatColor.AQUA + f.format(s.getTimestamp().toInstant())));

        lore.add(" ");
        lore.add(ChatColor.YELLOW + get("constants.menu.left_click_edit"));
        lore.add(ChatColor.YELLOW + get("constants.menu.right_click_delete"));

        meta.setLore(lore);

        meta.addItemFlags(ItemFlag.values());
        item.setItemMeta(meta);

        NBTWrapper nbt = of(item);
        nbt.setID("manage:soundevent");
        nbt.set("selection", s);
        item = nbt.getItem();

        return item;
    }

    @NotNull
    public static ItemStack toItemStack(@NotNull StructureInfo info) {
        ItemStack item = new ItemStack(info.getPrimaryMaterial());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + info.getLocalizedName());

        List<String> lore = new ArrayList<>();
        lore.add(info.getRarity().toString());
        lore.add(" ");
        lore.add(ChatColor.YELLOW + info.getCriteria().getDisplayMessage());

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.values());
        item.setItemMeta(meta);

        NBTWrapper nbt = of(item);
        nbt.setID("spawn:structure");
        nbt.set("info", info);
        item = nbt.getItem();

        return item;
    }

    private static final List<Material> ITEMS = Arrays.stream(Material.values()).filter(w::isItem).collect(Collectors.toList());

    @NotNull
    public static Material toMaterial(@NotNull Sound s) {
        String n = s.name();

        switch (n.toLowerCase()) {
            case "item_bucket_empty_axolotl": return matchMaterial("AXOLOTL_BUCKET");
            case "entity_parrot_imitate_illusioner": return matchMaterial("CROSSBOW");
        }

        if (n.startsWith("item.crossbow")) return matchMaterial("CROSSBOW");

        List<Material> possible = new ArrayList<>();

        // Takes Absolute Priority
        for (Material m : ITEMS) {
            if (n.equals(m.name())) return m;
            if (n.contains(m.name()) || m.name().contains(n)) possible.add(m);
        }

        if (!possible.isEmpty()) {
            possible.sort(Comparator.comparingDouble(m ->
                    (StarUtil.levenshteinDistance(n, m.name()) + StarUtil.levenshteinDistance(m.name(), n)) / 2.0D));
            return possible.get(0);
        }

        if (n.startsWith("ENTITY")) {
            String entity = n.split("_")[1].toLowerCase();

            for (EntityType e : EntityType.values())
                if (n.contains(e.name())) return toMaterial(e);

            switch (entity) {
                case "generic": return LEATHER_CHESTPLATE;
                case "fishing_bobber":
                case "bobber": return FISHING_ROD;
                case "hostile": return ROTTEN_FLESH;
                case "item": return CHEST;
                case "leash_knot": return LEAD.find();
                case "mooshroom": return toMaterial(EntityType.MUSHROOM_COW);
                case "puffer_fish": return matchMaterial("PUFFERFISH");
            }

            return POPPY.find();
        }

        if (n.startsWith("ITEM")) {
            String mat = n.split("_")[1].toLowerCase();
            if (matchMaterial(mat) != null) return matchMaterial(mat);

            switch (mat) {
                case "axe": return IRON_AXE;
                case "hoe": return IRON_HOE;
                case "shovel": return IRON_SHOVEL.find();
                case "pickaxe": return IRON_PICKAXE;
                case "bottle": return GLASS_BOTTLE;
                case "armor": return IRON_CHESTPLATE;
                case "dye": return POPPY.find();
                case "firecharge": return FIRE_CHARGE.find();
                case "totem": TOTEM_OF_UNDYING.find();
                case "lodestone_compass": return COMPASS;
            }

            return APPLE;
        }

        if (n.startsWith("BLOCK")) {
            String mat = n.split("_")[1].toLowerCase();
            if (matchMaterial(mat) != null) return matchMaterial(mat);

            switch (mat) {
                case "wool": return WHITE_WOOL.find();
                case "wood": return OAK_LOG.find();
                case "wooden_button": return OAK_BUTTON.find();
                case "tripwire": return TRIPWIRE_HOOK;
            }

            List<Material> blockPossible = new ArrayList<>();

            for (Material m : ITEMS) {
                if (mat.equals(m.name())) return m;
                if (mat.contains(m.name()) || m.name().contains(mat)) blockPossible.add(m);
            }

            if (!blockPossible.isEmpty()) {
                blockPossible.sort(Comparator.comparingDouble(m ->
                        (StarUtil.levenshteinDistance(n, m.name()) + StarUtil.levenshteinDistance(m.name(), n)) / 2.0D));
                return blockPossible.get(0);
            }
        }

        if (n.contains("GENERIC")) return LEATHER_CHESTPLATE;
        if (n.contains("VILLAGER")) return EMERALD;

        if (n.startsWith("MUSIC"))
            if (n.contains("OVERWORLD")) return GRASS_BLOCK.find();
            else if (n.contains("NETHER")) return NETHERRACK;
            else return NOTE_BLOCK;

        if (n.startsWith("RECORD")) return JUKEBOX;
        if (n.startsWith("UI")) return REDSTONE_BLOCK;
        if (n.startsWith("WEATHER")) return BUCKET;
        if (n.startsWith("ENCHANT")) return ENCHANTED_BOOK;
        if (n.startsWith("EVENT")) return REDSTONE;
        if (n.startsWith("PARTICLE")) return SOUL_SAND;

        return STONE;
    }

    @NotNull
    public static String getFriendlyName(@Nullable Sound s) {
        if (s == null) return "";
        return w.getKey(s);
    }

    public static void setScrolls(StarInventory inv) {
        inv.setAttribute("row_count", 0);
        int size = inv.getSize();

        int upM;
        int downM;

        switch (size) {
            case 54: upM = 26; downM = 35; break;
            default: upM = 17; downM = 26; break;
        }

        ItemStack up = getHead("arrow_up");
        ItemMeta uMeta = up.getItemMeta();
        uMeta.setDisplayName(ChatColor.GREEN + get("constants.up"));
        up.setItemMeta(uMeta);
        NBTWrapper uNBT = NBTWrapper.of(up);
        uNBT.setID("scroll_up");
        up = uNBT.getItem();
        inv.setItem(upM, up);

        ItemStack down = getHead("arrow_down");
        ItemMeta dMeta = down.getItemMeta();
        dMeta.setDisplayName(ChatColor.GREEN + get("constants.down"));
        down.setItemMeta(dMeta);
        NBTWrapper dNBT = NBTWrapper.of(down);
        dNBT.setID("scroll_down");
        down = dNBT.getItem();
        inv.setItem(downM, down);
    }

    public static ItemStack getHead(String key) {
        try {
            Properties p = new Properties();
            p.load(InventorySelector.class.getResourceAsStream("/util/heads.properties"));

            String value = p.getProperty(key);
            if (value == null) return null;

            ItemStack head = w.isLegacy() ? new ItemStack(matchMaterial("SKULL_ITEM"), 1, (short) 3) : new ItemStack(matchMaterial("PLAYER_HEAD"));
            SkullMeta hMeta = (SkullMeta) head.getItemMeta();

            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            profile.getProperties().put("textures", new Property("textures", value));
            Method mtd = hMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            mtd.setAccessible(true);
            mtd.invoke(hMeta, profile);

            head.setItemMeta(hMeta);
            return head;
        } catch (IOException | ReflectiveOperationException e) {
            StarConfig.print(e);
        }

        return null;
    }

    public static void setRows(Inventory inv, Map<Integer, List<ItemStack>> rows) {
        setRows(inv, rows, 0);
    }

    public static void setRows(Inventory inv, Map<Integer, List<ItemStack>> rows, int start) {
        for (int i = 0; i < 4; i++) {
            List<ItemStack> row = rows.get(i + start);
            if (row == null || row.isEmpty()) continue;

            for (int j = 0; j < 7; j++) {
                ItemStack item = j > row.size() - 1 ? new ItemStack(Material.AIR) : row.get(j);
                if (item == null) continue;

                inv.setItem((i + 1) * 9 + j + 1, item);
            }
        }
    }

    @NotNull
    public static ItemStack toItemStack(@NotNull Cosmetic c) {
        ItemStack item = new ItemStack(c.getIcon());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + c.getDisplayName());
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        NBTWrapper nbt = of(item);
        nbt.setID("cosmetic:selection");
        nbt.set("cosmetic", c.getNamespace());
        nbt.set("display", c.getDisplayName());

        if (c instanceof Trail<?>) nbt.set("trail_type", ((Trail<?>) c).getType().name());

        return nbt.getItem();
    }

    @NotNull
    public static String toInputString(@NotNull Object input) {
        if (input == null) throw new IllegalArgumentException("Null Input");

        if (input instanceof Sound || input instanceof StarSound) {
            Sound s = input instanceof StarSound ? ((StarSound) input).find() : (Sound) input;
            return getFriendlyName(s);
        }

        if (input instanceof ItemStack)
            return toInputString(((ItemStack) input).getType());

        if (input instanceof Enum<?>) {
            Enum<?> e = (Enum<?>) input;
            return WordUtils.capitalizeFully(e.name().replace("_", " "));
        }

        if (input instanceof Collection<?>) {
            Collection<?> c = (Collection<?>) input;
            if (c.isEmpty()) throw new IllegalArgumentException("Empty Input List");

            return toInputString(c.stream().findFirst().orElse(null));
        }

        if (input instanceof String) {
            String s = input.toString();
            if (s.contains(":")) {
                String prefix = s.split(":")[0];
                String value = WordUtils.capitalizeFully(s.split(":")[1].replace("_", " "));
                
                switch (prefix) {
                    case "crack": return getWithArgs("constants.cosmetics.crack", value);
                    case "dust": return getWithArgs("constants.cosmetics.dust", value);
                }

                return value;
            }

            return WordUtils.capitalizeFully(s);
        }

        throw new IllegalArgumentException("Unexpected input type: " + input + " (" + input.getClass().getName() + ")");
    }

    @NotNull
    public static Material toInputMaterial(@NotNull Object input) {
        if (input instanceof Material || input instanceof StarMaterial) return input instanceof StarMaterial ? ((StarMaterial) input).find() : (Material) input;
        if (input instanceof Particle) return toMaterial((Particle) input);
        if (input instanceof EntityType) return toMaterial((EntityType) input);
        if (input instanceof Sound) return toMaterial((Sound) input);
        if (input instanceof Collection<?>) {
            Collection<?> c = (Collection<?>) input;
            if (c.isEmpty()) throw new IllegalArgumentException("Empty Input List");

            return toInputMaterial(c.stream().findFirst().orElse(null));
        }

        if (input instanceof String) {
            String s = input.toString();
            if (s.contains(":")) {
                String value = s.split(":")[1];
                try {
                    return StarMaterial.valueOf(value.toUpperCase()).find();
                } catch (IllegalArgumentException e) { return matchMaterial(value); }
            }

            switch (s.toLowerCase()) {
                case "riptide": return matchMaterial("TRIDENT");
            }
        }

        throw new IllegalArgumentException("Unexpected input type: " + input + " (" + input.getClass().getName() + ")");
    }

    @NotNull
    public static ItemStack toItemStack(@NotNull Player p, @NotNull CosmeticLocation<?> loc) {
        Object input = loc.getInput();

        ItemStack item = input instanceof ItemStack ? ((ItemStack) input).clone() : new ItemStack(toInputMaterial(input));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + loc.getParent().getDisplayName() + " | " + loc.getDisplayName());
        meta.addItemFlags(ItemFlag.values());

        List<String> lore = new ArrayList<>();
        lore.add(loc.getRarity().toString());

        ChatColor c = loc.isUnlocked(p) ? ChatColor.GREEN : ChatColor.RED;

        if (!loc.getRarity().hasVisibleRequirements() || loc.isUnlocked(p)) {
            lore.add(" ");
            CompletionCriteria criteria = loc.getCompletionCriteria();

            lore.addAll(Arrays.stream(
                    ChatPaginator.wordWrap(criteria.getDisplayMessage(), 30)
            ).map(s -> c + s).collect(Collectors.toList()));
            lore.add(ChatColor.DARK_GREEN + getWithArgs("constants.completed", String.format("%,.2f", criteria.getProgressPercentage(p)) + "%"));
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTWrapper nbt = NBTWrapper.of(item);
        nbt.setID("choose:cosmetic");
        nbt.set("location", loc);

        item = nbt.getItem();

        return item;
    }

    @NotNull
    public static Material toMaterial(@NotNull EntityType type) {
        for (Material mat : Material.values()) {
            if (mat.name().equalsIgnoreCase(type.name())) return mat;
            if (mat.name().equalsIgnoreCase(type.name() + "_SPAWN_EGG")) return mat;
        }

        switch (type.name()) {
            case "WITHER": return WITHER_SKELETON_SKULL.find();
            case "ENDER_DRAGON": return DRAGON_EGG;
            case "EXPERIENCE_ORB": return EXPERIENCE_BOTTLE.find();
            case "BOAT": return OAK_BOAT.find();
            case "DRAGON_FIREBALL": return FIRE_CHARGE.find();
            case "IRON_GOLEM": return CARVED_PUMPKIN.find();
            case "PLAYER": return PLAYER_HEAD.find();
            case "SHULKER_BULLET": return PURPUR_BLOCK;
            case "EVOKER_FANGS": return TOTEM_OF_UNDYING.find();
        }

        return APPLE;
    }

    public static void setBack(@NotNull StarInventory inv, int slot, @NotNull Consumer<Player> action) {
        ItemStack back = getHead("arrow_left");
        ItemMeta meta = back.getItemMeta();
        meta.setDisplayName(ChatColor.RED + get("constants.back"));
        back.setItemMeta(meta);

        NBTWrapper nbt = of(back);
        nbt.setID("back");
        back = nbt.getItem();

        inv.setItem(slot, back);
        inv.setAttribute("back_inventory_action", action);
    }

    public static void setBack(@NotNull StarInventory inv, @NotNull Consumer<Player> action) {
        setBack(inv, inv.getSize() - 5, action);
    }

    public static void setPages(@NotNull List<StarInventory> pages) {
        AtomicInteger index = new AtomicInteger(0);
        pages.forEach(inv -> {
            inv.setAttribute("pages", pages);
            inv.setAttribute("current_page", index.getAndIncrement());
        });

        ItemStack prev = getHead("arrow_left_gray");
        ItemMeta pMeta = prev.getItemMeta();
        pMeta.setDisplayName(ChatColor.YELLOW + get("constants.previous_page"));
        prev.setItemMeta(pMeta);
        prev = NBTWrapper.setID(prev, "previous_page");

        ItemStack next = getHead("arrow_right_gray");
        ItemMeta nMeta = next.getItemMeta();
        nMeta.setDisplayName(ChatColor.YELLOW + get("constants.next_page"));
        next.setItemMeta(nMeta);
        next = NBTWrapper.setID(next, "next_page");

        for (int i = 0; i < pages.size(); i++) {
            StarInventory page = pages.get(i);
            page.setAttribute("page", i);

            if (i > 0) page.setItem(47, prev);
            if (i < pages.size() - 1) page.setItem(51, next);
        }
    }

    @NotNull
    public static Material toMaterial(@NotNull Particle particle) {
        for (Material m : Material.values()) if (particle.name().equalsIgnoreCase(m.name())) return m;

        switch (particle.name().toLowerCase()) {
            case "white_ash":
            case "ash": return FLINT_AND_STEEL;
            case "block_crack":
            case "block_dust":
            case "block_marker": return STONE;
            case "bubble_column_up":
            case "bubble_pop": return matchMaterial("SEAGRASS");
            case "campfire_cosy_smoke":
            case "campfire_signal_smoke": return matchMaterial("CAMPFIRE");
            case "cloud": return WHITE_WOOL.find();
            case "composter": return matchMaterial("COMPOSTER");
            case "crimson_spore": return matchMaterial("CRIMSON_NYLIUM");
            case "crit": return DIAMOND_SWORD;
            case "crit_magic": return ENCHANTED_BOOK;
            case "current_down": return matchMaterial("TRIDENT");
            case "damage_indicator": return IRON_AXE;
            case "dolphin": return matchMaterial("DOLPHIN_SPAWN_EGG");
            case "dragon_breath": return DRAGON_EGG;
            case "dripping_dripstone_lava":
            case "falling_dripstone_lava":
            case "falling_lava":
            case "landing_lava":
            case "lava":
            case "drip_lava": return LAVA_BUCKET;
            case "enchantment_table": return ENCHANTING_TABLE.find();
            case "dripping_dripstone_water":
            case "falling_dripstone_water":
            case "falling_water":
            case "drip_water": return WATER_BUCKET;
            case "falling_honey":
            case "landing_honey":
            case "dripping_honey": return matchMaterial("HONEY_BLOCK");
            case "falling_obsidian_tear":
            case "landing_obsidian_tear":
            case "dripping_obsidian_tear": return matchMaterial("CRYING_OBSIDIAN");
            case "falling_nectar":
            case "wax_on":
            case "wax_off": return matchMaterial("HONEYCOMB");
            case "dust_color_transition": return FLINT;
            case "electric_spark": return END_ROD;
            case "explosion_huge":
            case "explosion_large":
            case "explosion_normal": return TNT;
            case "falling_dust": return GRAVEL;
            case "spore_blossom_air":
            case "falling_spore_blossom": return matchMaterial("SPORE_BLOSSOM");
            case "fireworks_spawrk": return FIREWORK;
            case "small_flame":
            case "flame": return TORCH;
            case "flash": return matchMaterial("LIGHTNING_ROD");
            case "glow": return SEA_LANTERN;
            case "glow_squid_ink": return matchMaterial("GLOW_INK_SAC");
            case "heart": return GOLDEN_APPLE;
            case "item_crack": return IRON_PICKAXE;
            case "mob_appearence": return ROTTEN_FLESH;
            case "nautilus": return matchMaterial("NAUTILUS_SHELL");
            case "note": return NOTE_BLOCK;
            case "reverse_portal":
            case "portal": return OBSIDIAN;
            case "scrape": return IRON_BLOCK;
            case "sculk_charge":
            case "sculk_charge_pop":
            case "vibration":
            case "sculk_soul": return matchMaterial("SCULK_SENSOR");
            case "shriek": return matchMaterial("SCULK_SHRIEKER");
            case "slime": return SLIME_BLOCK;
            case "smoke_large":
            case "smoke_normal": return FURNACE;
            case "sneeze": return PAPER;
            case "snow_shovel": return IRON_SHOVEL.find();
            case "snowflake":
            case "snowball": return SNOW_BALL;
            case "sonic_boom": return matchMaterial("SCULK");
            case "soul": return SOUL_SAND;
            case "soul_fire_flame": return matchMaterial("SOUL_TORCH");
            case "spell_mob":
            case "spell_witch":
            case "spell_mob_ambient":
            case "spell": return ENCHANTMENT_TABLE;
            case "spit": return LEAD.find();
            case "squid_ink": return INK_SACK;
            case "suspended_depth":
            case "suspended": return STRING;
            case "sweep_attack": return GOLD_SWORD;
            case "totem": return TOTEM_OF_UNDYING.find();
            case "villager_angry":
            case "villager_happy":
            case "town_aura": return EMERALD;
            case "warped_spore": return matchMaterial("WARPED_NYLIUM");
            case "water_drop":
            case "water_wake":
            case "water_bubble": return LILY_PAD.find();
        }

        return CLAY_BALL;
    }

    @SafeVarargs
    private static <T> void add(@NotNull List<T> l, T... elements) {
        l.addAll(Arrays.asList(elements));
    }

    @NotNull
    public static List<Integer> getGUIPlacements(int size, int itemCount) {
        List<Integer> placements = new ArrayList<>();
        if (size == 36 || size == 54) return placements;

        Function<Integer, Integer> mod = place -> place + ((size - 27) / 2);

        switch (itemCount) {
            case 1:
                add(placements, mod.apply(13));
                break;
            case 2:
                add(placements, mod.apply(12), mod.apply(14));
                break;
            case 3:
                add(placements, mod.apply(11),
                        mod.apply(13), mod.apply(15));
                break;
            case 4:
                add(placements, mod.apply(11), mod.apply(12),
                        mod.apply(14), mod.apply(15));
                break;
            case 5:
                add(placements, mod.apply(11), mod.apply(12), mod.apply(13),
                        mod.apply(14), mod.apply(15));
                break;
            case 6:
                add(placements, mod.apply(10), mod.apply(11), mod.apply(12),
                        mod.apply(14), mod.apply(15), mod.apply(16));
                break;
            case 7:
                add(placements,
                        mod.apply(10), mod.apply(11), mod.apply(12), mod.apply(13),
                        mod.apply(14), mod.apply(15), mod.apply(16));
                break;

            // 45+
            case 8: add(placements, 11, 12, 14, 15, 29, 30, 32, 33); break;
            case 9: add(placements, 11, 12, 13, 14, 15, 29, 30, 32, 33); break;
            case 10: add(placements, 11, 12, 13, 14, 15, 29, 30, 31, 32, 33); break;
            case 11: add(placements, 10, 11, 12, 14, 15, 16, 29, 30, 31, 32, 33); break;
            case 12: add(placements, 10, 11, 12, 14, 15, 16, 28, 29, 30, 32, 33, 34); break;
        }

        return placements;
    }

    @NotNull
    public static ItemStack toItemStack(@NotNull Player p, @NotNull PetInfo info) {
        CompletionCriteria criteria = info.getCriteria();

        ItemStack item = info.getIcon().clone();
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GOLD + info.getName());
        List<String> lore = new ArrayList<>();
        ChatColor c = criteria.isUnlocked(p) ? ChatColor.GREEN : ChatColor.RED;

        lore.add(info.getRarity().toString());
        lore.add(" ");
        lore.addAll(Arrays.stream(
                ChatPaginator.wordWrap(criteria.getDisplayMessage(), 30)
        ).map(s -> c + s).collect(Collectors.toList()));
        lore.add(ChatColor.DARK_GREEN + getWithArgs("constants.completed", String.format("%,.2f", criteria.getProgressPercentage(p)) + "%"));
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

    public static void setReset(@NotNull StarInventory inv) {
        Cosmetic parent = inv.getAttribute("parent", Cosmetic.class);

        ItemStack reset = new ItemStack(Material.BARRIER);
        ItemMeta meta = reset.getItemMeta();

        if (Trail.class.isAssignableFrom(parent.getClass())) {
            TrailType type = ((Trail<?>) parent).getType();

            meta.setDisplayName(ChatColor.RED + get("constants.cosmetics.reset.trail"));
            reset.setItemMeta(meta);

            NBTWrapper nbt = of(reset);
            nbt.setID("cancel:cosmetic:trail");
            nbt.set("trail", type.name());
            reset = nbt.getItem();
        } else if (Hat.class.isAssignableFrom(parent.getClass())) {
            meta.setDisplayName(ChatColor.RED + get("constants.cosmetics.reset.hat"));
            reset.setItemMeta(meta);

            NBTWrapper nbt = of(reset);
            nbt.setID("cancel:cosmetic:hat");
            reset = nbt.getItem();
        } else {
            meta.setDisplayName(ChatColor.RED + get("constants.cosmetics.reset"));
            reset.setItemMeta(meta);

            NBTWrapper nbt = of(reset);
            nbt.setID("cancel:cosmetic");
            nbt.set("cosmetic", parent.getClass());
            reset = nbt.getItem();
        }

        inv.setItem(18, reset);
    }


}
