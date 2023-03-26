package me.gamercoder215.starcosmetics.util;

import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.wrapper.DataWrapper;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public interface Constants {

    SecureRandom r = new SecureRandom();

    Wrapper w = Wrapper.getWrapper();

    DataWrapper dw = Wrapper.getDataWrapper();

    List<Cosmetic> PARENTS = new ArrayList<>();

}
