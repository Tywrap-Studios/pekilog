package net.tywrapstudios.pekilog.config.data;

public class Config {
    public final boolean logPrivateMessages;
    public final boolean logTellraw;
    public final String MOD_VERSION_DO_NOT_TOUCH;

    public Config(ConfigData data) {
        this.logPrivateMessages = data.logPrivateMessages;
        this.logTellraw = data.logTellraw;
        this.MOD_VERSION_DO_NOT_TOUCH = data.MOD_VERSION_DO_NOT_TOUCH;
    }
}
