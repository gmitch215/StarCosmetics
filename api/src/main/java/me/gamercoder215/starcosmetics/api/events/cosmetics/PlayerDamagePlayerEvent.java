package me.gamercoder215.starcosmetics.api.events.cosmetics;

import com.google.common.base.Function;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Called when a player damages another player
 */
public class PlayerDamagePlayerEvent extends PlayerDamageEntityEvent {


    /**
     * Called when a player takes damage from another player
     * @param event The event to copy
     */
    public PlayerDamagePlayerEvent(@NotNull EntityDamageByEntityEvent event) {
        super(event);
    }

    /**
     * Called when a player takes damage from another Player
     * @param damager           The player that damaged the entity
     * @param damagee           The player that was damaged
     * @param cause             The cause of the damage
     * @param modifiers         The damage modifiers
     * @param modifierFunctions The damage modifier functions
     */
    public PlayerDamagePlayerEvent(@NotNull Player damager, @NotNull Player damagee, DamageCause cause, Map<DamageModifier, Double> modifiers, Map<DamageModifier, ? extends Function<? super Double, Double>> modifierFunctions) {
        super(damager, damagee, cause, modifiers, modifierFunctions);
    }

    @Override
    public Player getEntity() {
        return (Player) super.getEntity();
    }
}
