package uk.co.jordsta95.infiniores.init;

import uk.co.jordsta95.infiniores.InfiniOres;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import uk.co.jordsta95.infiniores.block.OreSpawnerBlock;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, InfiniOres.MODID);

    public static final RegistryObject<OreSpawnerBlock> ORE_SPAWNER = BLOCKS.register("ore_spawner",
            () -> new OreSpawnerBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(999.0f, 999.0f)
                    .destroyTime(-1)
                    .lightLevel(state -> 1)
            ));

}
