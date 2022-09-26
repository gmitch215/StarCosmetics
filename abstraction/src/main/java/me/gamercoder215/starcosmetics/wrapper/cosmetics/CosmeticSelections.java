package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticKey;
import me.gamercoder215.starcosmetics.api.cosmetics.selection.CosmeticSelection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

public interface CosmeticSelections {

    SecureRandom r = new SecureRandom();

    Map<CosmeticKey, List<CosmeticSelection>> getAllSelections();

    default List<CosmeticSelection> getSelections(CosmeticKey key) {
        return getAllSelections().get(key);
    }

    @SuppressWarnings("unchecked")
    static Map<CosmeticKey, List<CosmeticSelection>> getForVersion(String version) {
        try {
            Class<?> selClass = Class.forName("me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections" + version);

            Field selectionsF = selClass.getDeclaredField("SELECTIONS");
            selectionsF.setAccessible(true);
            if (!Modifier.isStatic(selectionsF.getModifiers()))
                throw new AssertionError("SELECTIONS field is not static: " + version);

            return (Map<CosmeticKey, List<CosmeticSelection>>) selectionsF.get(null);
        } catch (NoSuchFieldException e) {
          throw new AssertionError("SELECTIONS field not found: " + version);
        } catch (ReflectiveOperationException e) {
            StarConfig.print(e);
        }
        return null;
    }

    static List<CosmeticSelection> getForVersion(CosmeticKey key, String version) {
        return getForVersion(version).get(key);
    }

}
