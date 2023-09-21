package themes

import automationstudio.Color
import automationstudio.ItemType

class GruvboxLightTheme : Theme {
    override val name: String
        get() = "GruvboxLight"

    private val defaultForeground = Color("#3c3836")
    private val background = Color("#fbf1c7")
    private val selection = Color("#ebdbb2")

    private val colorMap = generateThemeFromSeed(
        foreGround = defaultForeground,
        selectionForeGround = Color("#b57614"),
        success = Color("#79740e"),
        warning = Color("#af3a03"),
        error = Color("#9d0006"),
        comment = Color("#928374"),
        string = Color("#79740e"),
        number = Color("#8f3f71"),
        keyword = Color("#9d0006"),
        dataType = Color("#427b58"),
        name = Color("#076678")
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

class GruvboxDarkTheme : Theme {
    override val name: String
        get() = "GruvboxDark"

    private val defaultForeground = Color("#ebdbb2")
    private val background = Color("#282828")
    private val selection = Color("#3c3836")

    private val colorMap = generateThemeFromSeed(
        foreGround = defaultForeground,
        selectionForeGround = Color("#fabd2f"),
        success = Color("#b8bb26"),
        warning = Color("#fabd2f"),
        error = Color("#fb4934"),
        comment = Color("#928374"),
        string = Color("#b8bb26"),
        number = Color("#d3869b"),
        keyword = Color("#fb4934"),
        dataType = Color("#8ec07c"),
        name = Color("#fabd2f")
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