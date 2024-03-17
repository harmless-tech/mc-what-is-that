package tech.harmless.mc.whatwasthat.actions;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.harmless.mc.whatwasthat.WhatWasThat;
import tech.harmless.mc.whatwasthat.advancements.WhatAdvancements;
import tech.harmless.mc.whatwasthat.config.WhatConfig;

// TODO: Target a player and bother them for a while.
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

    public static void decideTickListener(ServerLevel world) {
        tickTrack += 1;
        if (tickTrack < WhatConfig.ticksPerAttempt) return;

        if (badPlayer != null) WhatWasThat.LOGGER.info("Locked to player {}", badPlayer.getScoreboardName());

        if (badPlayer == null) {
            // TODO: Lock onto a bad player
            var player = world.getRandomPlayer();
            if (player == null) return;

            int score = (world.players().size() * WhatConfig.increasePerPlayer);
            score += WhatAdvancements.isDoneTrackNether(player, world) ? WhatConfig.angerVisitedNether : 0;
            score += WhatAdvancements.isDoneTrackEnd(player, world) ? WhatConfig.angerVisitedEnd : 0;
            score += world.dimension().equals(ServerLevel.NETHER) ? WhatConfig.angerInNether : 0;
            score += world.dimension().equals(ServerLevel.END) ? WhatConfig.angerInEnd : 0;

            WhatWasThat.LOGGER.warn("Score for {} is {}.", player.getScoreboardName(), score);

            //            player.getServer().getAdvancements()
            //
            //            world.getServer().getAdvancements().get();
            //            player.getAdvancements().getOrStartProgress().isDone();

            var i = world.dimensionTypeId().registry();
            WhatWasThat.LOGGER.info(i.toString());

            // TODO: Determine dimension using advancements.

            //            Level.NETHER

            //            Registry.register(BuiltInRegistries.)

            //            world.registryAccess().registryOrThrow(BuiltInRegistries.)

        } else if (action == null) {
            // TODO
        }

        // TODO: This should account for nether and end visits.
        // var player = world.getRandomPlayer();
        // if (player == null)
        // return;
        //
        // var bPos = player.blockPosition();
        // int rand;
        // if (player.position().y < world.getSeaLevel() && !world.canSeeSky(bPos))
        // rand = world.getRandom().nextInt(0, WhatConfig.chanceInCave);
        // else
        // rand = world.getRandom().nextInt(0, WhatConfig.chanceOutsideCave);
        //
        // if (rand != 0)
        // return;

        // actions.add(new Tuple<>(player, new WhatSoundIncrease(
        // player,
        // RegistryEntry.of(SoundEvents.BLOCK_MUD_STEP),
        // 10,
        // 0.25f,
        // 0.25f,
        // 0.25f,
        // 0.10f
        // )));
    }

    public static void actorTickListener(ServerLevel world) {
        if (badPlayer == null || badPlayer.hasDisconnected()) {
            action = null;
            ticksLockBadPlayer = 0;
            return;
        }
        ticksLockBadPlayer++;

        if (action == null) {
            if (ticksLockBadPlayer >= maxTicksLockBadPlayer) badPlayer = null;
            return;
        }

        action.update(world, badPlayer);
        if (action.isDone()) action = null;
    }
}
