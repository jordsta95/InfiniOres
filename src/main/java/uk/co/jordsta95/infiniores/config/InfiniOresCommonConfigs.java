package uk.co.jordsta95.infiniores.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;

public class InfiniOresCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> INFINIORE_MAX_TIER;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> INFINIORE_ORES;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> INFINIORE_ORE_WEIGHTS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> INFINIORE_ORE_REMOVE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFINIORES_PILLARS;
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFINIORES_NOTIFY;

    static{
        BUILDER.push("InfiniOres Config");
        INFINIORE_MAX_TIER = BUILDER.comment("What is the maximum tier (spread distance) an infinite ore vein should be?")
                .define("Maximum blocks from origin", 10);
        INFINIORE_ORES = BUILDER.comment("Which ores to have infinite ore spawners?")
                .defineList("Block IDs for InifiniOre Spawner to use", Arrays.asList("minecraft:iron_ore","minecraft:gold_ore","minecraft:diamond_ore", "minecraft:emerald_ore", "minecraft:coal_ore", "minecraft:copper_ore", "minecraft:lapis_ore", "minecraft:redstone_ore"), entry -> true);
        INFINIORE_ORE_WEIGHTS = BUILDER.comment("Weight of Ore Spawner using each ore; higher number = more likely to be chosen. Lower weight will also affect respawn rates for ores")
                .defineList("Ore weighting", Arrays.asList("10","5","1","1","15", "10", "2", "3"), entry -> true);

        BUILDER.comment("---");
        BUILDER.comment("Default ores: [\"minecraft:iron_ore\",\"minecraft:gold_ore\",\"minecraft:diamond_ore\", \"minecraft:emerald_ore\", \"minecraft:coal_ore\", \"minecraft:copper_ore\", \"minecraft:lapis_ore\", \"minecraft:redstone_ore\"]");
        BUILDER.comment("Default weights: [\"10\",\"5\",\"1\",\"1\",\"15\", \"10\", \"2\", \"3\"]");
        BUILDER.comment("Default functionality:");
        BUILDER.comment("The chance for the ore spawner to choose Iron Ore is 10/47, compared to Gold's 5/47, Diamond and Emeralds' 1/47, Coal's 15/47, etc. This is because the total of all ore weights is 32.");
        BUILDER.comment("The ore spawner will check every tick if a random number between 1 & 100 (inclusive) is less than or equal to the ore's weight. (e.g. with the default config options, there is a 10% chance for Iron Ore)");
        BUILDER.comment("If it is, it will then have a 50% chance of doing ore spawning behaviour or a 0.01% chance of increasing its tier (the distance that the spawner can spread)");
        BUILDER.comment("If it roles the chance to spawn ore, it will choose a random block that its tier can spread to. If the block is air, it will replace with stone. If it is stone, it will replace with the ore it can spawn.");
        BUILDER.comment("If the chosen block is neither, it will do nothing.");
        BUILDER.comment("As such, if you wish for the ores to respawn faster, use higher weights for all ores.");
        BUILDER.comment("------");

        BUILDER.comment("Which ore veins will be removed from spawning naturally?");
        BUILDER.comment("Ore veins are not the same as ores! ");
        INFINIORE_ORE_REMOVE = BUILDER.comment("If trying to disable an ore from a mod, only use this if the mod doesn't have a config option to disable it.")
                .defineList("Prevent spawning",
                        Arrays.asList(
                                "minecraft:ore_iron_small", "minecraft:ore_iron_middle", "minecraft:ore_iron_upper" //Iron
                                ,"minecraft:ore_gold", "minecraft:ore_gold_lower" //Gold
                                ,"minecraft:ore_coal_upper", "minecraft:ore_coal_lower" //Coal
                                ,"minecraft:ore_diamond", "minecraft:ore_diamond_large", "minecraft:ore_diamond_buried" //Diamond
                                , "minecraft:ore_lapis", "minecraft:ore_lapis_buried" //Lapis
                                , "minecraft:ore_copper", "minecraft:ore_copper_large" //Copper
                                , "minecraft:ore_emerald" //Emerald,
                                , "minecraft:ore_redstone", "minecraft:ore_redstone_lower" //Redstone
                        ),
                        entry -> true);

        INFINIORES_PILLARS = BUILDER.comment("Ore pillars are small structures that will force they way to the surface from the ore spawning block when a chunk generates an ore spawner")
                .define("Should ore pillars spawn?", true);
        INFINIORES_NOTIFY = BUILDER.define("Notify player(s) when a chunk loads with an ore spawner?", true);



        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
