package tech.harmless.mc.whatwasthat.mixin;

import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.harmless.mc.whatwasthat.events.PlayerJoinEvent;

@Mixin(PlayerList.class)
public class PlayerJoinMixin {
    @Inject(at = @At(value = "TAIL"), method = "placeNewPlayer")
    private void playerJoin(Connection connection, ServerPlayer player, CommonListenerCookie cookie, CallbackInfo ci) {
        PlayerJoinEvent.EVENT.invoker().joinServer(player);
    }
}
