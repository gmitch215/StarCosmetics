import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

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
        archiveClassifier.set("sources")

        from(sourceSets["main"].allSource)
    }

    register("javadocJar", Jar::class.java) {
        dependsOn("javadoc")
        archiveClassifier.set("javadoc")

        from(javadoc.get().destinationDir)
    }

    withType<ShadowJar> {
        dependsOn("sourcesJar", "javadocJar")
    }
}

publishing {
    publications {
        getByName<MavenPublication>("maven") {
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])
        }
    }
}

artifacts {
    add("archives", tasks["sourcesJar"])
    add("archives", tasks["javadocJar"])
}