package automationstudio

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import themes.Theme

enum class ItemType {
    DataType,
    Number,
    InvalidKeyword,
    Keyword,
    Name,
    Remark,
    String,
    Text,
    TextSelection,
    Operator,
    Linenumber,
    PrintPageBoundaries,
    FormFeed,
    LineModificatorChange,
    LineModificatorSave,
    BracesHighlight,
    HypertextUrlHighlight,
    CodeSnippets,
    ColumnIndentation,
    IncludeFiles,
    Equal,
    Similar,
    InLeftPaneOnly,
    InRightPaneOnly,
    Different,
    DifferentContent,
    DifferentDetails,
    DifferentBlockItem,
    IncomparableBlockItem,
    NoHwInfo,
    Errors,
    Warnings,
    GroupBackgroundColor,
    LadderPowerFlow,
    Value,
    ForceValue,
    ArchiveValue,
    RootNodeColor,
    InactiveValue,
    SFCTransition,
}

@Serializable
data class Color(
    @XmlSerialName("Red")
    val r: Int,
    @XmlSerialName("Green")
    val g: Int,
    @XmlSerialName("Blue")
    val b: Int
) {
    constructor(hex: String) : this(
        r = hex.substring(1, 3).toInt(16),
        g = hex.substring(3, 5).toInt(16),
        b = hex.substring(5, 7).toInt(16)
    )
}

@Serializable
data class Font(
    @XmlSerialName("Name")
    val name: String = "Microsoft Sans Serif",
    @XmlSerialName("Size")
    val size: Int = 8,
    @XmlSerialName("Style")
    val style: String = "Regular"
)

@Serializable
data class Item(
    @XmlSerialName("Name")
    @XmlElement(false)
    val name: ItemType,
    @XmlSerialName("ForeColor")
    var foreColor: Color? = null
)

@Serializable
data class Items(val items: List<Item>)

@Serializable
data class Category(
    @XmlSerialName("Name")
    val name: String,
    @XmlSerialName("Font")
    var font: Font?,
    @XmlSerialName("BackColor")
    var backColor: Color? = null,
    @XmlSerialName("MonitorBackColor")
    var monitorBackColor: Color? = null,
    @XmlSerialName("SelectionBackColor")
    var selectionBackColor: Color? = null,
    val items: Items
)

@Serializable
data class DefaultEditor(@XmlSerialName("Extension") val extension: String, @XmlSerialName("Format") val format: String)

@Serializable
data class DefaultEditors(val defaultEditors: List<DefaultEditor>)

@Serializable
data class Categories(val categories: List<Category>)

@Serializable
data class EditorSettings(
    @XmlSerialName("TabWidth")
    val tabWidth: Int = 4,
    @XmlSerialName("SyntaxColoring")
    val syntaxColoring: Boolean = true,
    @XmlSerialName("Intellisense")
    val intellisense: Boolean = true,
    @XmlSerialName("AutoDeclare")
    val autoDeclare: Boolean = false,
    @XmlSerialName("ShowWhiteSpace")
    val showWhiteSpace: Boolean = false,
    @XmlSerialName("ReplaceTabs")
    val replaceTabs: Boolean = false,
    @XmlSerialName("IndentMode")
    val indentMode: String = "Smart",
    @XmlSerialName("VirtualWhiteSpace")
    val virtualWhiteSpace: Boolean = false,
    @XmlSerialName("ShowLineNumbers")
    val showLineNumbers: Boolean = false,
    @XmlSerialName("AutoFormat")
    val autoFormat: Boolean = true,
    @XmlSerialName("PromptBeforeDrag")
    val promptBeforeDrag: Boolean = false,
    @XmlSerialName("FilteredDescription")
    val filteredDescription: Boolean = false,
    @XmlSerialName("WordWrapping")
    val wordWrapping: Boolean = false,
    @XmlSerialName("Outlining")
    val outlining: Boolean = true,
    @XmlSerialName("ShowLineModifications")
    val showLineModifications: Boolean = true,
    @XmlSerialName("ShowHypertext")
    val showHypertext: Boolean = true,
    val xmlns: String = "http://br-automation.co.at/AS/EditorSettings",
    val categories: Categories = Categories(categories = allCategories),
    val defaultEditors: DefaultEditors = DefaultEditors(defaultEditors = defaultEditorDefaults)
) {
    fun applyTheme(theme: Theme) {
        for (c in categories.categories) {
            for (i in c.items.items) {
                i.foreColor = theme.colorFor(i.name)
            }
            c.backColor = theme.backgroundColor()
            c.selectionBackColor = theme.selectionBackgroundColor()
            c.monitorBackColor = theme.monitorBackgroundColor()
        }
    }

    fun setTextEditorFont(fontName: String) {
        val category = categories.categories.find { it.name == "TextEditor" }
        if (category != null) {
            category.font = Font(fontName)
        }
    }

    fun toXml(): String {
        val xml = XML() {
            indentString = "  "
        }
        // Hack to prepend processing instructions
        val sb = StringBuilder()
        sb.appendLine("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
        sb.appendLine("<?AutomationStudio Version=4.7.4.67 SP?>")
        sb.append(xml.encodeToString(this))
        return sb.toString()
    }
}
