package me.gamercoder215.starcosmetics.util.selection;

import com.google.common.collect.ImmutableList;
import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.AnimatedItem;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseCape;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.capes.Cape;
import me.gamercoder215.starcosmetics.api.player.StarPlayerUtil;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

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

    public static Pattern[] patterns(DyeColor color, PatternType... types) {
        return Arrays.stream(types)
                .map(type -> new Pattern(color, type))
                .toArray(Pattern[]::new);
    }

    public static ItemStack cape(StarMaterial base, Object... objects) {
        List<Pattern> patterns = new ArrayList<>();
        for (Object obj : objects) {
            if (obj instanceof Pattern)
                patterns.add((Pattern) obj);
            else if (obj instanceof Pattern[])
                patterns.addAll(Arrays.asList((Pattern[]) obj));
        }

        return cape(base.findStack(), patterns);
    }

    public static ItemStack cape(StarMaterial base, Pattern[]... patterns) {
        return cape(base.findStack(), Arrays.stream(patterns).flatMap(Arrays::stream).collect(Collectors.toList()));
    }

    public static ItemStack cape(StarMaterial base, Pattern... patterns) {
        return cape(base.findStack(), Arrays.asList(patterns));
    }

    public static ItemStack cape(ItemStack base, Iterable<Pattern> patterns) {
        ItemStack cape = base.clone();
        BannerMeta meta = (BannerMeta) cape.getItemMeta();
        meta.setPatterns(ImmutableList.copyOf(patterns));
        cape.setItemMeta(meta);

        return cape;
    }

    public static AnimatedItem of(long interval, Iterable<ItemStack> frames) {
        AnimatedItem.Builder builder = AnimatedItem.builder(StarPlayerUtil::setCape);
        for (ItemStack item : frames)
            builder.addFrame(interval, item);

        return builder.build();
    }

    public static AnimatedItem of(long interval, StarMaterial... frames) {
        return of(interval, Arrays.stream(frames).map(StarMaterial::findStack).collect(Collectors.toList()));
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
        return getWithArgs("constants.cape", WordUtils.capitalizeFully(name.replace('_', ' ')));
    }

    @Override
    public @NotNull Cosmetic getParent() {
        return parent;
    }

    @Override
    public Class<? extends Cosmetic> getParentClass() {
        return Cape.class;
    }

    @Override
    public @NotNull Class<?> getInputType() {
        return Object.class;
    }
    
}
