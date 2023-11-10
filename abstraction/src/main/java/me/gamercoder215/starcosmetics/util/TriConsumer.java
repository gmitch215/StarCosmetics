package me.gamercoder215.starcosmetics.util;

@FunctionalInterface
public interface TriConsumer<T, U, R> {

    void accept(T t, U u, R r);

}
