plugins {
    id("com.github.johnrengelman.shadow") version "8.1.0"
}

val mcVersion = "1.17.1"

dependencies {
    implementation(project(":starcosmetics-abstraction"))
    implementation(project(":starcosmetics-api"))
    implementation(project(":starcosmetics-1_12_R1"))

    compileOnly("org.spigotmc:spigot:$mcVersion-R0.1-SNAPSHOT")
}

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}