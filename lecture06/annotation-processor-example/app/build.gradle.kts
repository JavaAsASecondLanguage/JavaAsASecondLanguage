group = "io.github.javaasasecondlanguage"
version = "1.0-SNAPSHOT"

dependencies {
    // javac --annotation-processor [*.jar] <- gradle build immutable-processor [sources]
    annotationProcessor(project(":immutable-annotation-processor"))
    implementation(project(":immutable-annotation"))
}
