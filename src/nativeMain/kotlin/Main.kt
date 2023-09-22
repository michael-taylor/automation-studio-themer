import automationstudio.EditorSettings
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.parameters.options.option
import okio.FileSystem
import okio.Path.Companion.toPath
import themes.*

class Themer : CliktCommand() {
    init {
        context {
            helpOptionNames = setOf("-h", "--help")
        }
    }

    private val theme by option("-t", "--theme", help = "Name of theme file")
    private val font by option("-f", "--font", help = "Name of text editor font")
    private val outputFile by option("-o", "--output", help = "Name of config file to save output to")

    override fun run() {
        val settings = EditorSettings()

        if (theme != null) {
            val themeFile = theme!!.toPath()

            if (FileSystem.SYSTEM.exists(themeFile)) {
                try {
                    val theme = parseThemeFile(theme!!)
                    settings.applyTheme(theme)
                } catch (e: MissingColorException) {
                    println(e.message)
                    throw ProgramResult(-1)
                }
            } else {
                println("ERROR: Theme file $theme not found")
                throw ProgramResult(-1)
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