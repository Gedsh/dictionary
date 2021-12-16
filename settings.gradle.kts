enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")

pluginManagement {

    repositories {
        google()
        mavenCentral()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
    }
}

rootProject.name = "Dictionary"
