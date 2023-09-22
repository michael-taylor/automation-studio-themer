package themes

import automationstudio.Color
import automationstudio.ItemType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okio.FileSystem
import okio.Path.Companion.toPath

@Serializable
data class ColorTheme(val colors: Map<String, String>)

class MissingColorException(colorItem: String) : Throwable("Required color for $colorItem is missing")

fun parseThemeString(jsonString: String): Theme {
    val themeData = Json.decodeFromString<ColorTheme>(jsonString)

    // Required colors: foreground, background, and selectionBackground
    val defaultForegroundColor = themeData.colors["foreground"]
    val background = themeData.colors["background"]
    val selectionBackground = themeData.colors["selectionBackground"]

    if (defaultForegroundColor == null) throw MissingColorException("foreground")
    if (background == null) throw MissingColorException("background")
    if (selectionBackground == null) throw MissingColorException("selectionBackground")

    val colorMap = mutableMapOf<ItemType, Color>()

    // Recommended colors
    val foreground = Color(defaultForegroundColor)
    val monitorBackground = Color(themeData.colors["monitorBackground"] ?: background)
    val recommendedItems = setOf(
        ItemType.DataType,
        ItemType.Number,
        ItemType.Keyword,
        ItemType.TextSelection,
        ItemType.Errors,
        ItemType.Warnings,
        ItemType.Remark,
        ItemType.String,
        ItemType.Name,
        ItemType.Equal
    )
    for (i in recommendedItems) {
        val color = themeData.colors.entries.find { it.key.equals(i.name, ignoreCase = true) }

        if (color == null) {
            println("WARNING: Color for $i not found")
            colorMap[i] = Color(defaultForegroundColor)
        } else {
            colorMap[i] = Color(color.value)
        }
    }

    // Fill in rest of colors
    for (i in ItemType.entries) {
        if (!colorMap.containsKey(i)) {
            val colorStr = themeData.colors.entries.find { it.key.equals(i.name, ignoreCase = true) }
            colorMap[i] = if (colorStr != null) Color(colorStr.value) else when (i) {
                ItemType.InvalidKeyword,
                ItemType.Different,
                ItemType.DifferentContent,
                ItemType.DifferentDetails,
                ItemType.DifferentBlockItem,
                ItemType.IncomparableBlockItem,
                ItemType.ForceValue,
                ItemType.NoHwInfo -> colorMap[ItemType.Errors] ?: foreground
                ItemType.Similar,
                ItemType.InLeftPaneOnly,
                ItemType.InRightPaneOnly,
                ItemType.LineModificatorChange -> colorMap[ItemType.Warnings] ?: foreground
                ItemType.LineModificatorSave -> colorMap[ItemType.Equal] ?: foreground
                ItemType.Linenumber -> colorMap[ItemType.Remark] ?: foreground
                ItemType.Operator,
                ItemType.IncludeFiles -> colorMap[ItemType.Keyword] ?: foreground
                ItemType.BracesHighlight -> foreground
                ItemType.Value -> colorMap[ItemType.Number] ?: foreground
                else -> foreground
            }
        }
    }

    return ThemeImpl(
        themeName = "Parsed Theme",
        defaultForeground = foreground,
        background = Color(background),
        selectionBackground = Color(selectionBackground),
        monitorBackground = monitorBackground,
        colorMap = colorMap
    )
}

fun parseThemeFile(filename: String): Theme {
    val jsonString = FileSystem.SYSTEM.read(filename.toPath()) {
        readUtf8()
    }

    return parseThemeString(jsonString)
}
