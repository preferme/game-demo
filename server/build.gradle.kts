plugins {
    java
    idea
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":web-admin"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springframework.boot:spring-boot-devtools")
//    implementation("javax.inject:javax.inject:1")
//    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}


//tasks.named("jar") {
//    dependsOn(":web-admin:npmBuild")
//}


val processResources: ProcessResources by tasks
val copyDistToStatic by tasks.registering(Copy::class) {
    from("${rootDir}/web-admin/dist")
    into("${project.projectDir}/src/main/resources/static")
    includeEmptyDirs = true
}
processResources.dependsOn(copyDistToStatic)

val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks
val unpack by tasks.registering(Copy::class) {
    dependsOn(bootJar)
    from(zipTree(bootJar.outputs.files.singleFile))
    into("${buildDir}/dependency")
}

tasks.clean {
    delete(files("${project.projectDir}/src/main/resources/static"))
}

tasks.named("build") {
    finalizedBy(unpack)
}

tasks.test {
    useJUnitPlatform()
}
