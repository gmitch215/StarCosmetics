rootProject.name = "StarCosmetics"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

include(":starcosmetics")
project(":starcosmetics").projectDir = rootDir.resolve("plugin")

listOf("api", "abstraction").forEach {
    include(":starcosmetics-$it")
    project(":starcosmetics-$it").projectDir = rootDir.resolve(it)
}

listOf(
    "1_9_R1",
    "1_9_R2",
    "1_10_R1",
    "1_11_R1",
    "1_12_R1",
    "1_13_R1",
    "1_13_R2",
    "1_14_R1",
    "1_15_R1",
    "1_16_R1",
    "1_16_R2",
    "1_16_R3",
    "1_17_R1",
    "1_18_R1",
    "1_18_R2",
    "1_19_R1",
    "1_19_R2",
    "1_19_R3",
    "1_20_R1"
).forEach {
    include(":starcosmetics-$it")
    project(":starcosmetics-$it").projectDir = rootDir.resolve("nms/$it")
}