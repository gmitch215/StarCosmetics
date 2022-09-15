package me.gamercoder215.starcosmetics.api.cosmetics;

import java.util.function.Consumer;

import org.bukkit.Particle;

public enum BaseParticleCosmetic implements Consumer<Particle> {
    
    SMALL_RING((p -> {
        // TODO
    }));
    ;

    private Consumer<Particle> particle;

    BaseParticleCosmetic(Consumer<Particle> runnable) {
        this.particle = runnable;
    }

    @Override
    public void accept(Particle t) {
        particle.accept(t);
    }

}
