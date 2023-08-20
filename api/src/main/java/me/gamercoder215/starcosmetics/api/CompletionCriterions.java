package me.gamercoder215.starcosmetics.api;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * An enum for completion criteria shown in the configuration file.
 * @see CompletionCriteria
 */
public enum CompletionCriterions {

    ;

    private final int length;
    private final Function<Object[], CompletionCriteria> factory;

    CompletionCriterions(int length, Function<Object[], CompletionCriteria> factory) {
        this.factory = factory;
        this.length = length;
    }

    /**
     * Creates a new {@link CompletionCriteria} with the given arguments.
     * @param args the arguments to pass to the factory
     * @return the new {@link CompletionCriteria}
     * @throws IllegalArgumentException if the factory fails
     */
    @NotNull
    public CompletionCriteria create(Object... args) throws IllegalArgumentException {
        if (args.length != length) throw new IllegalArgumentException("Invalid number of arguments for " + this + " (expected " + length + ", got " + args.length + ")");
        return factory.apply(args);
    }
}
