## Theme generator for B&R's Automation Studio

This utility can generate theme (**Editor.set**) files for Automation Studio. The current themes include:

* Dracula
* Gruvbox Dark
* Gruvbox Light
* Tokyo Night

### Usage

The available themes can be listed by using the `--list` parameter.

To set the theme, pass it in with the `--theme {name}` or `-t {name}` parameter.

The font used in the text editor can be set with the `--font {fontname}` or `-f {fontname}` parameter.

Normally, the **Editor.set** file will be printed to standard output, but it can be saved to a file with the `--output {filename}` or `-o {filename}` parameter.

### Config file location

The theme config (editor settings) file, **Editor.set** is stored in **$USERPROFILE\AppData\Roaming\BR\\{AutomationStudioVersion}**. It should not be edited or replaced while Automation Studio is running.
