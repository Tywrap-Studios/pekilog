package net.tywrapstudios.pekilog.config.data;

import static net.tywrapstudios.pekilog.Pekilog.MOD_VER;

public class ConfigData {
    /**
     * This is where the order of the entries and the entries themselves are defined.
     */
    public String MOD_VERSION_DO_NOT_TOUCH = MOD_VER;
    public String c1 = "Whether any logging is enabled.";
    public boolean enabled = true;
    public String c2 = "Whether the mod should log an info message if a feature is disabled";
    public boolean outputDisabledLoggerInfo = true;
    public String c3 = "Enable or disabled certain loggers";
    public boolean logPrivateMessages = true;
    public boolean logTellraw = true;

    public void update() {
    }
}
