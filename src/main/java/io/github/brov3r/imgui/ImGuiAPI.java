package io.github.brov3r.imgui;

import imgui.ImFont;
import imgui.ImFontConfig;
import imgui.ImGuiIO;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Interface for managing ImGui fonts and widgets.
 * Provides methods to create, retrieve, and manage fonts and widgets used in ImGui.
 */
public interface ImGuiAPI {
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
}