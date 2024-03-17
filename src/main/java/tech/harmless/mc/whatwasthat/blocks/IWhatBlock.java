package tech.harmless.mc.whatwasthat.blocks;

import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public interface IWhatBlock {
    @NotNull String id();

    @NotNull Item.Properties createItemProps();
}
