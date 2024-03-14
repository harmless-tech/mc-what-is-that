package tech.harmless.mc.whatwasthat;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.harmless.mc.whatwasthat.util.Tuple;
import tech.harmless.mc.whatwasthat.what.IWhat;
import tech.harmless.mc.whatwasthat.what.WhatSoundIncrease;

public class WhatWasThat implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("what-was-that");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		ServerTickEvents.END_WORLD_TICK.addPhaseOrdering(phase1, phase2);
		ServerTickEvents.END_WORLD_TICK.register(phase1, WhatWasThat::decideTickListener);
		ServerTickEvents.END_WORLD_TICK.register(phase2, WhatWasThat::actorTickListener);

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("wwt_test").executes(context -> {
				var src = context.getSource();
				var player = src.getPlayer();

				assert player != null;
				actions.add(new Tuple<>(player, new WhatSoundIncrease(player,
						RegistryEntry.of(SoundEvents.BLOCK_MUD_STEP), 25.0, 0.25f, 0.1f)));

				return 1;
			}));
		});
	}

	protected static final Identifier phase1 = new Identifier("what-was-that", "tickphase1");
	protected static final Identifier phase2 = new Identifier("what-was-that", "tickphase2");

	static int tickTrack = 0;

	public static void decideTickListener(ServerWorld world) {
		tickTrack += 1;
		if (tickTrack < 20)
			return;

		// TODO: This should account for nether and end visits.
		var player = world.getRandomAlivePlayer();
		if (player == null)
			return;

		int n = world.getRandom().nextBetween(0, 100);
		if (n != 1)
			return;

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

	// TODO: test if holding this breaks things...
	static final List<Tuple<ServerPlayerEntity, IWhat>> actions = new ArrayList<>();

	public static void actorTickListener(ServerWorld world) {
		for (int i = 0; i < actions.size(); i++) {
			var action = actions.get(i);
			var player = action.x();
			if (player != null) {
				action.y().update(world, player);
				if (action.y().isDone()) {
					actions.remove(i);
					i--;
				}
			} else {
				actions.remove(i);
				i--;
			}
		}
	}
}
