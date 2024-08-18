package io.github.brov3r.imgui;

import com.avrix.Launcher;
import imgui.ImFont;
import imgui.ImFontConfig;
import imgui.ImFontGlyphRangesBuilder;
import imgui.ImGuiIO;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * A utility class for managing ImGui fonts, including loading and creating fonts from various sources.
 */
public class ImGuiFont {

    /**
     * Loads the default fonts and additional custom fonts, including Cyrillic, Japanese, and FontAwesome icons.
     * Fonts are loaded from the JAR file that contains the application.
     */
    public static void loadDefaultFonts() {
        ImGuiIO io = ImGuiManager.getIo();

        // Load default ImGui font
        io.getFonts().addFontDefault();

        // Configure font glyph ranges
        final ImFontGlyphRangesBuilder rangesBuilder = new ImFontGlyphRangesBuilder();
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesDefault());
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesCyrillic());
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesJapanese());
        rangesBuilder.addRanges(FontAwesomeIcons._IconRange);

        final ImFontConfig fontConfig = new ImFontConfig();
        fontConfig.setMergeMode(true);

        final short[] glyphRanges = rangesBuilder.buildRanges();

        // Load custom fonts from the JAR file
        try {
            File coreJarFile = new File(Launcher.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            createFont("Montserrat-Regular-14", coreJarFile.getPath(), "media/fonts/Montserrat-Regular.ttf", 14, fontConfig, glyphRanges);
            createFont("Arial-Regular-14", coreJarFile.getPath(), "media/fonts/Arial-Regular.ttf", 14, fontConfig, glyphRanges);
            createFont("Roboto-Regular-14", coreJarFile.getPath(), "media/fonts/Roboto-Regular.ttf", 14, fontConfig, glyphRanges);
            createFont("FontAwesome-14", coreJarFile.getPath(), "media/fonts/FontAwesome.ttf", 14, fontConfig, glyphRanges);
        } catch (URISyntaxException e) {
            System.out.println("[!] Failed to load custom fonts: Invalid URI - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[!] Failed to load custom fonts: " + e.getMessage());
        }

        fontConfig.destroy();
    }

    /**
     * Creates an ImFont from a font file located inside a JAR file.
     *
     * @param fontName         The name to assign to the font.
     * @param jarFilePath      The path to the JAR file containing the font.
     * @param internalFilePath The path to the font file inside the JAR.
     * @param fontSize         The size of the font.
     * @param fontConfig       Configuration options for the font (e.g., merge mode).
     * @param glyphRanges      Glyph ranges to include in the font.
     * @return The created ImFont object, or null if creation failed.
     */
    public static ImFont createFont(String fontName, String jarFilePath, String internalFilePath, int fontSize, ImFontConfig fontConfig, short[] glyphRanges) {
        return createFontInternal(fontName, () -> loadFontBytes(jarFilePath, internalFilePath), fontSize, fontConfig, glyphRanges);
    }

    /**
     * Creates an ImFont from a font file located inside a JAR file, using default font configuration.
     *
     * @param fontName         The name to assign to the font.
     * @param jarFilePath      The path to the JAR file containing the font.
     * @param internalFilePath The path to the font file inside the JAR.
     * @param fontSize         The size of the font.
     * @return The created ImFont object, or null if creation failed.
     */
    public static ImFont createFont(String fontName, String jarFilePath, String internalFilePath, int fontSize) {
        return createFontInternal(fontName, () -> loadFontBytes(jarFilePath, internalFilePath), fontSize, null, null);
    }

    /**
     * Creates an ImFont from a font file located on the filesystem.
     *
     * @param fontName The name to assign to the font.
     * @param fontPath The path to the font file.
     * @param fontSize The size of the font.
     * @return The created ImFont object, or null if creation failed.
     */
    public static ImFont createFont(String fontName, Path fontPath, int fontSize) {
        return createFontInternal(fontName, () -> loadFontBytes(fontPath), fontSize, null, null);
    }

    /**
     * Internal method to create an ImFont using a FontDataLoader to load the font data.
     *
     * @param fontName    The name to assign to the font.
     * @param loader      A FontDataLoader that provides the font data as a byte array.
     * @param fontSize    The size of the font.
     * @param fontConfig  Configuration options for the font (e.g., merge mode), or null for default configuration.
     * @param glyphRanges Glyph ranges to include in the font, or null for default ranges.
     * @return The created ImFont object, or null if creation failed.
     */
    private static ImFont createFontInternal(String fontName, FontDataLoader loader, int fontSize, ImFontConfig fontConfig, short[] glyphRanges) {
        ImFont font = null;
        try {
            byte[] fontData = loader.load();
            if (fontData != null) {
                if (fontConfig != null && glyphRanges != null) {
                    font = ImGuiManager.getIo().getFonts().addFontFromMemoryTTF(fontData, fontSize, fontConfig, glyphRanges);
                } else {
                    font = ImGuiManager.getIo().getFonts().addFontFromMemoryTTF(fontData, fontSize);
                }
                ImGuiManager.addFont(fontName, font);
            }
        } catch (Exception e) {
            System.out.printf("[!] Failed to create font '%s': %s%n", fontName, e.getMessage());
        }
        return font;
    }

    /**
     * Loads font data from a font file inside a JAR file.
     *
     * @param jarFilePath      The path to the JAR file containing the font.
     * @param internalFilePath The path to the font file inside the JAR.
     * @return A byte array containing the font data, or null if loading failed.
     */
    public static byte[] loadFontBytes(String jarFilePath, String internalFilePath) {
        byte[] font = null;
        try {
            URL jarUrl = new URL("jar:file:" + jarFilePath + "!/" + internalFilePath);
            try (InputStream inputStream = jarUrl.openStream()) {
                Path tempFontFile = Files.createTempFile("imgui_temp-font", ".ttf");
                Files.copy(inputStream, tempFontFile, StandardCopyOption.REPLACE_EXISTING);
                font = loadFontBytes(tempFontFile);
                Files.delete(tempFontFile);
            }
        } catch (IOException e) {
            System.out.printf("[!] File '%s' not found inside JAR file '%s'!%n", internalFilePath, jarFilePath);
        }
        return font;
    }

    /**
     * Loads font data from a font file on the filesystem.
     *
     * @param fontPath The path to the font file.
     * @return A byte array containing the font data, or null if loading failed.
     */
    public static byte[] loadFontBytes(Path fontPath) {
        try {
            return Files.readAllBytes(fontPath);
        } catch (IOException e) {
            System.out.printf("[!] Failed to read font file '%s': %s%n", fontPath, e.getMessage());
            return null;
        }
    }

    /**
     * A functional interface for loading font data as a byte array.
     */
    @FunctionalInterface
    private interface FontDataLoader {
        /**
         * Loads font data.
         *
         * @return A byte array containing the font data.
         * @throws Exception If an error occurs during font data loading.
         */
        byte[] load() throws Exception;
    }
}