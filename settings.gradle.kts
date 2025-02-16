// settings.gradle.kts
// -------------------
// This file is used during the initialization phase of
// a Gradle build. Its primary purpose is to define the
// overall project structure – for example, declaring the
// root project and including subprojects (or modules) in
// a multi-project build. It's where you set the name of the
// root project and decide which projects should be part of the build.

// It is not intended to be profile-specific. Its content 
// typically remains static across different environments 
// because it defines the build's structure rather than runtime behavior.

/* Gradle uses the root project name often. rootProject.name is by default
 * the folder name if it is not set specifically.
 
 Setting rootProject.name offers several benefits:
 * - Ensures artifacts use your intended name.
 * - Prevents conflicts in multi-module builds.
 * - Provides a consistent identifier for tools, CI/CD, and plugins.
 * - Improves documentation clarity.
 */
rootProject.name = "todo-list-api"
