package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.cosmetics.*;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;

import java.util.List;
import java.util.Map;

import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.getForVersion;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.join;

final class CosmeticSelections1_20_R3 implements CosmeticSelections {

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, getForVersion(BaseTrail.PROJECTILE_TRAIL, "1_20"))
            .put(BaseTrail.GROUND_TRAIL, getForVersion(BaseTrail.GROUND_TRAIL, "1_20"))
            .put(BaseTrail.SOUND_TRAIL, getForVersion(BaseTrail.SOUND_TRAIL, "1_20"))

            .put(BaseShape.ALL, getForVersion(BaseShape.ALL, "1_20"))

            .put(BaseHat.NORMAL, getForVersion(BaseHat.NORMAL, "1_20"))
            .put(BaseHat.ANIMATED, getForVersion(BaseHat.ANIMATED, "1_20"))

            .put(BaseGadget.INSTANCE, getForVersion(BaseGadget.INSTANCE, "1_20"))

            .put(BaseCape.NORMAL, getForVersion(BaseCape.NORMAL, "1_20"))
            .put(BaseCape.ANIMATED, getForVersion(BaseCape.ANIMATED, "1_20"))
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        return SELECTIONS;
    }
    
}
