package me.gamercoder215.starcosmetics.wrapper.commands;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticRegistry;
import me.gamercoder215.starcosmetics.api.cosmetics.structure.StructureInfo;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.TrailType;
import me.gamercoder215.starcosmetics.util.StarSound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import revxrsal.commands.annotation.*;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.bukkit.BukkitCommandHandler;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import static me.gamercoder215.starcosmetics.wrapper.Wrapper.sendError;

@SuppressWarnings("unchecked")
final class CommandWrapperV2 implements CommandWrapper {

    private static BukkitCommandHandler handler;

    public <T extends Plugin & CosmeticRegistry> CommandWrapperV2(final T plugin) {
        if (handler != null) return;
        handler = BukkitCommandHandler.create(plugin);

        handler.getAutoCompleter()
                .registerSuggestion("structures", SuggestionProvider.map(plugin::getAvailableStructures, StructureInfo::getLocalizedName))
                .registerSuggestion("parents", SuggestionProvider.of(PARENTS_INFO.keySet()));

        handler.register(
                this,
                new CosmeticCommands()
        );

        handler.registerBrigadier();
        handler.setLocale(StarConfig.getConfig().getLocale());

        plugin.getLogger().info("Loaded Command Wrapper V2 (1.13+)");
    }

    @Override
    @Command({"starsettings", "ssettings", "settings", "ss"})
    @Description("Opens the StarCosmetics settings menu.")
    @Usage("/starsettings")
    @CommandPermission("starcosmetics.user.settings")
    public void settings(Player p) {
        CommandWrapper.super.settings(p);
    }

    @Override
    @Command({"starhelp", "shelp"})
    @Description("Displays help for StarCosmetics.")
    @Usage("/starhelp")
    public void help(CommandSender sender) { CommandWrapper.super.help(sender); }

    @Override
    @Command({"starreload", "sreload", "sr"})
    @Description("Reloads the StarCosmetics configuration.")
    @Usage("/starreload")
    @CommandPermission("starcosmetics.admin.reloadconfig")
    public void reloadConfig(CommandSender sender) {
        CommandWrapper.super.reloadConfig(sender);
    }

    @Override
    @Command({"starabout", "sabout", "sa", "stara"})
    @Description("Displays information about StarCosmetics.")
    @Usage("/starabout")
    public void about(Player p) { CommandWrapper.super.about(p); }

    // Cosmetic Commands

    @Command({"starcosmetics", "scosmetics", "sc", "cosmetics", "cs"})
    @Description("Opens the StarCosmetics Cosmetics menu.")
    @Usage("/starcosmetics")
    @CommandPermission("starcosmetics.user.cosmetics")
    final class CosmeticCommands {

        private final CommandWrapperV2 wrapper;

        CosmeticCommands() {
            this.wrapper = CommandWrapperV2.this;
        }

        @DefaultFor({"starcosmetics", "scosmetics", "sc", "cosmetics", "cs"})
        public void cosmetics(Player p) { wrapper.cosmetics(p); StarSound.ENTITY_ARROW_HIT_PLAYER.playSuccess(p); }

        @Subcommand({"pets", "pet"})
        @AutoComplete("remove")
        public void pets(Player p, @Optional String args) { wrapper.pets(p, args); }

        @Subcommand({"structures", "structure"})
        @AutoComplete("@structures *")
        public void structures(Player p, @Optional String structure) { wrapper.structures(p, structure); }

        @Subcommand("trails")
        public void trails(Player p) { wrapper.trails(p); }

        @Subcommand({"shapes", "particleshapes", "particles"})
        public void shapes(Player p) { wrapper.shapes(p); }

        @Subcommand({"customsounds", "sounds"})
        @AutoComplete("add")
        public void soundSelection(Player p, @Optional String args) { wrapper.soundSelection(p, args == null ? null : args.split("\\s")); }

        @Subcommand({"hat", "hats"})
        public void hats(Player p) { wrapper.hats(p); }

        @Subcommand({"info", "equipped"})
        @AutoComplete("@parents *")
        public void cosmeticInfo(Player p, @Single String cosmetic) { wrapper.cosmeticInfo(p, cosmetic); }
    }

    @Override
    @Command({"starstructures", "sstructures", "sstr"})
    @Description("Opens the StarCosmetics Structures menu.")
    @Usage("/starstructures")
    @CommandPermission("starcosmetics.user.cosmetics")
    @AutoComplete("@structures *")
    public void structures(Player p, @Optional String structure) {
        CommandWrapper.super.structures(p, structure);
    }

    @Command({"starpets", "starp", "sp", "spets", "pets"})
    @Description("Opens the StarCosmetics Pets menu.")
    @Usage("/starpets")
    @CommandPermission("starcosmetics.user.cosmetics")
    @AutoComplete("remove")
    public void pets(Player p, @Optional String args) { CommandWrapper.super.pets(p, args == null ? null : args.split("\\s")); }

    @Command({"starcosmeticinfo", "cosmeticinfo", "scinfo", "scosmeticinfo", "cinfo"})
    @Description("Displays information about the cosmetic the player has currently equipped.")
    @Usage("/starcosmeticinfo <cosmetic>")
    @CommandPermission("starcosmetics.user.cosmetics")
    @AutoComplete("@parents *")
    public void cosmeticInfo(Player p, @Single String cosmetic) {
        Object o = PARENTS_INFO.get(cosmetic);
        if (o == null) {
            sendError(p, "error.argument.cosmetic");
            return;
        }

        if (o instanceof TrailType) cosmeticInfo(p, (TrailType) o);
        else cosmeticInfo(p, (Class<? extends Cosmetic>) o);
    }

}
