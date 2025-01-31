plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	// A Gradle plugin that provides Maven-like dependency management functionality
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.6.0"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.4")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


tasks.withType<JavaCompile> {
	// This supports the detection of incompatible
	// third-party dependencies. But it may not help much in 
	// addition to the toolchain configuration.
    options.compilerArgs.addAll(listOf("--release", "21"))
}

tasks.withType<Test> {
	useJUnitPlatform()
}
