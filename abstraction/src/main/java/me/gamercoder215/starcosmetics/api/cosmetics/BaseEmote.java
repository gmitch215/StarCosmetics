package me.gamercoder215.starcosmetics.api.cosmetics;

import me.gamercoder215.starcosmetics.api.cosmetics.emote.Emote;
import me.gamercoder215.starcosmetics.api.player.PlayerSetting;
import me.gamercoder215.starcosmetics.api.player.StarPlayer;
import me.gamercoder215.starcosmetics.util.StarAnimator;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;

import static me.gamercoder215.starcosmetics.api.player.StarPlayerUtil.head;

public final class BaseEmote {

    private BaseEmote() {}

    public static ItemStack dyedArmor(Material material, DyeColor color) {
        ItemStack armor = new ItemStack(material);
        LeatherArmorMeta meta = (LeatherArmorMeta) armor.getItemMeta();
        meta.setColor(color.getColor());
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        armor.setItemMeta(meta);
        return armor;
    }

    public static void emote(@NotNull StarPlayer sp, @NotNull Emote emote) {
        Player p = sp.getPlayer().getPlayer();
        if (p == null) return;

        DyeColor color = sp.getSetting(PlayerSetting.EMOTE_COLOR);
        final int modifier = sp.getSetting(PlayerSetting.PARTICLE_REDUCTION).getModifier();

        ArmorStand stand = p.getWorld().spawn(
                p.getLocation().add(p.getLocation().getDirection().multiply(2.0)),
                ArmorStand.class
        );
        stand.setInvulnerable(true);
        stand.setMarker(true);
        stand.setCollidable(false);
        stand.setArms(true);
        stand.setBasePlate(false);
        stand.setGravity(false);

        stand.setHelmet(head(p));
        stand.setChestplate(dyedArmor(Material.LEATHER_CHESTPLATE, color));
        stand.setLeggings(dyedArmor(Material.LEATHER_LEGGINGS, color));
        stand.setBoots(dyedArmor(Material.LEATHER_BOOTS, color));

        StarAnimator animator = new StarAnimator(emote, stand);
        animator.play(() -> {
            stand.remove();
            stand.getWorld().spawnParticle(Particle.CLOUD, stand.getLocation(), 100 / modifier, 0.5, 0.5, 0.5, 0.1);
        });
    }

}
