package me.gamercoder215.starcosmetics.wrapper;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

public interface CommandWrapper {

    Map<String, List<String>> COMMANDS = ImmutableMap.<String, List<String>>builder()

            .build();

    Map<String, String> COMMAND_PERMISSION = ImmutableMap.<String, String>builder()

            .build();

    Map<String, String> COMMAND_DESCRIPTION = ImmutableMap.<String, String>builder()

            .build();

    Map<String, String> COMMAND_USAGE = ImmutableMap.<String, String>builder()

            .build();

    // Command Methods

}
