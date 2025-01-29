plugins {
	java
	id("org.springframework.boot") version "3.4.2"
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
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


tasks.withType<JavaCompile> {
	// This won't help much.
	// It won’t prevent incompatible or newer (Java 21) third-party 
	// dependencies from breaking your build. Those dependencies must be
	// pinned to versions that support Java 17 or you’ll hit a “class 
	// file major version” mismatch.
    options.compilerArgs.addAll(listOf("--release", "21"))
}

tasks.withType<Test> {
	useJUnitPlatform()
}
