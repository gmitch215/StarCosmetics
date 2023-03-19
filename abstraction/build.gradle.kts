plugins {
    id("com.github.johnrengelman.shadow") version "8.1.0"
}

description = "Abstraction for StarCosmetics Wrappers"

dependencies {
    implementation(project(":starcosmetics-api"))

    compileOnly("org.spigotmc:spigot-api:1.9-R0.1-SNAPSHOT")
    compileOnly("com.mojang:authlib:1.5.21")
}