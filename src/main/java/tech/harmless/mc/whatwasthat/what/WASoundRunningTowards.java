package tech.harmless.mc.whatwasthat.what;

import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class WASoundRunningTowards implements IWhat {
    private final Holder<SoundEvent> sound;
    private final float increasePitch;
    private float pitch;

    private final float yaw1;
    private final float yaw2;
    private Vec3 pos;

    private int cycles;
    private int spacing;
    private int maxSpacing;
    private boolean done;

    public WASoundRunningTowards(
            @NotNull ServerPlayer player,
            @NotNull Holder<SoundEvent> sound,
            double startDis,
            float startPitch,
            float increasePitch) {
        this.sound = sound;
        this.pitch = startPitch;
        this.increasePitch = increasePitch;

        var tmp = new Vec3(player.getDirection().getOpposite().step());
        var facing = new Vec3(tmp.x, 0.0, tmp.z);
        var v = player.position().add(facing.multiply(startDis, startDis, startDis));
        pos = v.add(0, -1.0, 0);

        var yaw = player.getYHeadRot();
        yaw1 = yaw - 100f;
        yaw2 = yaw + 100f;

        cycles = 0;
        spacing = 0;
        maxSpacing = 15;
        done = false;
    }

    @Override
    public void update(@NotNull ServerLevel world, @NotNull ServerPlayer player) {
        var cYaw = player.getYHeadRot();
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
        if (spacing <= maxSpacing) return;
        cycles++;

        player.connection.send(new ClientboundSoundPacket(
                sound,
                SoundSource.NEUTRAL,
                pos.x,
                pos.y,
                pos.z,
                2f,
                pitch,
                world.getRandom().nextLong()));

        if (pos.distanceTo(player.position()) <= 2.5f) {
            done = true;
            return;
        }
        var to = pos.vectorTo(player.position()).normalize();
        pos = pos.add(to);

        pitch += increasePitch;

        if (cycles >= 20) {
            cycles = 0;
            maxSpacing--;
            if (maxSpacing <= 0) done = true;
        }
        spacing = 0;
    }

    @Override
    public boolean isDone() {
        return done;
    }
}
