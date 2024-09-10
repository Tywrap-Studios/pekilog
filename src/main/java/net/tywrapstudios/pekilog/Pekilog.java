package net.tywrapstudios.pekilog;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pekilog implements ModInitializer {
	public static final String MOD_ID = "pekilog";
	public static final String MOD_NAME = "Pekilog";
	public static final String MOD_NAME_CAPS = "PEKILOG";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	@Override
	public void onInitialize() {
		LOGGER.info("[PEKILOG] Initializing Pekilog.");
	}
}