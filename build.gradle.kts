import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.41")
    id("com.github.johnrengelman.shadow").version("5.1.0")
    application
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
    mavenCentral()
}

val vertxVersion = "3.8.0"
val coroutineVersion = "1.3.0-RC2"
val reflectionVersion = "1.3.50"
val fastJsonVersion = "1.2.61"
val sqliteVersion = "3.28.0"
val captchaVersion = "1.6.2"

val hibernateVersion = "5.4.8.Final"
val sqliteDialect = "0.1.0"
val jpaVersion = "0.1.0"

dependencies {
    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    gradle
    implementation("com.alibaba:fastjson:$fastJsonVersion")
    implementation("io.vertx:vertx-core:$vertxVersion")
    implementation("io.vertx:vertx-web:$vertxVersion")
    implementation("io.vertx:vertx-web-client:$vertxVersion")
    implementation("io.vertx:vertx-lang-kotlin:$vertxVersion")
    implementation("io.vertx:vertx-lang-kotlin-coroutines:$vertxVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$reflectionVersion")

    implementation("org.xerial:sqlite-jdbc:$sqliteVersion")
    implementation("org.hibernate:hibernate-core:$hibernateVersion")
    implementation("com.github.gwenn:sqlite-dialect:$sqliteDialect")
    implementation("org.eclipse.persistence:eclipselink:$jpaVersion")

    implementation("com.github.whvcse:easy-captcha:$captchaVersion")


    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "1.8"

application {
    // Define the main class for the application
    mainClassName = "cn.banjiaojuhao.lab.se.AppKt"
}