package uk.co.jordsta95.infiniores.events;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uk.co.jordsta95.infiniores.InfiniOres;
import uk.co.jordsta95.infiniores.config.InfiniOresCommonConfigs;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = InfiniOres.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerForgeEvents {
    // listen for when the server starts so that we can add biome modifiers
    @SubscribeEvent
    public static void serverAboutToStart(ServerAboutToStartEvent event) {
        MinecraftServer server = event.getServer();

        RegistryAccess registries = server.registryAccess();

        // ideally you would be able to specify this list somewhere. but for now, this just adds all the biomes that are registered
        List<Holder<Biome>> biomesHolderList = new ArrayList<>();
        registries.registryOrThrow(Registries.BIOME).holders().forEach(biomesHolderList::add);
        HolderSet<Biome> biomesHolderSet = HolderSet.direct(biomesHolderList);

        Set<GenerationStep.Decoration> steps = Set.of(GenerationStep.Decoration.UNDERGROUND_ORES);

        // loop through all the features defined in the config, grab them as Holder<PlacedFeature> then convert to BiomeModifier
        final List<BiomeModifier> biomeModifiers = new ArrayList<>();
        for (String toRemove : InfiniOresCommonConfigs.INIFINIORE_ORE_REMOVE.get()) {
            var toRemoveLocation = new ResourceLocation(toRemove);
            Holder<PlacedFeature> placedFeatureHolder = registries.registryOrThrow(Registries.PLACED_FEATURE).getHolder(ResourceKey.create(Registries.PLACED_FEATURE, toRemoveLocation)).orElse(null);
            if (placedFeatureHolder == null) {
                System.out.println("Could not find feature " + toRemoveLocation + " to remove");
                continue;
            }

            HolderSet<PlacedFeature> placedFeatureHolderSet = HolderSet.direct(placedFeatureHolder);
            biomeModifiers.add(new ForgeBiomeModifiers.RemoveFeaturesBiomeModifier(biomesHolderSet, placedFeatureHolderSet, steps));
        }

        // for each biome holder, add the biome modifiers
        registries.registryOrThrow(Registries.BIOME).holders().forEach(biomeHolder -> {
            try {
                ModifiableBiomeInfo modifiableBiomeInfo = biomeHolder.value().modifiableBiomeInfo();
                ModifiableBiomeInfo.BiomeInfo biomeInfo = modifiableBiomeInfo.getModifiedBiomeInfo();

                // apply if it has not been modified before
                if (biomeInfo == null) {
                    modifiableBiomeInfo.applyBiomeModifiers(biomeHolder, biomeModifiers);
                } else { // use reflection to copy the current modified and re-modify it
                    ModifiableBiomeInfo.BiomeInfo original = modifiableBiomeInfo.getOriginalBiomeInfo();
                    final ModifiableBiomeInfo.BiomeInfo.Builder builder = ModifiableBiomeInfo.BiomeInfo.Builder.copyOf(original);
                    for (BiomeModifier.Phase phase : BiomeModifier.Phase.values()) {
                        for (BiomeModifier modifier : biomeModifiers) {
                            modifier.modify(biomeHolder, phase, builder);
                        }
                    }

                    Field modifiedBiomeInfoField = ModifiableBiomeInfo.class.getDeclaredField("modifiedBiomeInfo");
                    modifiedBiomeInfoField.setAccessible(true);
                    modifiedBiomeInfoField.set(modifiableBiomeInfo, builder.build());
                    modifiedBiomeInfoField.setAccessible(false);
                }
            } catch (IllegalStateException | NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }
        });
    }
}