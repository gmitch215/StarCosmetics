package me.gamercoder215.starcosmetics.api.events.cosmetics;

import com.google.common.base.Function;
import me.gamercoder215.starcosmetics.api.StarConfig;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Called when a player takes damage from an entity.
 */
@SuppressWarnings("unchecked")
public class PlayerTakeDamageByEntityEvent extends EntityDamageByEntityEvent {

    static <T> Object getDeclaredField(Class<T> clazz, T instance, String name) {
        try {
            return clazz.getDeclaredField(name).get(instance);
        } catch (ReflectiveOperationException e) {
            StarConfig.print(e);
            return null;
        }
    }

    /**
     * Called when a player takes damage from an entity
     * @param event The event to copy
     */
    public PlayerTakeDamageByEntityEvent(@NotNull EntityDamageByEntityEvent event) {
        super(event.getDamager(), (Player) event.getEntity(), event.getCause(), (Map<DamageModifier, Double>) getDeclaredField(EntityDamageEvent.class, event, "modifiers"), (Map<DamageModifier, ? extends Function<? super Double, Double>>) getDeclaredField(EntityDamageEvent.class, event, "modifierFunctions"));
    }

    /**
     * Called when a player takes damage from an entity
     * @param damager The entity that damaged the player
     * @param damagee The player that was damaged
     * @param cause The cause of the damage
     * @param modifiers The damage modifiers
     * @param modifierFunctions The damage modifier functions
     */
    public PlayerTakeDamageByEntityEvent(@NotNull Entity damager, @NotNull Player damagee, DamageCause cause, Map<DamageModifier, Double> modifiers, Map<DamageModifier, ? extends Function<? super Double, Double>> modifierFunctions) {
        super(damager, damagee, cause, modifiers, modifierFunctions);
    }

    @Override
    public Player getEntity() {
        return (Player) super.getEntity();
    }

    // Clone method needed for reflection

    /**
     * Gets the player that damaged the entity.
     * @return The player that damaged the entity
     */
    @NotNull
    public Player getPlayer() {
        return getEntity();
    }
}
