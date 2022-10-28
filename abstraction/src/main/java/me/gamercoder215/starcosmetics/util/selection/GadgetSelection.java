package me.gamercoder215.starcosmetics.util.selection;

import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseGadget;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class GadgetSelection<T extends Event> extends CosmeticSelection<ItemStack> {

    private final String name;
    private final ItemStack item;
    private final Consumer<T> eventAction;
    private final Class<T> eventClass;

    private GadgetSelection(String name, Class<T> eventClass, CompletionCriteria criteria, Rarity rarity, ItemStack item, Consumer<T> action) {
        super(item, criteria, rarity);
        this.name = name;
        this.item = item;
        this.eventAction = action;
        this.eventClass = eventClass;
    }

    public static <T extends Event> Builder<T> builder(Class<T> eventClass) {
        return new Builder<>(eventClass);
    }

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public Cosmetic getParent() {
        return BaseGadget.CLICK_GADGET;
    }

    public ItemStack getItem() {
        return item;
    }

    public Class<T> getEventClass() {
        return this.eventClass;
    }

    public void run(T event) {
        eventAction.accept(event);
    }

    public static final class Builder<T extends Event> {

        ItemStack item;
        String id;
        Class<T> eventClass;
        Consumer<T> action;
        Rarity rarity;
        CompletionCriteria criteria;

        private Builder(Class<T> eventClass) {
            this.eventClass = eventClass;
        }

        public Builder<T> info(String id, CompletionCriteria criteria, Rarity rarity) {
            this.id = id;
            this.rarity = rarity;
            this.criteria = criteria;
            return this;
       }

        public Builder<T> item(ItemStack item) {
            this.item = item;
            return this;
        }

        public Builder<T> item(Material m) {
            return item(new ItemStack(m));
        }

        public Builder<T> action(Consumer<T> action) {
            this.action = action;
            return this;
        }

        public GadgetSelection<T> build() {
            return new GadgetSelection<>(id, eventClass, criteria, rarity, item, action);
        }

    }

    @Override
    public @NotNull Class<ItemStack> getInputType() {
        return ItemStack.class;
    }

}
