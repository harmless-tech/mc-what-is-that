package tech.harmless.mc.whatwasthat.config;

import eu.midnightdust.lib.config.MidnightConfig;

public final class WhatConfig extends MidnightConfig {
    @Comment(centered = true)
    public static Comment textTicks;

    @Entry(min = 1)
    public static int ticksPerAttempt = 100;

    @Entry(min = 1)
    public static int ticksMinBother = 1200;

    @Entry(min = 1)
    public static int ticksMaxBother = 18000;

    @Comment(centered = true)
    public static Comment textScalars;

    @Entry(min = 1)
    public static int angerNether = 3;

    @Entry(min = 1)
    public static int angerEnd = 5;

    @Entry(min = 1)
    public static int angerInNether = 12;

    @Entry(min = 1)
    public static int angerInEnd = 17;

    @Entry(min = 1)
    public static int increasePerPlayer = 2;

    @Comment(centered = true)
    public static Comment textChances;

    @Comment(centered = true)
    public static Comment textChancesDesc;
}
