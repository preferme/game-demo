
buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("com.github.node-gradle:gradle-node-plugin:3.5.1")
    }
}

apply(plugin = "com.github.node-gradle.node")


//configure<com.github.gradle.node.NodeExtension> {
//    download = false
//}

tasks.register<com.github.gradle.node.npm.task.NpmTask>("npmBuild") {
    dependsOn(":npmInstall")
    group = "npm"
    args.set(listOf("run", "build"))
}

//tasks.named("jar") {
//    dependsOn(":npmBuild")
//}

tasks.register("npmDev", com.github.gradle.node.npm.task.NpmTask::class) {
    group = "npm"
    args.set(listOf("run", "dev"))
}


