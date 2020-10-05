import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "5.1.0"
}

tasks.withType<ShadowJar>() {
    manifest {
        attributes["Main-Class"] = "io.github.javaasasecondlanguage.homework02.webserver.Application"
    }
}

dependencies {
    implementation(project(":di"))
    implementation("org.slf4j:slf4j-log4j12:1.7.30")
}
