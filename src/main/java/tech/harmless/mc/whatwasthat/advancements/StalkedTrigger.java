package tech.harmless.mc.whatwasthat.advancements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.NotNull;
import tech.harmless.mc.whatwasthat.WhatWasThat;

public class StalkedTrigger extends SimpleCriterionTrigger<StalkedTrigger.TriggerInstance> {
    public static final StalkedTrigger STALKED_TRIGGER = Registry.register(
            BuiltInRegistries.TRIGGER_TYPES,
            new ResourceLocation(WhatWasThat.MOD_ID, "stalked_trigger"),
            new StalkedTrigger());

    public void trigger(ServerPlayer player) {
        this.trigger(player, triggerInstance -> true);
    }

    @Override
    public @NotNull Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player)
            implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player")
                                .forGetter(TriggerInstance::player))
                        .apply(instance, TriggerInstance::new));

        public static Criterion<TriggerInstance> instance(ContextAwarePredicate player) {
            return STALKED_TRIGGER.createCriterion(new TriggerInstance(Optional.of(player)));
        }
    }
}
