val mcVersion = "1.15.2"

dependencies {
    api(project(":starcosmetics-abstraction"))
    api(project(":starcosmetics-api"))

    compileOnly("org.spigotmc:spigot:$mcVersion-R0.1-SNAPSHOT")
}