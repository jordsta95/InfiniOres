package uk.co.jordsta95.infiniores.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import uk.co.jordsta95.infiniores.InfiniOres;
import uk.co.jordsta95.infiniores.blockentity.OreSpawnerBlockEntity;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, InfiniOres.MODID);

    public static final RegistryObject<BlockEntityType<OreSpawnerBlockEntity>> ORE_SPAWNER_BLOCK_ENTITY = BLOCK_ENTITIES.register("ore_spawner_block_entity",
            () -> BlockEntityType.Builder.of(OreSpawnerBlockEntity::new, BlockInit.ORE_SPAWNER.get()).build(null));
}
