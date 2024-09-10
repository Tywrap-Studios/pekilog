package net.tywrapstudios.pekilog.mixin;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.TellRawCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TellRawCommand.class)
public abstract class LogTellRawCommandMixin {
    @Inject(
            method = "register",
            at = @At(value = "TAIL")
    )
    private static void pekilog$sendTellRawCommandFeedBack(CommandDispatcher<ServerCommandSource> dispatcher, CallbackInfo ci) {
        //TODO: Make Tellraw work I guess
    }
}
