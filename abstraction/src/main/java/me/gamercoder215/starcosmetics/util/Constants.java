package me.gamercoder215.starcosmetics.util;

import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public interface Constants {

    SecureRandom r = new SecureRandom();

    List<Cosmetic> PARENTS = new ArrayList<>();

}
