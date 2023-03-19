import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.0"
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.9-R0.1-SNAPSHOT")
}

description = "The official StarCosmetics API, available for external use"

tasks {
    javadoc {
        enabled = true
        title = "StarCosmetics ${project.version} API"

        sourceSets["main"].allJava.srcDir("src/main/javadoc")

        options {
            require(this is StandardJavadocDocletOptions)

            overview = "src/main/javadoc/overview.html"
            links("https://hub.spigotmc.org/javadocs/spigot/")
        }
    }

    register("sourcesJar", Jar::class.java) {
        dependsOn("classes")

        archiveFileName.set("StarCosmetics-API-${project.version}-sources.jar")
        from(sourceSets["main"].allSource)
    }

    register("javadocJar", Jar::class.java) {
        dependsOn("javadoc")

        archiveFileName.set("StarCosmetics-API-${project.version}-javadoc.jar")
        from(javadoc.get().destinationDir)
    }

    withType<ShadowJar> {
        archiveFileName.set("StarCosmetics-API-${project.version}.jar")
    }
}

artifacts {
    add("archives", tasks["sourcesJar"])
    add("archives", tasks["javadocJar"])
}