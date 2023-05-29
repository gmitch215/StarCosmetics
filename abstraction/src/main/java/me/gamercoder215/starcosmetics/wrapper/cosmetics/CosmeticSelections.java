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
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CosmeticSelections {

    SecureRandom r = Constants.r;

    Wrapper w = Constants.w;

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
                .nbt(nbt -> nbt.set("pet", key.replace(' ', '_')))
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

            Constructor<?> selConstructor = selClass.getDeclaredConstructor();
            selConstructor.setAccessible(true);
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

    static Map<Cosmetic, List<CosmeticSelection<?>>> getForVersion(String version) {
        try {
            Class<? extends CosmeticSelections> selClass = Class.forName("me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections" + version)
                    .asSubclass(CosmeticSelections.class);

            Constructor<? extends CosmeticSelections> constr = selClass.getDeclaredConstructor();
            constr.setAccessible(true);
            CosmeticSelections sel = constr.newInstance();
            return sel.getAllSelections();
        } catch (ClassNotFoundException e) { // Using unsupported version
            StarConfig.print(e);
            return new HashMap<>();
        } catch (ReflectiveOperationException e) {
            StarConfig.print(e);
        }
        return null;
    }

    static List<CosmeticSelection<?>> getForVersion(Cosmetic key, String version) {
        return getForVersion(version).get(key);
    }

}
