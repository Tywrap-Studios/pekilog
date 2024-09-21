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

import java.util.Collection;
import java.util.Iterator;

@Mixin(TellRawCommand.class)
public abstract class LogTellRawCommandMixin {
    @Inject(
            method = "method_13777",
            at = @At(value = "HEAD")
    )
    private static void pekilog$logTellRawCommand(CommandContext context, CallbackInfoReturnable<Integer> cir) throws CommandSyntaxException {
        if (ConfigManager.getConfig().logTellraw && ConfigManager.getConfig().enabled) {
            ServerCommandSource source = (ServerCommandSource) context.getSource();
            Text message = TextArgumentType.getTextArgument(context, "message");
            Text playerName = source.getDisplayName();
            Collection<ServerPlayerEntity> targets = EntityArgumentType.getPlayers(context, "player");
            if (!ConfigManager.getConfig().onlyLogToConsole) {
                if (targets.size() == 1) {
                    source.sendFeedback(
                        Text.translatable("pekiLog.tellrawCommand", playerName, targets.iterator().next().getDisplayName())
                    , true);
                } else {
                    source.sendFeedback(
                        Text.translatable("pekiLog.tellrawCommand.multi", playerName, targets.size())
                    , true);
                }
            }
            String messageString = message.getString();
            String playerNameString = playerName.getString();
            Pekilog.LOGGER_COMMANDS.info("[{}: \"{}\" using /tellraw.]",playerNameString, messageString);
        } else if (ConfigManager.getConfig().outputDisabledLoggerInfo) {
            Pekilog.LOGGER_COMMANDS.info("command [logTellraw] was not logged as per Config.");
        }
    }
}
