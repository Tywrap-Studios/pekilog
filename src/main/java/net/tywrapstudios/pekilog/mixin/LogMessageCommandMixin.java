package net.tywrapstudios.pekilog.mixin;

import net.minecraft.command.argument.MessageArgumentType;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Iterator;

@Mixin(MessageCommand.class)
public abstract class LogMessageCommandMixin {
    @Inject(
            method = "execute",
            at = @At(value = "HEAD")
    )
    private static void pekilog$logMessageCommand(ServerCommandSource source, Collection<ServerPlayerEntity> targets, MessageArgumentType.SignedMessage signedMessage, CallbackInfoReturnable<Integer> cir) {
        if (ConfigManager.getConfig().logPrivateMessages && ConfigManager.getConfig().enabled) {
            Text messages = signedMessage.signedArgument().getContent();
            Text playerName = source.getDisplayName();
            if (!ConfigManager.getConfig().onlyLogToConsole) {
                if (targets.size() == 1) {
                    source.sendFeedback(
                        Text.translatable("pekiLog.messageCommand", playerName, targets.iterator().next().getDisplayName())
                    , true);
                } else {
                    source.sendFeedback(
                        Text.translatable("pekiLog.messageCommand.multi", playerName, targets.size())
                    , true);
                }
            }
            String messageString = messages.getString();
            String playerNameString = playerName.getString();
            Pekilog.LOGGER_COMMANDS.info("[{}: \"{}\" using /tell; /w; /msg.]",playerNameString,messageString);
        } else if (ConfigManager.getConfig().outputDisabledLoggerInfo) {
            Pekilog.LOGGER_COMMANDS.info("command [logPrivateMessages] was not logged as per Config.");
        }
    }
}
