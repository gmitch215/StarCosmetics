package me.gamercoder215.starcosmetics.util;

import me.gamercoder215.starcosmetics.api.StarConfig;
import org.bukkit.entity.Explosive;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.Metadatable;

import java.util.Arrays;

public final class StarUtil {

    public static <T extends Metadatable> T cosmetic(T m) {
        m.setMetadata("cosmetic", new FixedMetadataValue(StarConfig.getPlugin(), true));

        if (m instanceof Explosive) {
            Explosive e = (Explosive) m;
            e.setIsIncendiary(false);
            e.setYield(0);
        }

        return m;
    }
    
    public static int levenshteinDistance(String s1, String s2) {
        if (s1 == null || s2 == null) return -1;
        if (s1.equals(s2)) return 0;

        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
 
        for (int i = 0; i <= s1.length(); i++)
            for (int j = 0; j <= s2.length(); j++)
                if (i == 0) dp[i][j] = j;
                else if (j == 0) dp[i][j] = i;
                else {
                    char c1 = s1.charAt(i - 1);
                    char c2 = s2.charAt(j - 1);
                    dp[i][j] = Arrays.stream(new int[] {
                            dp[i - 1][j - 1] + (c1 == c2 ? 0 : 1),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1
                    }).min().orElse(Integer.MAX_VALUE);
                }
 
        return dp[s1.length()][s2.length()];
    }

}
