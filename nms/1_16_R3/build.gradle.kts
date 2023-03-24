val mcVersion = "1.16.4"

dependencies {
    implementation(project(":starcosmetics-abstraction"))
    implementation(project(":starcosmetics-api"))
    implementation(project(":starcosmetics-1_12_R1"))

    compileOnly("org.spigotmc:spigot:$mcVersion-R0.1-SNAPSHOT")
}