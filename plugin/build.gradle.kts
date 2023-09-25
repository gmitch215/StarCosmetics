dependencies {
    // Spigot
    compileOnly("org.spigotmc:spigot-api:1.9-R0.1-SNAPSHOT") {
        version {
            strictly("1.9-R0.1-SNAPSHOT")
        }
    }
    testImplementation("org.spigotmc:spigot-api:1.20.2-R0.1-SNAPSHOT") {
        version {
            strictly("1.20.2-R0.1-SNAPSHOT")
        }
    }

    // Implementation Dependencies
    implementation("org.bstats:bstats-bukkit:3.0.2")
    implementation("com.jeff_media:SpigotUpdateChecker:3.0.3")

    // Soft Dependencies
    compileOnly("me.clip:placeholderapi:2.11.3")

    // API
    api(project(":starcosmetics-api"))

    listOf(
        "1_9_R1",
        "1_9_R2",
        "1_10_R1",
        "1_11_R1",
        "1_12_R1",
        "1_13_R1",
        "1_13_R2",
        "1_14_R1",
        "1_15_R1",
        "1_16_R1",
        "1_16_R2",
        "1_16_R3",
        "1_17_R1",
        "1_18_R1",
        "1_18_R2",
        "1_19_R1",
        "1_19_R2",
        "1_19_R3",
        "1_20_R1",
        "1_20_R2"
    ).forEach { api(project(":starcosmetics-$it")) }
}


tasks {
    compileJava {
        listOf(
            "1_18_R1",
            "1_18_R2",
            "1_19_R1",
            "1_19_R2",
            "1_19_R3",
            "1_20_R1",
            "1_20_R2"
        ).forEach { dependsOn(project(":starcosmetics-$it").tasks["remap"]) }
    }

    register("sourcesJar", Jar::class.java) {
        dependsOn("classes")
        archiveClassifier.set("sources")

        from(sourceSets["main"].allSource)
    }

    processResources {
        expand(project.properties)
    }

    shadowJar {
        dependsOn("sourcesJar")
    }
}

publishing {
    publications {
        getByName<MavenPublication>("maven") {
            artifact(tasks["sourcesJar"])
        }
    }
}

artifacts {
    add("archives", tasks["sourcesJar"])
}