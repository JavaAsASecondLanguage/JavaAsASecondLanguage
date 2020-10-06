

group = "io.github.javaasasecondlanguage"
version = "1.0-SNAPSHOT"

plugins {
    java
    checkstyle
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "checkstyle")

    java {
        sourceCompatibility = JavaVersion.VERSION_14
        targetCompatibility = JavaVersion.VERSION_14
    }

    tasks.withType<JavaCompile> {
        options.compilerArgs.add("--enable-preview")
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
        jvmArgs("--enable-preview")
    }
    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.junit.jupiter:junit-jupiter:5.4.2")
        implementation("org.junit.jupiter:junit-jupiter:5.4.2")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
    }

    checkstyle {
        toolVersion = "8.36.1"
        config = resources.text.fromFile("../../config/checkstyle/checkstyle.xml")
    }

    tasks.withType<Checkstyle> {
        reports {
            xml.isEnabled = false
            html.isEnabled = true
        }
    }
}
