package tech.harmless.mc.whatwasthat.what;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;

public interface IWhat {
	void update(@NotNull ServerWorld world, @NotNull ServerPlayerEntity player);

	boolean isDone();
}
