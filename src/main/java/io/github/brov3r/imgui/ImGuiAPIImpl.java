package io.github.brov3r.imgui;

import imgui.ImFont;
import imgui.ImFontConfig;
import imgui.ImGuiIO;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Implementation of ImGuiAPI
 */
public class ImGuiAPIImpl implements ImGuiAPI {
    /**
     * Retrieves a font by its name.
     *
     * @param fontName the name of the font.
     * @return the {@link ImFont} associated with the specified name, or null if not found.
     */
    @Override
    public ImFont getFont(String fontName) {
        return ImGuiManager.getFont(fontName);
    }

    /**
     * Retrieves a map of all fonts with their associated names.
     *
     * @return a {@link Map} where the key is the font name and the value is the {@link ImFont}.
     */
    @Override
    public Map<String, ImFont> getFonts() {
        return ImGuiManager.getFonts();
    }

    /**
     * Retrieves the list of widgets managed by ImGui.
     *
     * @return a {@link List} of {@link ImGuiWidget} instances.
     */
    @Override
    public List<ImGuiWidget> getWidgets() {
        return ImGuiManager.getWidgets();
    }

    /**
     * Adds a new font to the manager.
     *
     * @param fontName the name to associate with the font.
     * @param font     the {@link ImFont} to add.
     */
    @Override
    public void addFont(String fontName, ImFont font) {
        ImGuiManager.addFont(fontName, font);
    }

    /**
     * Adds a new widget to the manager.
     *
     * @param widget the {@link ImGuiWidget} to add.
     */
    @Override
    public void addWidget(ImGuiWidget widget) {
        ImGuiManager.addWidget(widget);
    }

    /**
     * Removes a widget from the manager.
     *
     * @param widget the {@link ImGuiWidget} to remove.
     */
    @Override
    public void removeWidget(ImGuiWidget widget) {
        ImGuiManager.removeWidget(widget);
    }

    /**
     * Retrieves the ImGuiIO object.
     *
     * @return the {@link ImGuiIO} object that manages input/output and configuration settings.
     */
    @Override
    public ImGuiIO getIo() {
        return ImGuiManager.getIo();
    }

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
    @Override
    public ImFont createFont(String fontName, String jarFilePath, String internalFilePath, int fontSize, ImFontConfig fontConfig, short[] glyphRanges) {
        return ImGuiFont.createFont(fontName, jarFilePath, internalFilePath, fontSize, fontConfig, glyphRanges);
    }

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
    @Override
    public ImFont createFont(String fontName, String jarFilePath, String internalFilePath, int fontSize) {
        return ImGuiFont.createFont(fontName, jarFilePath, internalFilePath, fontSize);
    }

    /**
     * Creates and adds a font to the manager from a font file located at a specified path.
     * This method adds the created {@link ImFont} to the font manager with the specified name.
     *
     * @param fontName the name to associate with the font.
     * @param fontPath the path to the font file.
     * @param fontSize the size of the font.
     * @return the created {@link ImFont}, or null if the font creation or addition failed.
     */
    @Override
    public ImFont createFont(String fontName, Path fontPath, int fontSize) {
        return ImGuiFont.createFont(fontName, fontPath, fontSize);
    }

    /**
     * Loads font data from a file located within a JAR.
     * This method retrieves the font data as a byte array, which can be used for font creation.
     *
     * @param jarFilePath      the path to the JAR file.
     * @param internalFilePath the path to the font file within the JAR.
     * @return a byte array containing the font data, or null if loading failed.
     */
    @Override
    public byte[] loadFontBytes(String jarFilePath, String internalFilePath) {
        return ImGuiFont.loadFontBytes(jarFilePath, internalFilePath);
    }

    /**
     * Loads font data from a file located at a specified path.
     * This method retrieves the font data as a byte array, which can be used for font creation.
     *
     * @param fontPath the path to the font file.
     * @return a byte array containing the font data, or null if loading failed.
     */
    @Override
    public byte[] loadFontBytes(Path fontPath) {
        return ImGuiFont.loadFontBytes(fontPath);
    }
}