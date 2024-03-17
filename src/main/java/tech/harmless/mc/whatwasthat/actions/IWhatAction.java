package tech.harmless.mc.whatwasthat.actions;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public interface IWhatAction {
    void update(@NotNull ServerLevel world, @NotNull ServerPlayer player);

    boolean isDone();
}
