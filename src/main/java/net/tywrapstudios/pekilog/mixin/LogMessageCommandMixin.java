package net.tywrapstudios.pekilog.mixin;

import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.command.MessageCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.tywrapstudios.pekilog.Pekilog;
import net.tywrapstudios.pekilog.config.ConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

//TODO: Fix this shit not working Server-Side ong :pray:

@Mixin(MessageCommand.class)
public abstract class LogMessageCommandMixin {
    @Inject(
            method = "execute",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/ServerCommandSource;sendChatMessage(Lnet/minecraft/network/message/SentMessage;ZLnet/minecraft/network/message/MessageType$Parameters;)V"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/ServerCommandSource;sendChatMessage(Lnet/minecraft/network/message/SentMessage;ZLnet/minecraft/network/message/MessageType$Parameters;)V")
            )
    )
    private static void pekilog$logMessageCommand(ServerCommandSource source, Collection<ServerPlayerEntity> targets, SignedMessage message, CallbackInfo ci) {
        if (ConfigManager.getConfig().logPrivateMessages && ConfigManager.getConfig().enabled) {
            Text messages = message.getContent();
            Text playerName = source.getDisplayName();
            Text targetName = targets.iterator().next().getName();
            if (!ConfigManager.getConfig().onlyLogToConsole) {
                source.sendFeedback(() -> {
                    return Text.translatable("pekiLog.messageCommand", messages, targetName);
                }, true);
            }
            String messageString = messages.getString();
            String playerNameString = playerName.getString();
            Pekilog.LOGGER_COMMANDS.info("[{}: \"{}\" using /tell; /w; /msg.]",playerNameString,messageString);
        } else if (ConfigManager.getConfig().outputDisabledLoggerInfo) {
            Pekilog.LOGGER_COMMANDS.info("command [logPrivateMessages] was not logged as per Config.");
        }
    }
}
