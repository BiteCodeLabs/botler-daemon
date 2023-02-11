package org.bitecodelabs.botlerdaemon.mixin;

import java.util.List;
import net.minecraft.text.Text;
import org.bitecodelabs.botlerdaemon.Daemon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.dedicated.command.BanIpCommand;
import org.bitecodelabs.botlerdaemon.connections.SocketClient;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BanIpCommand.class)
public abstract class BanIpCommandMixin {
    @Inject(method = "banIp", at = @At("HEAD"))
    private static void ban(ServerCommandSource source, String targetIp, Text reason, CallbackInfoReturnable<Integer> cir)  {

        SocketClient socketClient = Daemon.Companion.getSOCKET();

        List<ServerPlayerEntity> list = source.getServer().getPlayerManager().getPlayersByIp(targetIp);

        for (ServerPlayerEntity serverPlayerEntity : list) {

            socketClient.emitEvent("BOTLER_SERVER_BAN", String.valueOf(serverPlayerEntity.getId()));

        }
    }
}