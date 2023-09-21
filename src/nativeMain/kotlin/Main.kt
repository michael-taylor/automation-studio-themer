import automationstudio.EditorSettings
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.parameters.options.eagerOption
import com.github.ajalt.clikt.parameters.options.option
import okio.FileSystem
import okio.Path.Companion.toPath
import themes.DraculaTheme
import themes.GruvboxDarkTheme
import themes.GruvboxLightTheme
import themes.TokyoNightTheme

val themes = listOf(
    DraculaTheme(),
    GruvboxDarkTheme(),
    GruvboxLightTheme(),
    TokyoNightTheme(),
)

class Themer : CliktCommand() {
    init {
        eagerOption("--list", help = "List the available themes") {
            // List themes here
            for (t in themes) {
                println("- ${t.name}")
            }
            throw ProgramResult(0)
        }

        context {
            helpOptionNames = setOf("-h", "--help")
        }
    }

    private val theme by option("-t", "--theme", help = "Name of theme")
    private val font by option("-f", "--font", help = "Name of text editor font")
    private val outputFile by option("-o", "--output", help = "Name of config file to save output to")

    override fun run() {
        val settings = EditorSettings()

        if (theme != null) {
            val t = themes.find { it.name.equals(theme, ignoreCase = true) }

            if (t == null) {
                println("ERROR: Theme $theme not found")
                throw ProgramResult(-1)
            } else {
                settings.applyTheme(t)
            }
        }

        if (font != null) {
            settings.setTextEditorFont(font!!)
        }

        if (outputFile != null) {
            val path = outputFile!!.toPath()
            FileSystem.SYSTEM.write(path) {
                writeUtf8(settings.toXml())
            }
        } else {
            println(settings.toXml())
        }
    }
}

fun main(args: Array<String>) = Themer().main(args)