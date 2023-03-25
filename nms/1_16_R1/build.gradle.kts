val mcVersion = "1.16.1"

dependencies {
    api(project(":starcosmetics-abstraction"))
    api(project(":starcosmetics-api"))
    api(project(":starcosmetics-1_12_R1"))

    compileOnly("org.spigotmc:spigot:$mcVersion-R0.1-SNAPSHOT")
}