enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Muviz"
include(":app-mobile")
include(":app-tv")
include(":core")
include(":feature")
include(":feature:home")
include(":feature:genre")
include(":feature:account")
include(":feature:search")
include(":feature:favorites")
include(":feature:cast")
include(":feature:film")
include(":core:network")
include(":core:common")
include(":core:ui")
include(":core:model")
