group = "io.github.javaasasecondlanguage"
version = "1.0-SNAPSHOT"

dependencies {
    compileOnly("com.google.auto.service:auto-service:1.0-rc6")
    annotationProcessor("com.google.auto.service:auto-service:1.0-rc6")
    implementation(project(":immutable-annotation"))
}
