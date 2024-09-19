package net.tywrapstudios.pekilog.mixin;

import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.MeCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.tywrapstudios.pekilog.Pekilog;
import net.tywrapstudios.pekilog.config.ConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MeCommand.class)
public abstract class LogMeCommandMixin {
    @Inject(method = "method_43645",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/network/message/SignedMessage;Lnet/minecraft/server/command/ServerCommandSource;Lnet/minecraft/network/message/MessageType$Parameters;)V"),
            slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/network/message/SignedMessage;Lnet/minecraft/server/command/ServerCommandSource;Lnet/minecraft/network/message/MessageType$Parameters;)V"))
    )
    private static void pekilog$logMeCommand(PlayerManager playerManager, ServerCommandSource serverCommandSource, SignedMessage message, CallbackInfo ci) {
        if (ConfigManager.getConfig().logMe && ConfigManager.getConfig().enabled) {
            ServerCommandSource source = serverCommandSource;
            Text playerName = source.getDisplayName();
            Text messageText = message.getContent();
            if (!ConfigManager.getConfig().onlyLogToConsole) {
                source.sendFeedback(
                        Text.translatable("pekiLog.meCommand", messageText), true
                );
            }
            String messageString = messageText.getString();
            String playerNameString = playerName.getString();
            Pekilog.LOGGER_COMMANDS.info("[{}: \"{}\" using /me.]",playerNameString,messageString);
        } else if (ConfigManager.getConfig().outputDisabledLoggerInfo) {
            Pekilog.LOGGER_COMMANDS.info("command [logMe] was not logged as per Config.");
        }
    }
}
