import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.parameters.options.eagerOption

class Themer : CliktCommand() {
    init {
        eagerOption("--list") {
            // List themes here
            println("Listing...")
            throw ProgramResult(0)
        }
    }

    override fun run() {
        println("Running")
    }
}

fun main(args: Array<String>) = Themer().main(args)