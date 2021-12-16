import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.get
import java.io.File
import java.io.FileInputStream
import java.util.*

val keystoreProperties = Properties().apply {
    load(FileInputStream(File("../../.local/KStore/keystore.properties")))
}

open class BaseAndroidPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            configureAndroidModule()
        }
    }

    private fun Project.configureAndroidModule() = android {

        setCompileSdkVersion(Config.compile_sdk)

        signingConfigs {
            create("release") {
                storeFile = file("../../../.local/KStore/pan_alexander.jks")
                storePassword = keystoreProperties.getProperty("storePassword")
                keyAlias = keystoreProperties.getProperty("keyBAlias")
                keyPassword = keystoreProperties.getProperty("keyBPassword")
            }
        }

        defaultConfig {
            minSdk = Config.min_sdk
            setTargetSdkVersion(Config.target_sdk)
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        buildTypes {
            getByName("release") {
                isMinifyEnabled = true
                isShrinkResources = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
                signingConfig = signingConfigs.getByName("release")
            }

            getByName("debug") {
                isMinifyEnabled = false
                isShrinkResources = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android.txt"),
                    "proguard-rules.pro"
                )
                signingConfig = signingConfigs.getByName("release")
            }
        }

        viewBinding.isEnabled = true

        compileOptions {
            sourceCompatibility = Config.java_version
            targetCompatibility = Config.java_version
        }
    }

    fun Project.android(action: BaseExtension.() -> Unit) =
        (extensions["android"] as BaseExtension).run(action)
}
