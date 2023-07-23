package uk.co.jordsta95.infiniores.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.registries.ForgeRegistries;
import uk.co.jordsta95.infiniores.InfiniOres;
import uk.co.jordsta95.infiniores.blockentity.util.TickableBlockEntity;
import uk.co.jordsta95.infiniores.config.InfiniOresCommonConfigs;
import uk.co.jordsta95.infiniores.init.BlockEntityInit;
import net.minecraft.world.level.Level;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static uk.co.jordsta95.infiniores.config.InfiniOresCommonConfigs.*;

public class OreSpawnerBlockEntity extends BlockEntity implements TickableBlockEntity {
    private int tier = 0;
    private int maxTier = InfiniOresCommonConfigs.INIFINIORE_MAX_TIER.get();
    private String ore = "minecraft:stone";
    private int weight = 100;
    public OreSpawnerBlockEntity(BlockPos pos, BlockState state){
        super(BlockEntityInit.ORE_SPAWNER_BLOCK_ENTITY.get(), pos, state);
    }

    private static final Random random = new Random();



    @Override
    public void load(CompoundTag nbt){
        super.load(nbt);
        CompoundTag infinioresData = nbt.getCompound(InfiniOres.MODID);
        this.tier = infinioresData.getInt("Tier");
        this.ore = infinioresData.getString("Ore");
        this.weight = infinioresData.getInt("Weight");
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        var infinioresData = new CompoundTag();
        infinioresData.putInt("Tier", this.tier);
        infinioresData.putString("Ore", this.ore);
        infinioresData.putInt("Weight", this.weight);
    }

    public int getTier(){
        return this.tier;
    }

    public int incrementTier(){
//        for(Object b : ForgeRegistries.BLOCKS.getValues().toArray()){
//            System.out.println(b.toString());
//        }
//        return 1;
//        this.tier++;
//        setChanged();
        System.out.println(this.tier);
        System.out.println(this.ore);
        return this.tier;
    }

    public void generateData(){
        var infinioresData = new CompoundTag();

        if(infinioresData.getInt("Tier") == 0) {
            String[] ores = InfiniOresCommonConfigs.INIFINIORE_ORES.get().stream().toArray(String[]::new);
            String[] weights = InfiniOresCommonConfigs.INIFINIORE_ORE_WEIGHTS.get().stream().toArray(String[]::new);

            String[] oreList = new String[0];

            for (int i = 0; i < ores.length; i++) {
                if (weights.length >= i && weights[i] != null) {
                    for (int n = 0; n < Integer.parseInt(weights[i]); n++) {
                        String[] newOreList = new String[oreList.length + 1];
                        for (int l = 0; l < oreList.length; l++) {
                            newOreList[l] = oreList[l];
                        }
                        newOreList[newOreList.length - 1] = ores[i];
                        oreList = newOreList;
                    }
                }else{ //Not enough weight variables in the config. So just add a weight of 1
                    String[] newOreList = new String[oreList.length + 1];
                    for (int l = 0; l < oreList.length; l++) {
                        newOreList[l] = oreList[l];
                    }
                    newOreList[newOreList.length - 1] = ores[i];
                    oreList = newOreList;
                }
            }

            int num = generateRandomNumber(1, oreList.length);
            String ore = oreList[num - 1]; //List is 0 indexed, num is 1-MAX; -1 to if final number is chosen
            int weight = 1; //Fallback as weight not set in config
            if (weights.length >= num && weights[num] != null) {
                weight = Integer.parseInt(weights[num]);
            }
            this.tier = 1;
            this.ore = ore;
            this.weight = weight;
            infinioresData.putInt("Tier", 1);
            infinioresData.putString("Ore", ore);
            infinioresData.putInt("Weight", weight);
            setChanged();
        }
    }

    @Override
    public void tick(){
        if(this.level == null || this.level.isClientSide()){
            return;
        }

        int check = (int)(Math.random()*100);
        if(check <= this.weight){ //If the weight of the ore is low it is meant to be rarer, so shouldn't respawn as quickly
            int action = (int)(Math.random()*10000);
            if(action > 9999){ //0.01% chance of tier growing
                if(this.tier < this.maxTier) {
                    this.tier++;
                }
            } else if (action >= 1 && action <= 5000) { //50% chance of orespawn behaviour
                updateBlock();
            }
            //Do nothing

        }
    }

    private void updateBlock(){
        //Get position
        int x = this.getBlockPos().getX();
        int y = this.getBlockPos().getY();
        int z = this.getBlockPos().getZ();

        //Get bounding box
        int minX = x - this.tier;
        int maxX = x + this.tier;
        int minY = y - this.tier;
        int maxY = y + this.tier;
        int minZ = z - this.tier;
        int maxZ = z + this.tier;

        int xUpdate = generateRandomNumber(minX, maxX);
        int yUpdate = generateRandomNumber(minY, maxY);
        int zUpdate = generateRandomNumber(minZ, maxZ);

        if(x != xUpdate && y != yUpdate && z != zUpdate) {
            BlockPos pos = new BlockPos(xUpdate, yUpdate, zUpdate);
            BlockState rep = this.level.getBlockState(pos);
            String name = rep.getBlock().asItem().toString();


            if(name == "air"){
                this.level.setBlock(pos, getBlockStateByID("minecraft:stone"), 2);
            }
            if(name == "stone") {
                this.level.setBlock(pos, getBlockStateByID(this.ore), 2);
            }
        }
    }

    public static int generateRandomNumber(int min, int max) {
        // Add 1 to the range to make the maximum value inclusive
        return random.nextInt(max - min + 1) + min;
    }

    public static BlockState getBlockStateByID(String id){
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(id)).defaultBlockState();
    }

}
