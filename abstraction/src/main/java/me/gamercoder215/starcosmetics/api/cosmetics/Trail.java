package me.gamercoder215.starcosmetics.api.cosmetics;

import java.util.function.BiConsumer;
import org.bukkit.entity.Entity;

public interface Trail extends CosmeticKey, BiConsumer<Entity, Object> {
    
    @Override
    default void accept(Object... args) {
        accept((Entity) args[0], args[1]);
    }

}
