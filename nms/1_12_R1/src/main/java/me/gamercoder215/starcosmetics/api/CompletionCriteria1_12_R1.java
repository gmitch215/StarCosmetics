package me.gamercoder215.starcosmetics.api;

import me.gamercoder215.starcosmetics.util.StarAdvancement;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.jetbrains.annotations.NotNull;

import static me.gamercoder215.starcosmetics.util.Constants.w;

public final class CompletionCriteria1_12_R1 {

    @NotNull
    public static CompletionCriteria fromAdvancement(@NotNull StarAdvancement s) {
        Advancement a = s.getAdvancement();

        return new CompletionCriteria(
                p -> p.getAdvancementProgress(a).isDone(),
                p -> {
                    AdvancementProgress progress = p.getAdvancementProgress(a);
                    return (double) progress.getAwardedCriteria().size() / (double) a.getCriteria().size();
                },
                "literal:" + w.getAdvancementDescription(s.getAdvancementId()));
    }

}
