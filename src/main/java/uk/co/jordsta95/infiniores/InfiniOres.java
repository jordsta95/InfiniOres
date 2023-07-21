package uk.co.jordsta95.infiniores;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import uk.co.jordsta95.infiniores.config.InfiniOresCommonConfigs;
import uk.co.jordsta95.infiniores.init.BlockEntityInit;
import uk.co.jordsta95.infiniores.init.BlockInit;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import uk.co.jordsta95.infiniores.init.ItemInit;

import java.util.HashMap;
import java.util.Map;

@Mod(InfiniOres.MODID)
public class InfiniOres {
    public static final String MODID = "infiniores";
    public static Map blockList;

    public InfiniOres() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        BlockEntityInit.BLOCK_ENTITIES.register(bus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, InfiniOresCommonConfigs.SPEC, "inifiniores.toml");

        makeOreList();
    }

    private void makeOreList(){
//        Map<String, Object> kv = new HashMap<>();
//
//        kv.put("minecraft:stone", ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft:stone")));
//        //kv.put(InfiniOresCommonConfigs.INIFINIORE_ORE.toString(), ForgeRegistries.BLOCKS.getValue(new ResourceLocation(InfiniOresCommonConfigs.INIFINIORE_ORE.toString())));
//
//        System.out.println("FINDME");
//        System.out.println(kv);
//
//        this.blockList = kv;
    }

    public Map getBlockList(){
        return this.blockList;
    }
}
