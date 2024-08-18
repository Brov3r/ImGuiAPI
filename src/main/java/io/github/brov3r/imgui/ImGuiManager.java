package io.github.brov3r.imgui;

import com.avrix.events.EventManager;
import imgui.ImFont;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjglx.opengl.Display;
import zombie.core.opengl.RenderThread;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Manages the ImGui lifecycle, including initialization, rendering, and widget management.
 * This class acts as a central point for handling ImGui widgets and fonts,
 * as well as interfacing with GLFW and OpenGL for rendering.
 */
public class ImGuiManager {
    /**
     * An instance of ImGui's GLFW implementation for window management and input handling.
     */
    private final static ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();

    /**
     * An instance of ImGui's OpenGL implementation for rendering.
     */
    private final static ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    /**
     * Indicates whether the mouse is currently capturing input.
     * This is used to determine if any widget is capturing the mouse.
     */
    private static boolean mouseCapture = false;

    /**
     * The ImGuiIO object that handles input/output and configuration settings for ImGui.
     * This object is initialized during the ImGui context creation.
     */
    private static ImGuiIO io;

    /**
     * A thread-safe list that holds all registered ImGui widgets.
     * The list is used to manage the widgets' lifecycle and rendering.
     */
    private static final List<ImGuiWidget> imGuiWidgets = new CopyOnWriteArrayList<>();

    /**
     * A thread-safe map that associates font names with ImGui fonts.
     * This allows for efficient font retrieval during rendering.
     */
    private static final Map<String, ImFont> imGuiFonts = new ConcurrentHashMap<>();

    /**
     * Getting a list of all registered ImGui widgets
     *
     * @return list of all loaded ImGui widgets
     */
    public static List<ImGuiWidget> getWidgets() {
        return imGuiWidgets;
    }

    /**
     * Retrieves a specific ImGui font by its name.
     *
     * @param fontName the name of the font to retrieve.
     * @return the {@link ImFont} object associated with the given name, or {@code null} if not found.
     */
    public static ImFont getFont(String fontName) {
        return imGuiFonts.get(fontName);
    }

    /**
     * Retrieves the map of all registered ImGui fonts.
     *
     * @return a {@link Map} containing all registered fonts, where the key is the font name and the value is the {@link ImFont} object.
     */
    public static Map<String, ImFont> getFonts() {
        return imGuiFonts;
    }

    /**
     * Registers a new font in the ImGui system.
     *
     * @param fontName the name of the font to register.
     * @param font     the {@link ImFont} object representing the font.
     */
    public static void addFont(String fontName, ImFont font) {
        imGuiFonts.put(fontName, font);
    }

    /**
     * Adds a widget to the screen.
     * This method registers the widget with the ImGuiManager, ensuring it is rendered each frame.
     *
     * @param widget the {@link ImGuiWidget} to add.
     */
    public static void addWidget(ImGuiWidget widget) {
        if (!imGuiWidgets.contains(widget)) {
            imGuiWidgets.add(widget);
        }
    }

    /**
     * Removes a widget from the screen.
     * This method unregisters the widget, preventing it from being rendered.
     *
     * @param widget the {@link ImGuiWidget} to remove.
     */
    public static void removeWidget(ImGuiWidget widget) {
        imGuiWidgets.remove(widget);
    }

    /**
     * Retrieves the ImGuiIO object.
     *
     * @return the {@link ImGuiIO} object that manages input/output and configuration settings.
     */
    public static ImGuiIO getIo() {
        return io;
    }

    /**
     * Checks if any widget is currently capturing the mouse input.
     *
     * @return {@code true} if a widget is capturing the mouse, {@code false} otherwise.
     */
    public static boolean isMouseCapture() {
        return mouseCapture;
    }

    /**
     * Initializes ImGui and its dependencies, including GLFW and OpenGL.
     * This method sets up the ImGui context, loads default fonts, and invokes the "OnImGuiInitialize" event.
     */
    public static void init() {
        RenderThread.invokeOnRenderContext(() -> {
            ImGui.createContext();

            io = ImGui.getIO();
            io.setIniFilename(Main.getConfig().getBoolean("saveIniFile") ? Main.getInstance().getConfigFolder().toPath().resolve("imgui-config.ini").toString() : null);

            ImGuiFont.loadDefaultFonts();

            imGuiGlfw.init(Display.getWindow(), true);
            imGuiGl3.init("#version 330 core");

            System.out.println("[#] ImGui - successful context initialization!");

            EventManager.invokeEvent("OnImGuiInitialize", io);
        });
    }

    /**
     * Renders all registered widgets to the screen.
     * This method is called every frame and handles the ImGui rendering pipeline,
     * including input handling, widget updates, and drawing.
     */
    public static void render() {
        if (io == null) return;

        imGuiGlfw.newFrame();
        ImGui.newFrame();

        mouseCapture = false;

        for (ImGuiWidget widget : imGuiWidgets) {
            if (!widget.isVisible()) continue;

            widget.update();
            widget.render();

            if (widget.isHover()) {
                mouseCapture = true;
            }
        }

        ImGui.render();

        imGuiGl3.renderDrawData(ImGui.getDrawData());
    }
}