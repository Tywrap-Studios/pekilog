package net.tywrapstudios.pekilog.config.data;


public class Config {
    public String MOD_VERSION_DO_NOT_TOUCH;
//    public String comment_enabled;
    public boolean enabled;
//    public String comment_info;
    public boolean outputDisabledLoggerInfo;
//    public String comment_loggers;
    public boolean logPrivateMessages;
    public boolean logTellraw;


    public Config(ConfigData data) {
        this.MOD_VERSION_DO_NOT_TOUCH = data.MOD_VERSION_DO_NOT_TOUCH;
        this.enabled = data.enabled;
        this.outputDisabledLoggerInfo = data.outputDisabledLoggerInfo;
        this.logPrivateMessages = data.logPrivateMessages;
        this.logTellraw = data.logTellraw;
    }
}
