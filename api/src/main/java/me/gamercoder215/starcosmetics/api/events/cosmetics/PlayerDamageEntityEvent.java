package me.gamercoder215.starcosmetics.api.events.cosmetics;

import com.google.common.base.Function;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static me.gamercoder215.starcosmetics.api.events.cosmetics.PlayerTakeDamageByEntityEvent.getDeclaredField;

/**
 * Called when a player damages another entity
 */
@SuppressWarnings("unchecked")
public class PlayerDamageEntityEvent extends EntityDamageByEntityEvent {

    /**
     * Called when a player takes damage from an entity
     * @param event The event to copy
     */
    public PlayerDamageEntityEvent(@NotNull EntityDamageByEntityEvent event) {
        super((Player) event.getDamager(), event.getEntity(), event.getCause(), (Map<DamageModifier, Double>) getDeclaredField(EntityDamageEvent.class, event, "modifiers"), (Map<DamageModifier, ? extends Function<? super Double, Double>>) getDeclaredField(EntityDamageEvent.class, event, "modifierFunctions"));
    }

    /**
     * Called when a player takes damage from an entity
     * @param damager The player that damaged the entity
     * @param damagee The entity that was damaged
     * @param cause The cause of the damage
     * @param modifiers The damage modifiers
     * @param modifierFunctions The damage modifier functions
     */
    public PlayerDamageEntityEvent(@NotNull Player damager, @NotNull Entity damagee, DamageCause cause, Map<DamageModifier, Double> modifiers, Map<DamageModifier, ? extends Function<? super Double, Double>> modifierFunctions) {
        super(damager, damagee, cause, modifiers, modifierFunctions);
    }

    @Override
    public Player getDamager() {
        return (Player) super.getDamager();
    }

    // Clone method needed for reflection

    /**
     * Gets the player that damaged the entity.
     * @return The player that damaged the entity
     */
    @NotNull
    public Player getPlayer() {
        return getDamager();
    }

}
