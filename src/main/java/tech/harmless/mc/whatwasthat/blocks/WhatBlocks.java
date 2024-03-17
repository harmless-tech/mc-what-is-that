package tech.harmless.mc.whatwasthat.blocks;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import tech.harmless.mc.whatwasthat.WhatWasThat;

public final class WhatBlocks {

    public static final BrokenJukeboxBlock BROKEN_JUKEBOX_BLOCK = BrokenJukeboxBlock.createInstance();

    public static void init() {
        register(BROKEN_JUKEBOX_BLOCK);
    }

    private static <T extends Block & IWhatBlock> void register(T block) {
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(WhatWasThat.MOD_ID, block.id()), block);
        Registry.register(
                BuiltInRegistries.ITEM,
                new ResourceLocation(WhatWasThat.MOD_ID, block.id()),
                new BlockItem(block, block.createItemProps()));
    }
}
