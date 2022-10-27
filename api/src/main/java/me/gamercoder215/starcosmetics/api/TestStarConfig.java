package me.gamercoder215.starcosmetics.api;

import org.jetbrains.annotations.NotNull;

class TestStarConfig implements StarConfig {

    @Override
    public @NotNull String getLanguage() {
        return "en";
    }

    @Override
    public @NotNull String get(String key) {
        return "";
    }

    @Override
    public @NotNull String getMessage(String key) {
        return "";
    }
}
