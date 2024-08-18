package io.github.brov3r.imgui;

import com.avrix.plugin.Metadata;
import com.avrix.plugin.Plugin;
import com.avrix.plugin.ServiceManager;
import com.avrix.utils.YamlFile;

/**
 * Main entry point of the example plugin
 */
public class Main extends Plugin {
    private static Main instance;

    /**
     * Constructs a new {@link Plugin} with the specified metadata.
     * Metadata is transferred when the plugin is loaded into the game context.
     *
     * @param metadata The {@link Metadata} associated with this plugin.
     */
    public Main(Metadata metadata) {
        super(metadata);
        instance = this;
    }

    /**
     * Called when the plugin is initialized.
     * <p>
     * Implementing classes should override this method to provide the initialization logic.
     */
    @Override
    public void onInitialize() {
        loadDefaultConfig();

        ServiceManager.register(ImGuiAPI.class, new ImGuiAPIImpl());
    }

    /**
     * Getting the instance of the Main class
     *
     * @return instance of the Main class
     */
    public static Main getInstance() {
        return instance;
    }


    /**
     * Getting the default config
     *
     * @return default config
     */
    public static YamlFile getConfig() {
        return getInstance().getDefaultConfig();
    }
}