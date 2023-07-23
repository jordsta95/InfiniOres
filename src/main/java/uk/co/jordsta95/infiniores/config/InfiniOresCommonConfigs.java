package uk.co.jordsta95.infiniores.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;

public class InfiniOresCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> INIFINIORE_MAX_TIER;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> INIFINIORE_ORES;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> INIFINIORE_ORE_WEIGHTS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> INIFINIORE_ORE_REMOVE;

    static{
        BUILDER.push("InfiniOres Config");
        INIFINIORE_MAX_TIER = BUILDER.comment("What is the maximum tier (spread distance) an infinite ore vein should be?")
                .define("Maximum blocks from origin", 10);
        INIFINIORE_ORES = BUILDER.comment("Which ores to have infinite ore spawners?")
                .defineList("Block IDs for InifiniOre Spawner to use", Arrays.asList("minecraft:iron_ore","minecraft:gold_ore","minecraft:diamond_ore","minecraft:coal_ore"), entry -> true);
        INIFINIORE_ORE_WEIGHTS = BUILDER.comment("Weight of Ore Spawner using each ore; higher number = more likely to be chosen. Lower weight will also affect respawn rates for ores")
                        .defineList("Ore weighting", Arrays.asList("10","5","1","1","15"), entry -> true);

        BUILDER.comment("---");
        BUILDER.comment("Default ores: [\"minecraft:iron_ore\",\"minecraft:gold_ore\",\"minecraft:diamond_ore\",\"minecraft:coal_ore\"]");
        BUILDER.comment("Default weights: [\"10\",\"5\",\"1\",\"1\",\"15\"]");
        BUILDER.comment("Default functionality:");
        BUILDER.comment("The chance for the ore spawner to choose Iron Ore is 10/32, compared to Gold's 5/32, Diamond and Emeralds' 1/32, and Coal's 15/32. This is because the total of all ore weights is 32.");
        BUILDER.comment("The ore spawner will check every tick if a random number between 1 & 100 (inclusive) is less than or equal to the ore's weight. (e.g. with the default config options, there is a 10% chance for Iron Ore)");
        BUILDER.comment("If it is, it will then have a 50% chance of doing ore spawning behaviour or a 0.01% chance of increasing its tier (the distance that the spawner can spread)");
        BUILDER.comment("If it roles the chance to spawn ore, it will choose a random block that its tier can spread to. If the block is air, it will replace with stone. If it is stone, it will replace with the ore it can spawn.");
        BUILDER.comment("If the chosen block is neither, it will do nothing.");
        BUILDER.comment("As such, if you wish for the ores to respawn faster, use higher weights for all ores.");
        BUILDER.comment("------");

        INIFINIORE_ORE_REMOVE = BUILDER.comment("Which ores will be removed from spawning naturally?")
                .defineList("Prevent spawning", Arrays.asList("minecraft:iron_ore","minecraft:gold_ore","minecraft:diamond_ore","minecraft:emerald_ore","minecraft:coal_ore"), entry -> true);



        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
