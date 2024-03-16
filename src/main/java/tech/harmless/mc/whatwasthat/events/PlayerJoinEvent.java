package tech.harmless.mc.whatwasthat.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerPlayer;

@FunctionalInterface
public interface PlayerJoinEvent {
    Event<PlayerJoinEvent> EVENT = EventFactory.createArrayBacked(PlayerJoinEvent.class, (listeners) -> (player) -> {
        for (var listener : listeners) listener.joinServer(player);
    });

    void joinServer(ServerPlayer player);
}
