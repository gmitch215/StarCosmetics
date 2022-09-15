package me.gamercoder215.starcosmetics.api.cosmetics;

import java.util.function.BiConsumer;

import org.bukkit.entity.Projectile;

public enum BaseTrail implements BiConsumer<Projectile, Object> {
    
    ARROW_TRAIL((p, o) -> {
        // TODO
    });
    ;

    private BiConsumer<Projectile, Object> trail;

    BaseTrail(BiConsumer<Projectile, Object> runnable) {
        this.trail = runnable;
    }

    @Override
    public void accept(Projectile t, Object u) {
        trail.accept(t, u);
    }

}
