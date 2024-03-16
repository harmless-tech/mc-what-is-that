package tech.harmless.mc.whatwasthat.what;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public interface IWhat {
    void update(@NotNull ServerLevel world, @NotNull ServerPlayer player);

    boolean isDone();
}
