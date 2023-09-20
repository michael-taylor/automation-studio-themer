import automationstudio.EditorSettings
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.eagerOption
import themes.DraculaTheme

val themes = listOf(
    DraculaTheme(),
)

class Themer : CliktCommand() {
    init {
        eagerOption("--list") {
            // List themes here
            for (t in themes) {
                println("- ${t.name}")
            }
            throw ProgramResult(0)
        }
    }

    val theme by argument(help = "Name of theme")

    override fun run() {
        val settings = EditorSettings()
        val t = themes.find { it.name.equals(theme, ignoreCase = true) }

        if (t == null)
        {
            println("ERROR: Theme $theme not found")
            throw ProgramResult(-1)
        }

        settings.applyTheme(t)
        println(settings.toXml())
    }
}

fun main(args: Array<String>) = Themer().main(args)