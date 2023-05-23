package me.gamercoder215.starcosmetics.util.selection;

import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseHat;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.hat.AnimatedHatData;
import me.gamercoder215.starcosmetics.api.cosmetics.hat.Hat;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.util.inventory.StarInventoryUtil;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import static me.gamercoder215.starcosmetics.util.inventory.StarInventoryUtil.itemBuilder;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.get;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.getWithArgs;

import java.util.Arrays;
import java.util.Objects;

public final class HatSelection extends CosmeticSelection<Object> {

    private final Hat parent;
    private final String name;

    public HatSelection(String name, Material object, CompletionCriteria criteria, Rarity rarity) {
        this(name, new ItemStack(object), criteria, rarity);
    }

    public HatSelection(String name, ItemStack object, CompletionCriteria criteria, Rarity rarity) {
        super(NBTWrapper.builder(object, nbt -> nbt.set("hat", true)), criteria, rarity);

        this.parent = BaseHat.NORMAL;
        this.name = name;
    }

    public HatSelection(String name, String skullOwner, CompletionCriteria criteria, Rarity rarity) {
        this(name, itemBuilder(StarMaterial.PLAYER_HEAD.find(), meta -> ((SkullMeta) meta).setOwner(skullOwner)), criteria, rarity);
    }

    public HatSelection(String name, AnimatedHatData data, CompletionCriteria criteria, Rarity rarity) {
        super(data.map(i -> NBTWrapper.builder(i, nbt -> nbt.set("hat", true))), criteria, rarity);

        this.parent = BaseHat.ANIMATED;
        this.name = name;
    }

    public static AnimatedHatData of(long interval, ItemStack... frames) {
        AnimatedHatData.Builder builder = AnimatedHatData.builder();
        for (ItemStack item : frames)
            builder.addFrame(interval, item);
        
        return builder.build();
    }

    public static AnimatedHatData of(long interval, Material... frames) {
        return of(interval, Arrays.stream(frames)
                .filter(Objects::nonNull)
                .map(ItemStack::new)
                .toArray(ItemStack[]::new)
        );
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
            String owner = "";
            if (input.getItemMeta() instanceof SkullMeta) {
                String s = ((SkullMeta) input.getItemMeta()).getOwner();
                if (s.contains("MHF_"))
                    owner = s.split("MHF_")[1];
                else
                    owner = s;
            }

            return getWithArgs("constants.hat",
                    get("cosmetics.hat." + name, isMHF(input) ? owner.split("_")[1] : StarInventoryUtil.toInputString(input))
            );
        } else {
            AnimatedHatData data = (AnimatedHatData) o;
            ItemStack input = data.getFrames().get(0).getValue();
            String owner = "";
            if (input.getItemMeta() instanceof SkullMeta) {
                String s = ((SkullMeta) input.getItemMeta()).getOwner();
                if (s.contains("MHF_"))
                    owner = s.split("MHF_")[1];
                else
                    owner = s;
            }

            return getWithArgs("constants.hat",
                    get("cosmetics.hat." + name, isMHF(input) ? owner.split("_")[1] : StarInventoryUtil.toInputString(input))
            );   
        }
    }

    private static boolean isMHF(ItemStack input) {
        return input.getType() == StarMaterial.PLAYER_HEAD.find() && ((SkullMeta) input.getItemMeta()).getOwner().startsWith("MHF_");
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
    public @NotNull Class<? extends ItemStack> getInputType() {
        return ItemStack.class;
    }

}
