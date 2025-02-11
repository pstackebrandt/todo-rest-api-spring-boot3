plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	// A Gradle plugin that provides Maven-like dependency management functionality
	id("io.spring.dependency-management") version "1.1.7"
}

val projectVersion = "0.7.0"

group = "com.example"
version = projectVersion

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
	implementation("org.springframework.boot:spring-boot-starter-validation")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testRuntimeOnly("org.mockito:mockito-inline:5.2.0")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.withType<JavaCompile> {
	// This supports the detection of incompatible
	// third-party dependencies. But it may not help much in 
	// addition to the toolchain configuration.
    options.compilerArgs.addAll(listOf("--release", "21"))
}

tasks.withType<Test> {
	useJUnitPlatform()
	testLogging {
		events("passed", "skipped", "failed")
		showStandardStreams = true
		showExceptions = true
		showCauses = true
		showStackTraces = true
	}
	
	jvmArgs(
		"-XX:+EnableDynamicAgentLoading",
		"-Djdk.instrument.traceUsage=false"
	)
}
