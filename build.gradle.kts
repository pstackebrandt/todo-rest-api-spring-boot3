plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	// A Gradle plugin that provides Maven-like dependency management functionality
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = project.property("version") as String
project.property("project.slug") as String

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

tasks.register<Copy>("generateReadmeTemp") {
    // Copy the template into a temporary folder.
    from("README.template.md")
    into("$buildDir/generated-readme")
    rename { "README.md" }
    // Do a simple line-by-line replacement.
    filter { line ->
        line.replace("\${project.version}", project.version.toString())
        .replace("\${project.slug}", project.property("project.slug") as String)
        .replace("\${project.url}", project.property("project.url") as String)
    }
    outputs.upToDateWhen { false }
}

tasks.register("generateReadme") {
    dependsOn("generateReadmeTemp")
    doLast {
        // Copy the generated README.md from the temporary folder to the project root.
        copy {
            from("$buildDir/generated-readme/README.md")
            into(".")
        }
        val generatedFile = file("README.md")
        println("Generated README.md size: ${'$'}{generatedFile.length()} bytes")
        println("README.md generated from README.template.md with version ${project.version}")
    }
}

tasks.register<Copy>("generateDockerfile") {
    from("Dockerfile.template")
    into("$buildDir/generated-dockerfile")
    rename { "Dockerfile" }
    filter { line ->
        line.replace("\${project.version}", project.version.toString())
            .replace("\${project.slug}", project.property("project.slug") as String)
    }
    outputs.upToDateWhen { false }

    doLast {
        // Copy the generated Dockerfile from the temporary folder to the project root.
        copy {
            from("$buildDir/generated-dockerfile/Dockerfile")
            into(".")
        }
        println("Dockerfile generated in the project root with version ${project.version} and project slug ${project.property("project.slug") as String}")
    }
}

// Group task that runs all update scripts
tasks.register("updateReadmeAndDockerfile") {
    dependsOn("generateReadme", "generateDockerfile")
}

