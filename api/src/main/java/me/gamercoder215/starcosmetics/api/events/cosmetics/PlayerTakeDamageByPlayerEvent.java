package me.gamercoder215.starcosmetics.api.events.cosmetics;

import com.google.common.base.Function;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Map;

/**
 * Called when a player takes damage from another player.
 */
public class PlayerTakeDamageByPlayerEvent extends PlayerTakeDamageByEntityEvent {

    /**
     * Called when a player takes damage from another player.
     * @param event The event to copy
     */
    public PlayerTakeDamageByPlayerEvent(EntityDamageByEntityEvent event) {
        super(event);
    }

    /**
     * Called when a player takes damage from another player.
     * @param damager The player that damaged the player
     * @param damagee The player that was damaged
     * @param cause The cause of the damage
     * @param modifiers The damage modifiers
     * @param modifierFunctions The damage modifier functions
     */
    public PlayerTakeDamageByPlayerEvent(Player damager, Player damagee, DamageCause cause, Map<DamageModifier, Double> modifiers, Map<DamageModifier, ? extends Function<? super Double, Double>> modifierFunctions) {
        super(damager, damagee, cause, modifiers, modifierFunctions);
    }

    @Override
    public Player getDamager() {
        return (Player) super.getDamager();
    }

}
