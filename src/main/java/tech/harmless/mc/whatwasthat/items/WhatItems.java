package tech.harmless.mc.whatwasthat.items;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import tech.harmless.mc.whatwasthat.blocks.BrokenJukeboxBlock;
import tech.harmless.mc.whatwasthat.blocks.WhatBlocks;

public final class WhatItems {
    public static final Item BROKEN_JUKEBOX = registerBlock(WhatBlocks.BROKEN_JUKEBOX, BrokenJukeboxBlock.ITEM_PROPS);

    public static void init() {}

    public static @NotNull Item registerBlock(@NotNull Block block, Item.Properties itemProps) {
        return Registry.register(
                BuiltInRegistries.ITEM, BuiltInRegistries.BLOCK.getKey(block), new BlockItem(block, itemProps));
    }
}
