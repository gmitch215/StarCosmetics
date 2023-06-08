package me.gamercoder215.starcosmetics.util;

import org.bukkit.Particle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum StarParticle {

    CHERRY_LEAVES("falling_cherry_leaves")

    ;

    private final String[] names;

    StarParticle(String... names) {
        List<String> list = new ArrayList<>();
        list.add(name());
        list.addAll(Arrays.asList(names));

        this.names = list.toArray(new String[0]);
    }

    @Override
    public String toString() {
        return find().name();
    }

    public Particle find() {
        for (String s : names) {
            try {
                Particle p = Particle.valueOf(s.toUpperCase());
                if (p != null) return p;
            } catch (IllegalArgumentException ignored) {}
        }

        throw new AssertionError("No particle found for " + name());
    }

}
