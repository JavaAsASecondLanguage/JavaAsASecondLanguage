plugins {
    java
}

subprojects {
    apply(plugin = "java")

    java {
        sourceCompatibility = JavaVersion.VERSION_14
        targetCompatibility = JavaVersion.VERSION_14
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
    }
}
