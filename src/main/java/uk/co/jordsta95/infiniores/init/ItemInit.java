package uk.co.jordsta95.infiniores.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import uk.co.jordsta95.infiniores.InfiniOres;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, InfiniOres.MODID);

    public static final RegistryObject<BlockItem> ORE_SPAWNER_ITEM = ITEMS.register("ore_spawner",
            () -> new BlockItem(BlockInit.ORE_SPAWNER.get(),
                    new Item.Properties().rarity(Rarity.EPIC)
            ));
}
