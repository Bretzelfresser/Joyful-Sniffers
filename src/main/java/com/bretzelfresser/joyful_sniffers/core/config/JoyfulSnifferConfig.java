package com.bretzelfresser.joyful_sniffers.core.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class JoyfulSnifferConfig {

    public static final ForgeConfigSpec.Builder BUILDER;
    public static final ForgeConfigSpec.DoubleValue EGG_HATCH_MULTIPLIER;
    public static final ForgeConfigSpec.IntValue TICKS_FOR_ALGEA, TICKS_OVERGROW;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("Sniffer");
        builder.comment("this is a multiplier on how fast Sniffer Eggs will hatch");
        EGG_HATCH_MULTIPLIER = builder.defineInRange("egg hatch multiplier", 1d, 0d, 20d);
        builder.comment("this defines how much ticks it will need for a sniffer to grow algaes on its back");
        TICKS_FOR_ALGEA = builder.defineInRange("ticks algae", 24000, 100, Integer.MAX_VALUE);
        builder.comment("this defines how much ticks it takes for a sniffer with algae on its back to overgrow");
        TICKS_OVERGROW = builder.defineInRange("ticks to overgrow", 72000, 100, Integer.MAX_VALUE);
        builder.pop();
        BUILDER = builder;
    }
}
