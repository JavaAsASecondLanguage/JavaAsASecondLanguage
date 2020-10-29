group = "io.github.javaasasecondlanguage"
version = "1.0-SNAPSHOT"

plugins {
    java
    id("org.springframework.boot") version "2.3.4.RELEASE"
}

java {
    sourceCompatibility = JavaVersion.VERSION_14
    targetCompatibility = JavaVersion.VERSION_14
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator:2.3.4.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-web:2.3.4.RELEASE")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.3.4.RELEASE") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    runtimeOnly(files("../frontend-1.0-SNAPSHOT.jar"))
}

tasks.test {
    useJUnitPlatform()
}