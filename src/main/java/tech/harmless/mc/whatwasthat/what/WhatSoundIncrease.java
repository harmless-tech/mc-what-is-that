package tech.harmless.mc.whatwasthat.what;

import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public class WhatSoundIncrease implements IWhat {
	private final RegistryEntry<SoundEvent> sound;
	private float pitch;
	private final float increasePitch;
	private boolean done;
	private int spacing;
	private final float yaw1;
	private final float yaw2;
	private Vec3d pos;
	private final Vec3d facing;

	public WhatSoundIncrease(@NotNull ServerPlayerEntity player, @NotNull RegistryEntry<SoundEvent> sound,
			double startDis, float startPitch, float increasePitch) {
		this.sound = sound;
		this.pitch = startPitch;
		this.increasePitch = increasePitch;

		var tmp = new Vec3d(player.getMovementDirection().getOpposite().getUnitVector());
		facing = new Vec3d(tmp.x, 0.0, tmp.z);
		var v = player.getPos().add(facing.multiply(startDis));
		pos = v.add(0, -1.0, 0);

		var yaw = player.getHeadYaw();
		yaw1 = yaw - 100f;
		yaw2 = yaw + 100f;

		done = false;
	}

	@Override
	public void update(@NotNull ServerWorld world, @NotNull ServerPlayerEntity player) {
		var cYaw = player.getHeadYaw();
		if (yaw1 < -180f && cYaw <= yaw2 && cYaw >= 180f + yaw1) {
			done = true;
			return;
		} else if (yaw2 > 180f && cYaw >= yaw1 && cYaw <= yaw2 - 180f) {
			done = true;
			return;
		} else if (cYaw <= yaw1 || cYaw >= yaw2) {
			done = true;
			return;
		}

		spacing++;
		if (spacing < 12)
			return;
		spacing = 0;

		player.networkHandler.sendPacket(new PlaySoundS2CPacket(sound, SoundCategory.NEUTRAL, pos.getX(), pos.getY(),
				pos.getZ(), 2f, pitch, world.getRandom().nextLong()));

		pos = pos.subtract(facing);
		pitch += increasePitch;

		if (pos.distanceTo(player.getPos()) <= 2.5f)
			done = true;
	}

	@Override
	public boolean isDone() {
		return done;
	}
}
