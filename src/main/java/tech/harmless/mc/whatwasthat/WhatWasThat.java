package tech.harmless.mc.whatwasthat;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.harmless.mc.whatwasthat.advancements.JoinTrigger;
import tech.harmless.mc.whatwasthat.config.WhatConfig;
import tech.harmless.mc.whatwasthat.events.PlayerJoinEvent;
import tech.harmless.mc.whatwasthat.what.WASoundRunningTowards;

public final class WhatWasThat implements ModInitializer {
    public static final String MOD_NAME = "What Was That?";
    public static final String MOD_ID = "what-was-that";

    public static final Logger LOGGER = LoggerFactory.getLogger("what-was-that");

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Init Stage for {} ({})", MOD_NAME, MOD_ID);

        MidnightConfig.init(MOD_ID, WhatConfig.class);

        WhatActions.init();

        PlayerJoinEvent.EVENT.register(JoinTrigger.JOIN_TRIGGER::trigger);

        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("wwt_test").executes(context -> {
                CommandSourceStack src = context.getSource();
                var player = src.getPlayer();
                assert player != null;

                WhatActions.set(
                        player,
                        new WASoundRunningTowards(player, Holder.direct(SoundEvents.MUD_STEP), 30.0, 0.25f, 0.05f));

                return 1;
            }));
        }));

        // Commands
        //
        // CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess,
        // environment) -> {
        // dispatcher.register(CommandManager.literal("wwt_test").executes(context -> {
        // var src = context.getSource();
        // var player = src.getPlayer();
        //
        // assert player != null;
        // actions.add(new Tuple<>(player, new WhatSoundRunningTowards(player,
        // RegistryEntry.of(SoundEvents.BLOCK_MUD_STEP), 250.0, 0.25f, 0.1f)));
        //
        // return 1;
        // }));
        // });
    }
}
