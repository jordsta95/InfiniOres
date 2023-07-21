package uk.co.jordsta95.infiniores.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class InfiniOresCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> INIFINIORE_MAX_TIER;
    public static final ForgeConfigSpec.ConfigValue<String> INIFINIORE_ORE;

    static{
        BUILDER.push("InfiniOres Config");
        INIFINIORE_MAX_TIER = BUILDER.comment("What is the maximum tier (spread distance) an infinite ore vein should be?")
                .define("Maximum blocks from origin", 10);
        INIFINIORE_ORE = BUILDER.comment("Which ore to be infinite?").define("Ore", "minecraft:iron_ore");
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
