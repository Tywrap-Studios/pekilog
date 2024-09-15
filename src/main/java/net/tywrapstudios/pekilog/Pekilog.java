package net.tywrapstudios.pekilog;

import net.fabricmc.api.ModInitializer;

import net.tywrapstudios.pekilog.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Pekilog implements ModInitializer {
	public static final String MOD_ID = "pekilog";
	public static final String MOD_VER = "1.0.0";
	public static final String CONFIG_V = "1";
	public static final String MOD_NAME = "Pekilog";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
	public static final Logger LOGGER_COMMANDS = LoggerFactory.getLogger(MOD_NAME+" [Commands]");

	@Override
	public void onInitialize() {
		LOGGER.info("[Mod Log] Initializing Pekilog.");
		LOGGER.info("[Config] Loading Config.");
		ConfigManager.loadConfig();
		if (!Objects.equals(ConfigManager.getConfig().CONFIG_DO_NOT_TOUCH, CONFIG_V)) {
			LOGGER.warn("[Config] Your Config somehow got out of sync with the actual mod version. This can be dangerous. Try to re-run the instance or delete the log file.");
		}
	}
}