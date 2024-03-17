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
import tech.harmless.mc.whatwasthat.actions.ActionSoundRunningTowards;
import tech.harmless.mc.whatwasthat.actions.WhatActions;
import tech.harmless.mc.whatwasthat.advancements.JoinTrigger;
import tech.harmless.mc.whatwasthat.blockentities.WhatBlockEntities;
import tech.harmless.mc.whatwasthat.blocks.WhatBlocks;
import tech.harmless.mc.whatwasthat.config.WhatConfig;
import tech.harmless.mc.whatwasthat.events.PlayerJoinEvent;
import tech.harmless.mc.whatwasthat.itemgroup.WhatItemGroup;
import tech.harmless.mc.whatwasthat.items.WhatItems;

public final class WhatWasThat implements ModInitializer {
    public static final String MOD_NAME = "What Was That?";
    public static final String MOD_ID = "what-was-that";

    public static final Logger LOGGER = LoggerFactory.getLogger("what-was-that");

    @Override
    public void onInitialize() {
        LOGGER.info("Init Stage for {} ({})", MOD_NAME, MOD_ID);

        MidnightConfig.init(MOD_ID, WhatConfig.class);

        // Init Stuff
        WhatBlocks.init();
        WhatBlockEntities.init();
        WhatItems.init();
        WhatItemGroup.init();
        WhatActions.init();

        // Events
        PlayerJoinEvent.EVENT.register(JoinTrigger.JOIN_TRIGGER::trigger);

        // TODO: Remove!
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("wwt_test").executes(context -> {
                CommandSourceStack src = context.getSource();
                var player = src.getPlayer();
                assert player != null;

                WhatActions.set(
                        player,
                        new ActionSoundRunningTowards(player, Holder.direct(SoundEvents.MUD_STEP), 30.0, 0.25f, 0.05f));

                return 1;
            }));
        }));
    }
}
