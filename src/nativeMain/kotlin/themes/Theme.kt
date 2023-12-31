package themes

import automationstudio.Color
import automationstudio.ItemType
import kotlin.math.max
import kotlin.math.min

interface Theme {
    val name: String
    fun colorFor(item: ItemType): Color
    fun backgroundColor(): Color
    fun monitorBackgroundColor(): Color
    fun selectionBackgroundColor(): Color
}

fun generateThemeFromSeed(
    foreGround: Color,
    selectionForeGround: Color,
    success: Color,
    warning: Color,
    error: Color,
    comment: Color,
    string: Color,
    number: Color,
    keyword: Color,
    dataType: Color,
    name: Color
) : Map<ItemType, Color> {
    val colorMap = mutableMapOf<ItemType, Color>()

    for (t in ItemType.entries) {
        colorMap[t] = when (t) {
            ItemType.DataType -> dataType
            ItemType.Number -> number
            ItemType.InvalidKeyword -> error
            ItemType.Keyword -> keyword
            ItemType.Name -> name
            ItemType.Remark -> comment
            ItemType.String -> string
            ItemType.Text -> foreGround
            ItemType.TextSelection -> selectionForeGround
            ItemType.Operator -> keyword
            ItemType.Linenumber -> comment
            ItemType.LineModificatorChange -> warning
            ItemType.LineModificatorSave -> success
            ItemType.BracesHighlight -> keyword
            ItemType.IncludeFiles -> dataType
            ItemType.Different -> warning
            ItemType.Errors -> error
            ItemType.Warnings -> warning
            else -> foreGround
        }
    }

    return colorMap
}

fun Color.tintRed(adjustment: Int): Color {
    return Color(
        min(this.r + adjustment, 255),
        this.g,
        max(this.b - adjustment, 0)
    )
}

private fun Int.clamp(min: Int, max: Int): Int {
    return min(max(min, this), max)
}

fun Color.blend(other: Color, alphaPercent: Int): Color {
    val alpha = alphaPercent.clamp(0, 100)
    return Color(
        (alpha * this.r + ((1 - alpha) * other.r)).clamp(0, 255),
        (alpha * this.g + ((1 - alpha) * other.g)).clamp(0, 255),
        (alpha * this.b + ((1 - alpha) * other.b)).clamp(0, 255)
    )
}

fun Color.darken(background: Color, amount: Int): Color {
    return this.blend(background, amount)
}

fun Color.lighten(foreground: Color, amount: Int): Color {
    return this.blend(foreground, amount)
}
