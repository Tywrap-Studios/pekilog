package net.tywrapstudios.pekilog.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.tywrapstudios.pekilog.Pekilog;
import net.tywrapstudios.pekilog.config.data.Config;
import net.tywrapstudios.pekilog.config.data.ConfigData;

import java.io.*;
import java.nio.file.Paths;

public class ConfigManager {
    /**
     * The manager makes/edits the config file, and reads (loads) from it as well.
     */
    private static final Gson GSON_OBJECT = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private static Config CONFIG;

    public static Config getConfig() {
        return CONFIG;
    }

    public static boolean loadConfig() {
        Config oldConfig = CONFIG;
        boolean success;

        CONFIG = null;
        try {
            success = ensureConfig("Successfully read config.");
        }
        catch(IOException exception) {
            success = false;
            CONFIG = oldConfig;
            Pekilog.LOGGER.error("[Config] Something went wrong while reading config!");
            exception.printStackTrace();
        }
        return success;
    }

    public static boolean reloadConfig() {
        Config oldConfig = CONFIG;
        boolean success;

        CONFIG = null;
        try {
            Pekilog.LOGGER.info("[Config] Reloading config...");
            success = ensureConfig("Successfully Reloaded Config.");
        }
        catch(IOException exception) {
            success = false;
            CONFIG = oldConfig;
            Pekilog.LOGGER.error("[Config] Something went wrong while reloading config!");
            exception.printStackTrace();
        }
        return success;
    }

    public static boolean ensureConfig(String logMessage) throws IOException {
        boolean success;
        File dir = Paths.get("", "config").toFile();

        dir.mkdirs();

        File config = new File(dir, "pekilog.json");

        ConfigData configData = config.exists() ? GSON_OBJECT.fromJson(new InputStreamReader(new FileInputStream(config), "UTF-8"), ConfigData.class) : new ConfigData();
        configData.update();

        CONFIG = new Config(configData);

        {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(config), "UTF-8"));
            writer.write(GSON_OBJECT.toJson(configData));
            writer.close();
        }
        success = true;
        Pekilog.LOGGER.info("[Config] " + logMessage);
        return success;
    }

}
