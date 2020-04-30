plugins {
    kotlin("jvm") version "1.3.61"
}

group = "org.flyfish"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven {
        setUrl("https://jitpack.io")
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.shaunxiao:kotlinGameEngine:v0.1.0")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}