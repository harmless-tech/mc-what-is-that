package tech.harmless.mc.whatwasthat.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

// TODO: Implement from scratch I guess...
public class BrokenJukeboxBlockEntity extends JukeboxBlockEntity {
    //    public static final BlockEntityType<BrokenJukeboxBlockEntity> ENTITY_TYPE =
    // FabricBlockEntityTypeBuilder.create(BrokenJukeboxBlockEntity::new, WhatBlocks.BROKEN_JUKEBOX).build();

    public BrokenJukeboxBlockEntity(BlockPos pos, BlockState blockState) {
        super(pos, blockState);
    }
}
