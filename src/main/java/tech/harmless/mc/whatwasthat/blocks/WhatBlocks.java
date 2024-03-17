package tech.harmless.mc.whatwasthat.blocks;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import tech.harmless.mc.whatwasthat.WhatWasThat;

public final class WhatBlocks {
    public static final BrokenJukeboxBlock BROKEN_JUKEBOX =
            register(BrokenJukeboxBlock.ID, new BrokenJukeboxBlock(BrokenJukeboxBlock.BLOCK_PROPS));

    public static void init() {}

    public static <T extends Block> @NotNull T register(@NotNull String id, @NotNull T block) {
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(WhatWasThat.MOD_ID, id), block);
    }
}
