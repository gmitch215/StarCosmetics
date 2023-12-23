@file:Suppress("UnstableApiUsage")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("org.sonarqube") version "4.0.0.2929"
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false

    java
    `maven-publish`
    `java-library`
    jacoco
}

val pGroup = "me.gamercoder215.starcosmetics"
val pVersion = "1.3.1"
val pAuthor = "GamerCoder215"

sonarqube {
    properties {
        property("sonar.projectKey", "${pAuthor}_StarCosmetics")
        property("sonar.organization", "gamercoder215")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

allprojects {
    group = pGroup
    version = pVersion
    description = "Advanced and Open-Source Cosmetics Plugin for Spigot 1.9+"

    apply(plugin = "maven-publish")
    apply<JavaPlugin>()
    apply<JavaLibraryPlugin>()

    repositories {
        mavenCentral()
        mavenLocal()

        maven("https://jitpack.io")
        maven("https://plugins.gradle.org/m2/")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://oss.sonatype.org/content/repositories/central")

        maven("https://repo.codemc.org/repository/nms/")
        maven("https://repo.codemc.org/repository/maven-public/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://hub.jeff-media.com/nexus/repository/jeff-media-public/")
        maven("https://libraries.minecraft.net/")
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = pGroup
                artifactId = project.name
                version = pVersion

                pom {
                    description.set(project.description)
                    licenses {
                        license {
                            name.set("GPL-3.0")
                            url.set("https://github.com/GamerCoder215/StarCosmetics/blob/master/LICENSE")
                        }
                    }
                    scm {
                        connection.set("scm:git:git://GamerCoder215/StarCosmetics.git")
                        developerConnection.set("scm:git:ssh://GamerCoder215/StarCosmetics.git")
                        url.set("https://github.com/GamerCoder215/StarCosmetics")
                    }
                }

                from(components["java"])
            }
        }

        repositories {
            maven {
                credentials {
                    username = System.getenv("JENKINS_USERNAME")
                    password = System.getenv("JENKINS_PASSWORD")
                }

                val releases = "https://repo.codemc.io/repository/maven-releases/"
                val snapshots = "https://repo.codemc.io/repository/maven-snapshots/"
                url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshots else releases)
            }
        }
    }
}

val jvmVersion = JavaVersion.VERSION_1_8

subprojects {
    apply<JacocoPlugin>()
    apply(plugin = "org.sonarqube")
    apply(plugin = "com.github.johnrengelman.shadow")

    dependencies {
        compileOnly("org.jetbrains:annotations:24.1.0")

        testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
        testImplementation("org.mockito:mockito-core:5.8.0")

        testImplementation("org.spigotmc:spigot-api:1.9-R0.1-SNAPSHOT")
        testImplementation("net.md-5:bungeecord-chat:1.20-R0.1")
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

        jacocoTestReport {
            dependsOn(test)

            reports {
                csv.required.set(false)

                xml.required.set(true) // SonarCloud
                xml.outputLocation.set(layout.buildDirectory.file("jacoco/jacoco.xml"));

                html.required.set(true)
                html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
            }
        }

        test {
            useJUnitPlatform()
            testLogging {
                events("passed", "skipped", "failed")
            }
            finalizedBy(jacocoTestReport)
        }

        javadoc {
            enabled = false
            options.encoding = "UTF-8"
            options.memberLevel = JavadocMemberLevel.PROTECTED
        }

        jar {
            dependsOn("shadowJar")
            archiveClassifier.set("dev")
        }

        withType<ShadowJar> {
            manifest {
                attributes(
                    "Implementation-Title" to project.name,
                    "Implementation-Version" to project.version,
                    "Implementation-Vendor" to pAuthor
                )
            }
            exclude("META-INF", "META-INF/**")

            relocate("revxrsal.commands", "me.gamercoder215.starcosmetics.shaded.lamp")
            relocate("org.bstats", "me.gamercoder215.starcosmetics.shaded.bstats")
            relocate("com.jeff_media.updatechecker", "me.gamercoder215.starcosmetics.shaded.updatechecker")

            archiveFileName.set("${project.name}-${project.version}.jar")
            archiveClassifier.set("")
        }
    }

    artifacts {
        add("default", tasks.getByName<ShadowJar>("shadowJar"))
    }
}