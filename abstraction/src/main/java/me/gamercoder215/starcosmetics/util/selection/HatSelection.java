package me.gamercoder215.starcosmetics.util.selection;

import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseHat;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
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

public final class HatSelection extends CosmeticSelection<ItemStack> {

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

    @Override
    public @NotNull String getKey() {
        return name;
    }

    @Override
    public @NotNull String getDisplayName() {
        ItemStack input = getInput();

        boolean head = input.getType() == StarMaterial.PLAYER_HEAD.find();
        String owner = head ? ((SkullMeta) input.getItemMeta()).getOwner() : "";
        boolean mhf = owner.startsWith("MHF_");

        return getWithArgs("constants.hat",
                get("cosmetics.hat." + name, head && mhf ? owner.split("_")[1] : StarInventoryUtil.toInputString(input))
        );
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
