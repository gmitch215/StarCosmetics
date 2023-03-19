@file:Suppress("UnstableApiUsage")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("org.sonarqube") version "4.0.0.2929"
    id("com.github.johnrengelman.shadow") version "8.1.0" apply false

    java
    `maven-publish`
}

val pGroup = "me.gamercoder215.starcosmetics"
val pVersion = "1.1.0"
val pAuthor = "StarCosmetics"

sonarqube {
    properties {
        property("sonar.projectKey", "${pAuthor}_StarCosmetics")
        property("sonar.organization", "gamercoder215")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

allprojects {
    group = pGroup
    version = pVersion
    description = "Advanced and Open-Source Cosmetics Plugin for Spigot 1.9+"

    repositories {
        mavenCentral()
        mavenLocal()

        maven("https://jitpack.io")
        maven("https://plugins.gradle.org/m2/")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://oss.sonatype.org/content/repositories/central")

        maven("https://repo.codemc.org/repository/nms/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://hub.jeff-media.com/nexus/repository/jeff-media-public/")
        maven("https://libraries.minecraft.net/")
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }
}

val jvmVersion = JavaVersion.VERSION_1_8

subprojects {
    apply<JavaPlugin>()
    apply(plugin = "org.sonarqube")

    dependencies {
        compileOnly("org.jetbrains:annotations:24.0.1")

        testImplementation("org.mockito:mockito-core:5.2.0")
        testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")

        testImplementation("org.spigotmc:spigot-api:1.9-R0.1-SNAPSHOT")
        testImplementation("net.md-5:bungeecord-chat:1.16-R0.4")
    }

    java {
        sourceCompatibility = jvmVersion
        targetCompatibility = jvmVersion
    }

    tasks {
        compileJava {
            options.encoding = "UTF-8"
            options.isDeprecation = false
            options.isWarnings = false
            options.compilerArgs.addAll(listOf("-Xlint:all", "-Xlint:-processing"))
        }

        test {
            useJUnitPlatform()
            testLogging {
                events("passed", "skipped", "failed")
            }
        }

        javadoc {
            enabled = false
            options.encoding = "UTF-8"
            options.memberLevel = JavadocMemberLevel.PROTECTED
        }

        jar.configure {
            enabled = false
            dependsOn("shadowJar")
        }

        withType<ShadowJar> {
            manifest {
                attributes["Implementation-Title"] = project.name
                attributes["Implementation-Version"] = project.version
                attributes["Implementation-Vendor"] = pAuthor
            }

            relocate("revxrsal.commands", "me.gamercoder215.shaded.lamp")
            relocate("org.bstats.bukkit", "me.gamercoder215.shaded.bstats")

            archiveFileName.set("${project.name}-${project.version}.jar")
        }
    }
}