plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "vp"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    //USER PERSISTENCE
    implementation(project(":user:persistence"))

    //WEBFLUX
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    //MONGODB
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")


    //TEST
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation ("org.mockito:mockito-core")

    //COROUTINE
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-rx3")

    //REFLECT
    implementation("org.jetbrains.kotlin:kotlin-reflect")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}