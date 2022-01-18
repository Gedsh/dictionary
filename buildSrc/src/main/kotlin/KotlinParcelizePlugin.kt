import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinParcelizePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.apply("kotlin-parcelize")
    }
}
