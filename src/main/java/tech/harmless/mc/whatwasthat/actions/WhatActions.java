package tech.harmless.mc.whatwasthat.actions;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.harmless.mc.whatwasthat.WhatWasThat;
import tech.harmless.mc.whatwasthat.advancements.WhatAdvancements;
import tech.harmless.mc.whatwasthat.config.WhatConfig;
import tech.harmless.mc.whatwasthat.util.Tuple;

public class WhatActions {
    static final ResourceLocation PHASE_1 = new ResourceLocation(WhatWasThat.MOD_ID, "tickactionphase1");
    static final ResourceLocation PHASE_2 = new ResourceLocation(WhatWasThat.MOD_ID, "tickactionphase2");

    @Nullable private static ServerPlayer badPlayer;

    private static int ticksLockBadPlayer;
    private static int maxTicksLockBadPlayer;

    @Nullable private static IWhatAction action;

    private static int tickTrack = 0;

    public static void init() {
        ServerTickEvents.END_WORLD_TICK.addPhaseOrdering(PHASE_1, PHASE_2);
        ServerTickEvents.END_WORLD_TICK.register(PHASE_1, WhatActions::decideTickListener);
        ServerTickEvents.END_WORLD_TICK.register(PHASE_2, WhatActions::actorTickListener);
    }

    public static void set(@NotNull ServerPlayer player, @NotNull IWhatAction action) {
        WhatActions.badPlayer = player;
        ticksLockBadPlayer = 0;
        maxTicksLockBadPlayer = 0;

        WhatActions.action = action;
    }

    public static @Nullable ServerPlayer getBadPlayer() {
        return badPlayer;
    }

    public static @NotNull Tuple<Integer, Integer> getLockTicks() {
        return new Tuple<>(ticksLockBadPlayer, maxTicksLockBadPlayer);
    }

    public static void decideTickListener(ServerLevel world) {
        tickTrack += 1;
        if (tickTrack < WhatConfig.ticksPerAttempt) return;

        if (badPlayer == null) {
            var player = world.getRandomPlayer();
            if (player == null) return;

            var random = world.getRandom();
            int score = (world.players().size() * WhatConfig.increasePerPlayer);

            // Scoring based on dimension info
            score += WhatAdvancements.isDoneTrackNether(player, world) ? WhatConfig.angerVisitedNether : 0;
            score += WhatAdvancements.isDoneTrackEnd(player, world) ? WhatConfig.angerVisitedEnd : 0;
            score += world.dimension().equals(ServerLevel.NETHER) ? WhatConfig.angerInNether : 0;
            score += world.dimension().equals(ServerLevel.END) ? WhatConfig.angerInEnd : 0;

            // Chance based on in cave or not
            var chance = Integer.MAX_VALUE;
            if (player.position().y < world.getSeaLevel() && !world.canSeeSky(player.blockPosition()))
                chance = WhatConfig.chanceInCave;
            else chance = WhatConfig.chanceOutsideCave;

            var i = random.nextInt(0, chance);
            if (i < score) {
                // TODO: base64 or hex logs
                WhatWasThat.LOGGER.info("Player {} was locked.", player.getScoreboardName());

                badPlayer = player;
                action = null;
                ticksLockBadPlayer = 0;
                maxTicksLockBadPlayer =
                        random.nextIntBetweenInclusive(WhatConfig.ticksMinBother, WhatConfig.ticksMaxBother);
            }
        } else if (action == null) {
            // TODO: Decide on harassment action.

            action = new ActionSoundRunningTowards(badPlayer, Holder.direct(SoundEvents.MUD_STEP), 30.0, 0.25f, 0.05f);
        }
    }

    // TODO: Add new phase that checks for releasing items. and events.

    public static void actorTickListener(ServerLevel world) {
        if (badPlayer == null) return;
        if (badPlayer.hasDisconnected()) {
            // TODO: base64 or hex logs
            WhatWasThat.LOGGER.info("Player {} disconnected and was unlocked.", badPlayer.getScoreboardName());
            badPlayer = null;
            return;
        }

        // Release player
        if (action == null) {
            if (ticksLockBadPlayer > maxTicksLockBadPlayer) {
                // TODO: base64 or hex logs
                WhatWasThat.LOGGER.info("Player {} hit max ticks and was unlocked.", badPlayer.getScoreboardName());
                badPlayer = null;
            }
            return;
        }

        ticksLockBadPlayer++;

        action.update(world, badPlayer);
        if (action.isDone()) action = null;
    }
}
