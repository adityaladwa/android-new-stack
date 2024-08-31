pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "android-new-stack"
include(":app")
include(":data")
project(":data").projectDir = File(rootDir, "core/data/")

include(":discovery-impl")
project(":discovery-impl").projectDir = File(rootDir, "features/discovery/discovery-impl/")

include(":discovery-api")
project(":discovery-api").projectDir = File(rootDir, "features/discovery/discovery-api/")

include(":ui")
project(":ui").projectDir = File(rootDir, "core/ui/")
