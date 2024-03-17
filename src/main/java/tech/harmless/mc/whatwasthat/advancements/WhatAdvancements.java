package tech.harmless.mc.whatwasthat.advancements;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.harmless.mc.whatwasthat.WhatWasThat;

public final class WhatAdvancements {

    private static boolean dirty = false;

    private static AdvancementHolder trackNether = null;
    private static AdvancementHolder trackEnd = null;

    public static void init() {
        ResourceManagerHelper.get(PackType.SERVER_DATA)
                .registerReloadListener(new SimpleSynchronousResourceReloadListener() {
                    @Override
                    public @NotNull ResourceLocation getFabricId() {
                        return new ResourceLocation(WhatWasThat.MOD_ID, "advancement_resource_listener");
                    }

                    @Override
                    public void onResourceManagerReload(ResourceManager resourceManager) {
                        dirty = true;
                    }
                });

        dirty = true;
    }

    public static @Nullable AdvancementHolder getTrackNether(@NotNull ServerAdvancementManager m) {
        get(m);
        return trackNether;
    }

    public static boolean isDoneTrackNether(@NotNull ServerPlayer player, @NotNull ServerLevel world) {
        var m = world.getServer().getAdvancements();
        get(m);
        if (trackNether == null) return false;

        return player.getAdvancements().getOrStartProgress(trackNether).isDone();
    }

    public static @Nullable AdvancementHolder getTrackEnd(@NotNull ServerAdvancementManager m) {
        get(m);
        return trackEnd;
    }

    public static boolean isDoneTrackEnd(@NotNull ServerPlayer player, @NotNull ServerLevel world) {
        var m = world.getServer().getAdvancements();
        get(m);
        if (trackEnd == null) return false;

        return player.getAdvancements().getOrStartProgress(trackEnd).isDone();
    }

    private static void get(@NotNull ServerAdvancementManager m) {
        trackNether = m.get(new ResourceLocation(WhatWasThat.MOD_ID, "track_nether"));
        trackEnd = m.get(new ResourceLocation(WhatWasThat.MOD_ID, "track_end"));

        dirty = false;
    }
}
