val mcVersion = "1.13.2"
val lampVersion = "3.1.5"

dependencies {
    api(project(":starcosmetics-abstraction"))
    api(project(":starcosmetics-api"))

    implementation("com.github.Revxrsal.Lamp:bukkit:$lampVersion")
    implementation("com.github.Revxrsal.Lamp:common:$lampVersion")

    compileOnly("org.spigotmc:spigot:$mcVersion-R0.1-SNAPSHOT")
}