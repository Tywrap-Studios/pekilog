package net.tywrapstudios.pekilog.mixin;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ReloadCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.tywrapstudios.pekilog.Pekilog;
import net.tywrapstudios.pekilog.config.ConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(ReloadCommand.class)
public abstract class ReloadMyConfig {
    @Inject(method = "method_13530",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/ReloadCommand;findNewDataPacks(Lnet/minecraft/resource/ResourcePackManager;Lnet/minecraft/world/SaveProperties;Ljava/util/Collection;)Ljava/util/Collection;"),
            slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/server/command/ReloadCommand;findNewDataPacks(Lnet/minecraft/resource/ResourcePackManager;Lnet/minecraft/world/SaveProperties;Ljava/util/Collection;)Ljava/util/Collection;"))
    )
    private static void pekilog$ReloadMyConfig(CommandContext context, CallbackInfoReturnable<Integer> cir) {
        ServerCommandSource source = (ServerCommandSource) context.getSource();
        if (Objects.equals(ConfigManager.getConfig().CONFIG_DO_NOT_TOUCH, Pekilog.CONFIG_V)) {
            ConfigManager.reloadConfig(context);
        } else {
            source.sendFeedback(
                    Text.translatable("pekiLog.configSyncError").formatted(Formatting.DARK_RED), false
            );
            Pekilog.LOGGER.error("[Config] Your Config Version is out of Sync, please delete your config file and reload Minecraft.");
        }
    }
}
