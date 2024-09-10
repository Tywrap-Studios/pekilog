package net.tywrapstudios.pekilog.mixin;

import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.command.MessageCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(MessageCommand.class)
public abstract class LogMessageCommandMixin {
    @Inject(
            method = "execute",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/ServerCommandSource;sendChatMessage(Lnet/minecraft/network/message/SentMessage;ZLnet/minecraft/network/message/MessageType$Parameters;)V"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/ServerCommandSource;sendChatMessage(Lnet/minecraft/network/message/SentMessage;ZLnet/minecraft/network/message/MessageType$Parameters;)V")
            )
    )
    private static void pekilog$sendMessageCommandFeedBack(ServerCommandSource source, Collection<ServerPlayerEntity> targets, SignedMessage message, CallbackInfo ci) {
        ServerPlayerEntity player = source.getPlayer();
        Text pekitext = message.getContent();
        Text playerName = source.getDisplayName();
        Text targetName = targets.iterator().next().getName();
        if (source.getEntity() == player) {
            source.sendFeedback(() -> {
                return Text.translatable("pekiLog.messageCommand", playerName, pekitext, targetName);
            }, true);
        }
    }
}
