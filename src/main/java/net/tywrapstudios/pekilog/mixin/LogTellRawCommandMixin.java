package net.tywrapstudios.pekilog.mixin;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.TextArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.TellRawCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.tywrapstudios.pekilog.Pekilog;
import net.tywrapstudios.pekilog.config.ConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;

@Mixin(TellRawCommand.class)
public abstract class LogTellRawCommandMixin {
    @Inject(
            method = "method_13777",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;sendMessageToClient(Lnet/minecraft/text/Text;Z)V"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;sendMessageToClient(Lnet/minecraft/text/Text;Z)V")
            )
    )
    private static void pekilog$logTellRawCommand(CommandContext context, CallbackInfoReturnable<Integer> cir) throws CommandSyntaxException {
        if (ConfigManager.getConfig().logTellraw && ConfigManager.getConfig().enabled) {
            ServerCommandSource source = (ServerCommandSource) context.getSource();
            Text message = TextArgumentType.getTextArgument(context, "message");
            Text playerName = source.getDisplayName();
            Iterator varMix = EntityArgumentType.getPlayers(context, "targets").iterator();
            ServerPlayerEntity targets = (ServerPlayerEntity) varMix.next();
            Text targetName = targets.getDisplayName();
            source.sendFeedback(() -> {
                return Text.translatable("pekiLog.tellrawCommand", playerName, message, targetName);
            }, true);
            String messageString = message.getString();
            String playerNameString = playerName.getString();
            Pekilog.LOGGER_COMMANDS.info("/tellraw was ran by [" + playerNameString + "] and it said [" + messageString + "]");
        } else if (ConfigManager.getConfig().outputDisabledLoggerInfo) {
            Pekilog.LOGGER_COMMANDS.info("command [logTellraw] was not logged as per Config.");
        }
    }
}
