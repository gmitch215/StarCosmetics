val mcVersion = "1.12.2"

dependencies {
    implementation(project(":starcosmetics-abstraction"))
    implementation(project(":starcosmetics-api"))

    compileOnly("org.spigotmc:spigot:$mcVersion-R0.1-SNAPSHOT")
}