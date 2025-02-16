plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	// A Gradle plugin that provides Maven-like dependency management functionality
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"

// version is implemented as a gradle property.
version = project.property("version") as String

// projectSlug is a simple var. It's not a property as version is.
val projectSlug = rootProject.name

// projectSlug is implemented as an gradle extension property.
// It can afterwards be accessed as a property in the project like this:
// project.extensions.extraProperties["project.slug"]
// but not like this: project.slug
project.extensions.extraProperties["project.slug"] = projectSlug

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

// This task expands the project properties in the application.properties file.
// It is used to replace placeholders in the application.properties file with
// the actual values from the project.
tasks.processResources {
    filesMatching("**/application.properties") {
        expand(
            mapOf(
                "project" to mapOf(
                    "version" to project.version,
                    "name" to project.property("project.name"),
                    "description" to project.property("project.description")
                ),
                "slug" to project.extensions.extraProperties["project.slug"],
                "url" to project.property("project.url")
            )
        )
    }
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

tasks.register<Copy>("generateReadmeTemp") {
    // Copy the template into a temporary folder.
    from("README.template.md")
    into("$buildDir/generated-readme")
    rename { "README.md" }
    // Do a simple line-by-line replacement.
    filter { line ->
        line.replace("\${project.version}", project.version.toString())
        .replace("\${project.slug}", projectSlug)
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
            .replace("\${project.slug}", projectSlug as String)
    }
    outputs.upToDateWhen { false }

    doLast {
        // Copy the generated Dockerfile from the temporary folder to the project root.
        copy {
            from("$buildDir/generated-dockerfile/Dockerfile")
            into(".")
        }
        println("Dockerfile generated in the project root with version ${project.version} and project slug $projectSlug")
    }
}

// Group task that runs all update scripts
tasks.register("generateReadmeAndDockerfile") {
    dependsOn("generateReadme", "generateDockerfile")
}

