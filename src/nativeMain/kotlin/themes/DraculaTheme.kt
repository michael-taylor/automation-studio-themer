package themes

import automationstudio.Color
import automationstudio.ItemType

class DraculaTheme : Theme {
    override val name: String
        get() = "Dracula"

    private val bg = Color("#282A36")
    private val fg = Color("#F8F8F2")
    private val selection = Color("#44475A")
    private val comment = Color("#6272A4")
    private val cyan = Color("#8BE9FD")
    private val green = Color("#50FA7B")
    private val orange = Color("#FFB86C")
    private val pink = Color("#FF79C6")
    private val purple = Color("#BD93F9")
    private val red = Color("#FF5555")
    private val yellow = Color("#F1FA8C")

    override fun colorFor(item: ItemType): Color {
        return when (item) {
            ItemType.DataType -> cyan
            ItemType.Number -> purple
            ItemType.InvalidKeyword -> red
            ItemType.Keyword -> pink
            ItemType.Name -> green
            ItemType.Remark -> comment
            ItemType.String -> yellow
            ItemType.Text -> fg
            ItemType.TextSelection -> orange
            ItemType.Operator -> pink
            ItemType.Linenumber -> comment
            ItemType.LineModificatorChange -> cyan
            ItemType.LineModificatorSave -> green
            ItemType.BracesHighlight -> pink
            ItemType.IncludeFiles -> cyan
            ItemType.Different -> orange
            ItemType.Errors -> red
            ItemType.Warnings -> orange
            else -> fg
        }
    }

    override fun backgroundColor(): Color {
        return bg
    }

    override fun monitorBackgroundColor(): Color {
        return bg.tintRed(20)
    }

    override fun selectionBackgroundColor(): Color {
        return selection
    }
}