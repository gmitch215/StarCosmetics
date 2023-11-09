package me.gamercoder215.starcosmetics.util.selection;

import com.google.common.collect.ImmutableList;
import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.AnimatedItem;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseCape;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.capes.Cape;
import me.gamercoder215.starcosmetics.api.cosmetics.hat.Hat;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.util.inventory.StarInventoryUtil;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import static me.gamercoder215.starcosmetics.wrapper.Wrapper.get;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.getWithArgs;

public final class CapeSelection extends CosmeticSelection<Object> {

    private final Cape parent;
    private final String name;

    public CapeSelection(String name, Material object, CompletionCriteria criteria, Rarity rarity) {
        this(name, new ItemStack(object), criteria, rarity);
    }

    public CapeSelection(String name, ItemStack object, CompletionCriteria criteria, Rarity rarity) {
        super(object, criteria, rarity);

        this.parent = BaseCape.NORMAL;
        this.name = name;
    }

    public CapeSelection(String name, AnimatedItem data, CompletionCriteria criteria, Rarity rarity) {
        super(data, criteria, rarity);

        this.parent = BaseCape.ANIMATED;
        this.name = name;
    }

    public static ItemStack cape(StarMaterial base, Pattern... patterns) {
        return cape(base.findStack(), patterns);
    }

    public static ItemStack cape(ItemStack base, Pattern... patterns) {
        return cape(base, Arrays.asList(patterns));
    }

    public static ItemStack cape(ItemStack base, Iterable<Pattern> patterns) {
        ItemStack cape = base.clone();
        BannerMeta meta = (BannerMeta) cape.getItemMeta();
        meta.setPatterns(ImmutableList.copyOf(patterns));
        cape.setItemMeta(meta);

        return cape;
    }

    public static AnimatedItem of(long interval, Iterable<ItemStack> frames) {
        AnimatedItem.Builder builder = AnimatedItem.builder(BaseCape::setCape);
        for (ItemStack item : frames)
            builder.addFrame(interval, item);

        return builder.build();
    }

    public static AnimatedItem of(long interval, ItemStack... frames) {
        return of(interval, Arrays.asList(frames));
    }

    public static AnimatedItem of(long interval, Collection<Material> frames) {
        return of(interval, frames.stream()
                .filter(Objects::nonNull)
                .map(ItemStack::new)
                .collect(Collectors.toList())
        );
    }

    public static AnimatedItem of(long interval, Material... frames) {
        return of(interval, Arrays.asList(frames));
    }

    @Override
    public @NotNull String getKey() {
        return name;
    }

    @Override
    public @NotNull String getDisplayName() {
        Object o = getInput();

        if (o instanceof ItemStack) {
            ItemStack input = (ItemStack) o;
            String str = StarInventoryUtil.toInputString(input);

            return getWithArgs("constants.cape",
                    get("cosmetics.cape." + name, str)
            );
        } else {
            AnimatedItem data = (AnimatedItem) o;
            ItemStack input = data.getFrames().get(0).getValue();
            String str = StarInventoryUtil.toInputString(input);

            return getWithArgs("constants.cape",
                    get("cosmetics.cape." + name, str)
            );
        }
    }

    @Override
    public @NotNull Cosmetic getParent() {
        return parent;
    }

    @Override
    public Class<? extends Cosmetic> getParentClass() {
        return Hat.class;
    }

    @Override
    public @NotNull Class<?> getInputType() {
        return Object.class;
    }
    
}
