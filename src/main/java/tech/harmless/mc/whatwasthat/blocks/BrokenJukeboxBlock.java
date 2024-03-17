package tech.harmless.mc.whatwasthat.blocks;

import java.util.List;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;
import tech.harmless.mc.whatwasthat.util.WhatUtils;

public class BrokenJukeboxBlock extends JukeboxBlock {
    public static final String ID = "broken_jukebox";
    public static final BlockBehaviour.Properties BLOCK_PROPS = FabricBlockSettings.create()
            .mapColor(MapColor.DIRT)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.0F, 6.0F)
            .sound(SoundType.WOOD)
            .ignitedByLava();
    public static final Item.Properties ITEM_PROPS = new FabricItemSettings().rarity(Rarity.UNCOMMON);

    public BrokenJukeboxBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(
            ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(WhatUtils.toolTipText(ID));
    }
}
