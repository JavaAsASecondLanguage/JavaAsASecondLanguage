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
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.3.4.RELEASE")
    implementation("com.h2database:h2:1.3.148")
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:2.3.2")
    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.3.4.RELEASE") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    runtimeOnly(files("../frontend-1.0-SNAPSHOT.jar"))
}

tasks.test {
    useJUnitPlatform()
}