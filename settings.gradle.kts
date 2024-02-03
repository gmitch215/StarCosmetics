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

mapOf(
    "1_9_R1" to 8,
    "1_9_R2" to 8,
    "1_10_R1" to 8,
    "1_11_R1" to 8,
    "1_12_R1" to 8,
    "1_13_R1" to 8,
    "1_13_R2" to 8,
    "1_14_R1" to 8,
    "1_15_R1" to 8,
    "1_16_R1" to 8,
    "1_16_R2" to 8,
    "1_16_R3" to 8,
    "1_17_R1" to 16,
    "1_18_R1" to 17,
    "1_18_R2" to 17,
    "1_19_R1" to 17,
    "1_19_R2" to 17,
    "1_19_R3" to 17,
    "1_20_R1" to 17,
    "1_20_R2" to 17,
    "1_20_R3" to 17
).forEach {
    val id = it.key
    val minJava = it.value

    if (JavaVersion.current().isCompatibleWith(JavaVersion.toVersion(minJava))) {
        include(":starcosmetics-$id")
        project(":starcosmetics-$id").projectDir = rootDir.resolve("nms/$id")
    }
}