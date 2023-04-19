plugins {
    java
    idea
    id("com.github.node-gradle.node") version "3.5.1"
    id("org.springframework.boot") version "2.4.2"  apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE"  apply false
}

group = "hl.game"
version = "1.0"

allprojects {
    repositories {
        mavenCentral()
    }
}

repositories {
    mavenCentral()
}

val lombok_version: String by project
val commons_lang3_version: String by project


subprojects {
    apply(plugin = "java")

    dependencies{
        compileOnly("org.projectlombok:lombok:${lombok_version}")
        annotationProcessor("org.projectlombok:lombok:${lombok_version}")

        testCompileOnly("org.projectlombok:lombok:${lombok_version}")
        testAnnotationProcessor("org.projectlombok:lombok:${lombok_version}")

        implementation("org.apache.commons:commons-lang3:${commons_lang3_version}")

    }
}