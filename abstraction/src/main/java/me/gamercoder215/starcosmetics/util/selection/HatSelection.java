package me.gamercoder215.starcosmetics.util.selection;

import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseHat;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.AnimatedItem;
import me.gamercoder215.starcosmetics.api.cosmetics.hat.Hat;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.util.inventory.StarInventoryUtil;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import static me.gamercoder215.starcosmetics.util.inventory.StarInventoryUtil.itemBuilder;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.get;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.getWithArgs;

public final class HatSelection extends CosmeticSelection<Object> {

    private final Hat parent;
    private final String name;

    public HatSelection(String name, Material object, CompletionCriteria criteria, Rarity rarity) {
        this(name, new ItemStack(object), criteria, rarity);
    }

    public HatSelection(String name, ItemStack object, CompletionCriteria criteria, Rarity rarity) {
        super(NBTWrapper.builder(object, meta -> meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES), nbt -> nbt.set("hat", true)), criteria, rarity);

        this.parent = BaseHat.NORMAL;
        this.name = name;
    }

    public HatSelection(String name, String skullOwner, CompletionCriteria criteria, Rarity rarity) {
        this(name, StarInventoryUtil.cleanSkull(itemBuilder(StarMaterial.PLAYER_HEAD.find(), meta -> ((SkullMeta) meta).setOwner(skullOwner))), criteria, rarity);
    }

    public HatSelection(String name, AnimatedItem data, CompletionCriteria criteria, Rarity rarity) {
        super(data.map(i -> NBTWrapper.builder(i, meta -> meta.setDisplayName(" "), nbt -> nbt.set("hat", true))), criteria, rarity);

        this.parent = BaseHat.ANIMATED;
        this.name = name;
    }

    @Override
    public @NotNull String getKey() {
        return name;
    }

    @Override
    public @NotNull String getDisplayName() {
        Object o = getInput();

        switch (name) {
            case "ores": return getWithArgs("constants.hat", get("constants.cosmetics.structures.ores"));
        }

        if (o instanceof ItemStack) {
            ItemStack input = (ItemStack) o;
            String str;
            if (input.getItemMeta() instanceof SkullMeta) {
                String s = ((SkullMeta) input.getItemMeta()).getOwner();
                if (s.contains("MHF_"))
                    str = s.split("MHF_")[1];
                else
                    str = s;
            } else str = StarInventoryUtil.toInputString(input);

            return getWithArgs("constants.hat",
                    get("cosmetics.hat." + name, str)
            );
        } else {
            AnimatedItem data = (AnimatedItem) o;
            ItemStack input = data.getFrames().get(0).getValue();
            String str;
            if (input.getItemMeta() instanceof SkullMeta) {
                String s = ((SkullMeta) input.getItemMeta()).getOwner();
                if (s.contains("MHF_"))
                    str = s.split("MHF_")[1];
                else
                    str = s;
            } else str = StarInventoryUtil.toInputString(input);

            return getWithArgs("constants.hat",
                    get("cosmetics.hat." + name, str)
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
    public @NotNull Class<? extends ItemStack> getInputType() {
        return ItemStack.class;
    }

}
