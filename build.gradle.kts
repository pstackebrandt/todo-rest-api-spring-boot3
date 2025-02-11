plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	// A Gradle plugin that provides Maven-like dependency management functionality
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = project.property("version") as String

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

tasks.named<ProcessResources>("processResources") {
    filesMatching("**/application.properties") {
        expand(project.properties)
    }
}

// Task to update the Dockerfile with the current version from gradle.properties
tasks.register("updateVersionInDockerfile") {
    doLast {
        val dockerFile = file("Dockerfile")
        if (!dockerFile.exists()) {
            println("Dockerfile not found!")
            return@doLast
        }
        // Use a regular expression to replace the existing ARG VERSION line with the new version
        val updatedContent = dockerFile.readText().replace(Regex("ARG\\s+VERSION=.*")) {
            "ARG VERSION=${project.version}"
        }
        dockerFile.writeText(updatedContent)
        println("Dockerfile updated with version ${project.version}")
    }
}

// Task to update a version badge in your README (if you have one)
tasks.register("updateReadmeVersionBadge") {
    doLast {
        val readmeFile = file("Readme.md")
        if (!readmeFile.exists()) {
            println("Readme.md not found!")
            return@doLast
        }
        // Updated regex:
        // - Uses IGNORE_CASE to match [Version] (or [version])
        // - Matches an optional ".svg" in the suffix
        val regex = Regex(
            "(!\\[version\\]\\(https://img\\.shields\\.io/badge/version-)[^-]+(-blue(?:\\.svg)?\\))",
            RegexOption.IGNORE_CASE
        )
        val updatedContent = readmeFile.readText().replace(regex) {
            "${it.groupValues[1]}${project.version}${it.groupValues[2]}"
        }
        readmeFile.writeText(updatedContent)
        println("Readme.md badge updated with version ${project.version}")
    }
}

// Group task that runs all update scripts
tasks.register("updateVersionNumberUsages") {
    dependsOn("updateVersionInDockerfile", "updateReadmeVersionBadge")
}
