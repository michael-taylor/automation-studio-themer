package themes

import automationstudio.Color
import automationstudio.ItemType

class TokyoNightTheme : Theme {
    override val name: String
        get() = "TokyoNight"

    private val defaultForeground = Color("#c0caf5")
    private val background = Color("#24283b")
    private val selection = Color("#292e42")

    private val colorMap = generateThemeFromSeed(
        foreGround = Color("#c0caf5"),
        selectionForeGround = Color("#9D599D"),
        success = Color("#0db9d7"),
        warning = Color("#e0af68"),
        error = Color("#db4b4b"),
        comment = Color("#565f89"),
        string = Color("#9ece6a"),
        number = Color("#ff9e64"),
        keyword = Color("#89ddff"),
        dataType = Color("#bb9af7"),
        name = Color("#c0caf5")
    )

    override fun colorFor(item: ItemType): Color {
        return colorMap[item] ?: defaultForeground;
    }

    override fun backgroundColor(): Color {
        return background
    }

    override fun monitorBackgroundColor(): Color {
        return background.tintRed(20)
    }

    override fun selectionBackgroundColor(): Color {
        return selection
    }
}