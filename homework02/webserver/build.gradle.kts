import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "5.1.0"
    jacoco
}

tasks.withType<ShadowJar>() {
    manifest {
        attributes["Main-Class"] = "io.github.javaasasecondlanguage.homework02.webserver.Server"
    }
}

dependencies {
    implementation(project(":di"))
}
