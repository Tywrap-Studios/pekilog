package net.tywrapstudios.pekilog.config.data;

public class ConfigData {
    /**
     * This is where the order of the entries and the entries themselves are defined.
     */
    public String CONFIG_DO_NOT_TOUCH = "1";
    public String c1 = "Whether any logging is enabled.";
    public boolean enabled = true;
    public String c2 = "Whether the mod should log an info message if a feature is disabled";
    public boolean outputDisabledLoggerInfo = true;
    public String c3 = "Enable or disabled certain loggers";
    public boolean logPrivateMessages = true;
    public boolean logTellraw = true;
    public boolean logMe = true;
    public String c4 = "Whether to only log to console.";
    public boolean onlyLogToConsole = false;

    public void update() {
    }
}
