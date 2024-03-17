package tech.harmless.mc.whatwasthat.blockentities;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;
import tech.harmless.mc.whatwasthat.WhatWasThat;

public final class WhatBlockEntities {
    //    public static final BlockEntityType<JukeboxBlockEntity> BROKEN_JUKEBOX = register(BrokenJukeboxBlock.ID,
    // FabricBlockEntityTypeBuilder.create(JukeboxBlockEntity::new, WhatBlocks.BROKEN_JUKEBOX).build());

    public static void init() {}

    public static <T extends BlockEntity> @NotNull BlockEntityType<T> register(
            @NotNull String id, @NotNull BlockEntityType<T> type) {
        return Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(WhatWasThat.MOD_ID, id), type);
    }
}
