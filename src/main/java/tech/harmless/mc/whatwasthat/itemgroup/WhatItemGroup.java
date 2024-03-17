package tech.harmless.mc.whatwasthat.itemgroup;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import tech.harmless.mc.whatwasthat.WhatWasThat;
import tech.harmless.mc.whatwasthat.items.WhatItems;

public final class WhatItemGroup {
    public static final CreativeModeTab ITEM_GROUP = FabricItemGroup.builder()
            .title(Component.translatable("what-was-that.name"))
            .icon(() -> new ItemStack(Items.PINK_PETALS))
            .displayItems((params, out) -> {
                out.accept(WhatItems.BROKEN_JUKEBOX);
            })
            .build();

    public static void init() {
        Registry.register(
                BuiltInRegistries.CREATIVE_MODE_TAB,
                new ResourceLocation(WhatWasThat.MOD_ID, "item_group_main"),
                ITEM_GROUP);
    }
}
