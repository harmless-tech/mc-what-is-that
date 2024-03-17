package tech.harmless.mc.whatwasthat.blocks;

import java.util.List;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JukeboxBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.harmless.mc.whatwasthat.util.WhatUtils;

public class BrokenJukeboxBlock extends JukeboxBlock implements IWhatBlock {
    public BrokenJukeboxBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(
            ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(WhatUtils.toolTipText(id()));
    }

    protected static BrokenJukeboxBlock createInstance() {
        return new BrokenJukeboxBlock(FabricBlockSettings.ofFullCopy(Blocks.JUKEBOX));
    }

    @Override
    public @NotNull String id() {
        return "broken_jukebox";
    }

    @Override
    public @NotNull Item.Properties createItemProps() {
        return new Item.Properties().rarity(Rarity.UNCOMMON);
    }
}
