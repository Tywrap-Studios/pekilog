package net.tywrapstudios.pekilog.config.data;


public class Config {
    public String MOD_VERSION_DO_NOT_TOUCH;
//  enabled;
    public boolean enabled;
//  info;
    public boolean outputDisabledLoggerInfo;
//  loggers;
    public boolean logPrivateMessages;
    public boolean logTellraw;
    public boolean logMe;


    public Config(ConfigData data) {
        this.MOD_VERSION_DO_NOT_TOUCH = data.MOD_VERSION_DO_NOT_TOUCH;
        this.enabled = data.enabled;
        this.outputDisabledLoggerInfo = data.outputDisabledLoggerInfo;
        this.logPrivateMessages = data.logPrivateMessages;
        this.logTellraw = data.logTellraw;
        this.logMe = data.logMe;
    }
}
