<div align="center">
    <h1>ImGuiAPI</h1>
</div>

<p align="center">
    <img alt="PZ Version" src="https://img.shields.io/badge/Project_Zomboid-41.78.16-blue">
    <img alt="Java version" src="https://img.shields.io/badge/Java-17-orange">
    <img alt="Avrix" src="https://img.shields.io/badge/AvrixLoader->=1.5.0-red">
</p>

**ImGuiAPI** - library that allows you to implement an interface based on ImGui.

# How to use

1) Install [AvrixLoader](https://github.com/Brov3r/Avrix)
2) Download the plugin from the releases page
2) Move to the `plugins` folder
3) Start the server and shut down
4) Configure the plugin in the plugin settings folder `plugins/imgui-api`

# For developers

## How to use

1) Download and include the library as `compileOnly`
2) In the right place in the plugin (preferably in the main class) get the API object:

```java
ImGuiAPI imGuiAPI = ServiceManager.getService(ImGuiAPI.class);
```

## Widgets

To render ImGui widgets, you need to create a class that inherits from `ImGuiWidget`:

```java
/**
 * Example widget
 */
public class ExampleWidget extends ImGuiWidget {
    /**
     * Renders the widget on the screen.
     * This is an abstract method that must be implemented by subclasses to define
     * the specific rendering logic of the widget.
     */
    @Override
    void render() {
        ImGui.setNextWindowSize(650, 400);

        ImGui.begin("Example window");

        captureMouseFocus(); // It is necessary after `.begin` to block clicks on standard game UI elements

        ImGui.end();
    }
}
```

Then in the `Main` class of your plugin you can create an object and add it to the screen:

```java
/**
 * Main entry point of the example plugin
 */
public class Main extends Plugin {
    // Code...

    /**
     * Called when the plugin is initialized.
     * <p>
     * Implementing classes should override this method to provide the initialization logic.
     */
    @Override
    public void onInitialize() {
        ExampleWidget widget = new ExampleWidget();
        widget.addToScreen();
    }
}
```

## Events

- `OnImGuiInitializeEvent` -> Triggered when ImGui is initialized. You can load custom fonts.

## API

```java
/**
 * Retrieves a font by its name.
 *
 * @param fontName the name of the font.
 * @return the {@link ImFont} associated with the specified name, or null if not found.
 */
ImFont getFont(String fontName);

/**
 * Retrieves a map of all fonts with their associated names.
 *
 * @return a {@link Map} where the key is the font name and the value is the {@link ImFont}.
 */
Map<String, ImFont> getFonts();

/**
 * Retrieves the list of widgets managed by ImGui.
 *
 * @return a {@link List} of {@link ImGuiWidget} instances.
 */
List<ImGuiWidget> getWidgets();

/**
 * Adds a new font to the manager.
 *
 * @param fontName the name to associate with the font.
 * @param font     the {@link ImFont} to add.
 */
void addFont(String fontName, ImFont font);

/**
 * Adds a new widget to the manager.
 *
 * @param widget the {@link ImGuiWidget} to add.
 */
void addWidget(ImGuiWidget widget);

/**
 * Removes a widget from the manager.
 *
 * @param widget the {@link ImGuiWidget} to remove.
 */
void removeWidget(ImGuiWidget widget);

/**
 * Retrieves the ImGuiIO object.
 *
 * @return the {@link ImGuiIO} object that manages input/output and configuration settings.
 */
ImGuiIO getIo();

/**
 * Creates and adds a font to the manager from a font file located within a JAR.
 * This method adds the created {@link ImFont} to the font manager with the specified name.
 *
 * @param fontName         the name to associate with the font.
 * @param jarFilePath      the path to the JAR file.
 * @param internalFilePath the path to the font file within the JAR.
 * @param fontSize         the size of the font.
 * @param fontConfig       the {@link ImFontConfig} to configure the font (can be null).
 * @param glyphRanges      the ranges of glyphs to include (can be null).
 * @return the created {@link ImFont}, or null if the font creation or addition failed.
 */
ImFont createFont(String fontName, String jarFilePath, String internalFilePath, int fontSize, ImFontConfig fontConfig, short[] glyphRanges);

/**
 * Creates and adds a font to the manager from a font file located within a JAR with default configuration.
 * This method adds the created {@link ImFont} to the font manager with the specified name.
 *
 * @param fontName         the name to associate with the font.
 * @param jarFilePath      the path to the JAR file.
 * @param internalFilePath the path to the font file within the JAR.
 * @param fontSize         the size of the font.
 * @return the created {@link ImFont}, or null if the font creation or addition failed.
 */
ImFont createFont(String fontName, String jarFilePath, String internalFilePath, int fontSize);

/**
 * Creates and adds a font to the manager from a font file located at a specified path.
 * This method adds the created {@link ImFont} to the font manager with the specified name.
 *
 * @param fontName the name to associate with the font.
 * @param fontPath the path to the font file.
 * @param fontSize the size of the font.
 * @return the created {@link ImFont}, or null if the font creation or addition failed.
 */
ImFont createFont(String fontName, Path fontPath, int fontSize);

/**
 * Loads font data from a file located within a JAR.
 * This method retrieves the font data as a byte array, which can be used for font creation.
 *
 * @param jarFilePath      the path to the JAR file.
 * @param internalFilePath the path to the font file within the JAR.
 * @return a byte array containing the font data, or null if loading failed.
 */
byte[] loadFontBytes(String jarFilePath, String internalFilePath);

/**
 * Loads font data from a file located at a specified path.
 * This method retrieves the font data as a byte array, which can be used for font creation.
 *
 * @param fontPath the path to the font file.
 * @return a byte array containing the font data, or null if loading failed.
 */
byte[] loadFontBytes(Path fontPath);
```

# Disclaimer

This software is provided "as is", without warranty of any kind, express or implied, including but not limited to the
warranties of merchantability, fitness for a particular purpose, and noninfringement of third party rights. Neither the
author of the software nor its contributors shall be liable for any direct, indirect, incidental, special, exemplary, or
consequential damages (including, but not limited to, procurement of substitute goods or services; loss of use, data, or
profits; or business interruption) however caused and on any theory of liability, whether in contract, strict liability,
or tort (including negligence or otherwise) arising in any way out of the use of this software, even if advised of the
possibility of such damage.

By using this software, you agree to these terms and release the author of the software and its contributors from any
liability associated with the use of this software.

# License

This project is licensed under [MIT License](./LICENSE).