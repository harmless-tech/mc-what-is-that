package tech.harmless.mc.whatwasthat.util;

import net.minecraft.network.chat.Component;
import tech.harmless.mc.whatwasthat.WhatWasThat;

public final class WhatUtils {
    public static Component toolTipText(String id) {
        return Component.translatable("item." + WhatWasThat.MOD_ID + "." + id + ".tooltip");
    }
}
