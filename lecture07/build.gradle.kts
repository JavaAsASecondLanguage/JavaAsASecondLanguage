group = "io.github.javaasasecondlanguage"
version = "1.0-SNAPSHOT"

plugins {
    java
    checkstyle
}

java {
    sourceCompatibility = JavaVersion.VERSION_14
    targetCompatibility = JavaVersion.VERSION_14
}

enableJavaPreviewFeatures()

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.0-rc1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
}

checkstyle {
    toolVersion = "8.36.1"
    config = resources.text.fromFile("../config/checkstyle/checkstyle.xml")
}

tasks.withType<Checkstyle> {
    reports {
        xml.isEnabled = false
        html.isEnabled = true
    }
}

fun enableJavaPreviewFeatures() {
    tasks.withType<JavaCompile> {
        options.compilerArgs.add("--enable-preview")
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
        jvmArgs("--enable-preview")
    }
}
