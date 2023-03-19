plugins {
    id("com.github.johnrengelman.shadow") version "8.1.0"
}

val mcVersion = "1.11.2"

dependencies {
    implementation(project(":starcosmetics-abstraction"))
    implementation(project(":starcosmetics-api"))

    compileOnly("org.spigotmc:spigot:$mcVersion-R0.1-SNAPSHOT")
}