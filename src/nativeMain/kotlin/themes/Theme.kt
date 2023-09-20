package themes

import automationstudio.Color
import automationstudio.ItemType

interface Theme {
    val name: String
    fun colorFor(item: ItemType): Color
    fun backgroundColor(): Color
    fun monitorBackgroundColor(): Color
    fun selectionBackgroundColor(): Color
}