package net.tywrapstudios.pekilog.mixin;

import net.minecraft.network.message.MessageType;
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
import java.util.Iterator;

@Mixin(MessageCommand.class)
public abstract class LogMessageCommandMixin {
    @Inject(
            method = "method_44144",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/ServerCommandSource;sendChatMessage(Lnet/minecraft/network/message/SentMessage;ZLnet/minecraft/network/message/MessageType$Parameters;)V"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/ServerCommandSource;sendChatMessage(Lnet/minecraft/network/message/SentMessage;ZLnet/minecraft/network/message/MessageType$Parameters;)V")
            )
    )
    private static void pekilog$logMessageCommand(ServerCommandSource serverCommandSource, Collection collection, MessageType.Parameters parameters, SignedMessage message, CallbackInfo ci) {
        if (ConfigManager.getConfig().logPrivateMessages && ConfigManager.getConfig().enabled) {
            Iterator iterator = collection.iterator();
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)iterator.next();
            Text messages = message.getContent();
            Text playerName = serverCommandSource.getDisplayName();
            Text targetName = serverPlayerEntity.getDisplayName();
            if (!ConfigManager.getConfig().onlyLogToConsole) {
                serverCommandSource.sendFeedback(
                    Text.translatable("pekiLog.messageCommand", messages, targetName), true
                );
            }
            String messageString = messages.getString();
            String playerNameString = playerName.getString();
            Pekilog.LOGGER_COMMANDS.info("[{}: \"{}\" using /tell; /w; /msg.]",playerNameString,messageString);
        } else if (ConfigManager.getConfig().outputDisabledLoggerInfo) {
            Pekilog.LOGGER_COMMANDS.info("command [logPrivateMessages] was not logged as per Config.");
        }
    }
}
