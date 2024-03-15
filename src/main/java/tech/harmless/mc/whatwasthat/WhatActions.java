package tech.harmless.mc.whatwasthat;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.harmless.mc.whatwasthat.config.WhatConfig;
import tech.harmless.mc.whatwasthat.util.Tuple;
import tech.harmless.mc.whatwasthat.what.IWhat;

import java.util.Optional;

// TODO: Target a player and bother them for a while.
public class WhatActions {
	static final ResourceLocation PHASE_1 = new ResourceLocation(WhatWasThat.MOD_ID, "tickphase1");
	static final ResourceLocation PHASE_2 = new ResourceLocation(WhatWasThat.MOD_ID, "tickphase2");

	@Nullable private static ServerPlayer badPlayer;
	private static int ticksBotherBadPlayer;
	private static int maxTicksBotherBadPlayer;

	@Nullable private static IWhat action;
	private static int tickTrack = 0;

	public static void init() {
		ServerTickEvents.END_WORLD_TICK.addPhaseOrdering(PHASE_1, PHASE_2);
		ServerTickEvents.END_WORLD_TICK.register(PHASE_1, WhatActions::decideTickListener);
		ServerTickEvents.END_WORLD_TICK.register(PHASE_2, WhatActions::actorTickListener);
	}

	protected static void set(@NotNull ServerPlayer player, @NotNull IWhat action) {
		WhatActions.badPlayer = player;
		ticksBotherBadPlayer = 0;
		maxTicksBotherBadPlayer = 0;

		WhatActions.action = action;
	}

	public static void decideTickListener(ServerLevel world) {
		tickTrack += 1;
		if (tickTrack < WhatConfig.ticksPerAction)
			return;

		if (badPlayer != null)
			WhatWasThat.LOGGER.info("Holding player {}", badPlayer.getScoreboardName());

		if (badPlayer == null) {
			// TODO
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
			ticksBotherBadPlayer = 0;
			return;
		}
		ticksBotherBadPlayer++;

		if (action == null) {
			if (ticksBotherBadPlayer >= maxTicksBotherBadPlayer)
				badPlayer = null;
			return;
		}

		action.update(world, badPlayer);
		if (action.isDone())
			action = null;
	}
}
