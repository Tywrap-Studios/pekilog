package net.tywrapstudios.pekilog.config.data;

import static net.tywrapstudios.pekilog.Pekilog.MOD_VER;

public class ConfigData {
    /**
     * This is where the order of the entries is defined.
     */
    public String MOD_VERSION_DO_NOT_TOUCH = MOD_VER;
    public boolean logPrivateMessages = true;
    public boolean logTellraw = true;

    public void update() {
    }
}
