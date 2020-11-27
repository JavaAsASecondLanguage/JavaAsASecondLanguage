group = "io.github.javaasasecondlanguage"
version = "1.0-SNAPSHOT"

plugins {
    java
    checkstyle
    application
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
    // jol
    implementation("org.openjdk.jol:jol-core:0.14")
    // jol samples
    implementation("org.openjdk.jol:jol-samples:0.14")
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
        jvmArgs("-XX:-UseCompressedOops")
        //jvmArgs("--enable-preview")
        //jvmArgs("-XX:+UnlockExperimentalVMOptions")
        //jvmArgs("-XX:+UseEpsilonGC");
    }
}

application {
    mainClass.set("io.github.javaasasecondlanguage.lecture09.practice1.GarbageGenerator")
}