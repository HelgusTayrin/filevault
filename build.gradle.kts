val koinVersion: String by project
val kotlinVersion: String by project

plugins {
	id("org.springframework.boot") version "2.6.7"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("org.jetbrains.kotlin.plugin.jpa") version "1.5.21"
	id("org.jetbrains.kotlin.plugin.allopen") version "1.5.21"
	kotlin("jvm")
	kotlin("plugin.spring")
	application
}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("javax.persistence.Embedabble")
}

group = "com.fileService"
version = "0.0.1"

repositories {
	mavenCentral()
}

dependencies {

	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.7")
	implementation("org.springframework.boot:spring-boot-starter-data-rest:2.6.7")
	implementation("org.springframework.boot:spring-boot-starter-tomcat:2.6.7")
	implementation("org.postgresql:postgresql:42.3.4")

	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

	implementation("javax.persistence:javax.persistence-api:2.2")


	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

kotlin.sourceSets["main"].kotlin.srcDir("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testResources")

//tasks.withType<KotlinCompile> {
//	kotlinOptions {
//		freeCompilerArgs = listOf("-Xjsr305=strict")
//		jvmTarget = "11"
//	}
//}

tasks.withType<Test> {
	useJUnitPlatform()
}

application {
	mainClass.set("FileServiceApplication.kt")
}