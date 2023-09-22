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

class ThemeImpl(
    private val themeName: String,
    private val defaultForeground: Color,
    private val background: Color,
    private val selectionBackground: Color,
    private val monitorBackground: Color,
    private val colorMap: Map<ItemType, Color>
) : Theme {
    override val name: String
        get() = themeName

    override fun colorFor(item: ItemType): Color {
        return colorMap[item] ?: defaultForeground
    }

    override fun backgroundColor(): Color {
        return background
    }

    override fun monitorBackgroundColor(): Color {
        return monitorBackground
    }

    override fun selectionBackgroundColor(): Color {
        return selectionBackground
    }
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
