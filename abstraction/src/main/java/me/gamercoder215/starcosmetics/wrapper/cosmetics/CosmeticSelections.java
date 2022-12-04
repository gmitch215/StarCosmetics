package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetInfo;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetType;
import me.gamercoder215.starcosmetics.util.Constants;
import me.gamercoder215.starcosmetics.util.inventory.ItemBuilder;
import me.gamercoder215.starcosmetics.util.inventory.StarInventoryUtil;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CosmeticSelections {

    SecureRandom r = Constants.r;

    Wrapper w = Wrapper.getWrapper();

    Map<PetType, PetInfo> PET_MAP = new HashMap<>();

    static ItemStack petIcon(Material m, String key, String name) {
        if (Bukkit.getServer() == null) return null;
        return ItemBuilder.of(m)
                .name(ChatColor.GOLD + name)
                .id("choose:pet")
                .nbt(nbt -> nbt.set("pet", key))
                .build();
    }

    static ItemStack petIcon(Material m, String name) {
        return petIcon(m, name.toLowerCase(), name);

    }

    static Location head(ArmorStand stand) {
        return stand.getLocation().add(0, 0.8, 0);
    }

    static ItemStack petIcon(String headKey, String name) {
        return petIcon(headKey, name.toLowerCase(), name);
    }

    static ItemStack petIcon(String headKey, String key, String name) {
        if (Bukkit.getServer() == null) return null;
        return ItemBuilder.of(StarInventoryUtil.getHead(headKey))
                .name(ChatColor.GOLD + name)
                .id("choose:pet")
                .nbt(nbt -> nbt.set("pet", key))
                .build();
    }

    Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections();

    default void loadPets() {}

    default List<CosmeticSelection<?>> getSelections(Cosmetic key) {
        return getAllSelections().getOrDefault(key, new ArrayList<>());
    }

    static void loadExternalPets(String ver) {
        try {
            Class<?> selClass = Class.forName("me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections" + ver);

            Constructor<?> selConstructor = selClass.getConstructor();
            CosmeticSelections sel = (CosmeticSelections) selConstructor.newInstance();
            sel.loadPets();
        } catch (ClassNotFoundException ignored) { // Using unsupported version
        } catch (ReflectiveOperationException e) {
            StarConfig.print(e);
        }
    }

    @SafeVarargs
    static <T> List<T> join(List<T>... lists) {
        List<T> list = new ArrayList<>();
        for (List<T> l : lists) if (l != null) list.addAll(l);

        return list;
    }

    static List<CosmeticSelection<?>> join(List<CosmeticSelection<?>> list, Cosmetic key, String ver) {
        return join(list, getForVersion(ver).get(key));
    }

    @SuppressWarnings("unchecked")
    static Map<Cosmetic, List<CosmeticSelection<?>>> getForVersion(String version) {
        try {
            Class<?> selClass = Class.forName("me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections" + version);

            Field selectionsF = selClass.getDeclaredField("SELECTIONS");
            selectionsF.setAccessible(true);
            if (!Modifier.isStatic(selectionsF.getModifiers()))
                throw new AssertionError("SELECTIONS field is not static: " + version);

            return (Map<Cosmetic, List<CosmeticSelection<?>>>) selectionsF.get(null);
        } catch (NoSuchFieldException e) {
            throw new AssertionError("SELECTIONS field not found: " + version);
        } catch (ClassNotFoundException e) { // Using unsupported version
            return null;
        } catch (ReflectiveOperationException e) {
            StarConfig.print(e);
        }
        return null;
    }

    static List<CosmeticSelection<?>> getForVersion(Cosmetic key, String version) {
        return getForVersion(version).get(key);
    }

}
