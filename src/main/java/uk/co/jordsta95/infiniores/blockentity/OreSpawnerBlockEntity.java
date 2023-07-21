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
import net.minecraftforge.registries.ForgeRegistries;
import uk.co.jordsta95.infiniores.InfiniOres;
import uk.co.jordsta95.infiniores.blockentity.util.TickableBlockEntity;
import uk.co.jordsta95.infiniores.init.BlockEntityInit;
import net.minecraft.world.level.Level;


import java.util.Map;
import java.util.Random;

public class OreSpawnerBlockEntity extends BlockEntity implements TickableBlockEntity {
    private int tier = 1;
    private int maxTier = 10;
    private String ore = "minecraft:iron_ore";
    private int weight = 100;
    public OreSpawnerBlockEntity(BlockPos pos, BlockState state){
        super(BlockEntityInit.ORE_SPAWNER_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void load(CompoundTag nbt){
        super.load(nbt);
        CompoundTag infinioresData = nbt.getCompound(InfiniOres.MODID);
        this.tier = infinioresData.getInt("Tier");
        this.ore = infinioresData.getString("Ore");
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        var inifinioresData = new CompoundTag();
        inifinioresData.putInt("Tier", this.tier);
        inifinioresData.putString("Ore", this.ore);
    }

    public int getTier(){
        return this.tier;
    }

    public int incrementTier(){
//        for(Object b : ForgeRegistries.BLOCKS.getValues().toArray()){
//            System.out.println(b.toString());
//        }
//        return 1;
        this.tier++;
        setChanged();
        return this.tier;
    }

    @Override
    public void tick(){
        if(this.level == null || this.level.isClientSide()){
            return;
        }

        int check = (int)(Math.random()*100);
        if(check >= (100 - this.weight)){
            int action = (int)(Math.random()*10000);
            if(action >= 9999){ //0.01% chance of tier growing
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
            if(name == "stone"){
                this.level.setBlock(pos, getBlockStateByID(this.ore), 2);
            }

            System.out.println(name.toString());
        }
    }

    public static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        // Add 1 to the range to make the maximum value inclusive
        return random.nextInt(max - min + 1) + min;
    }

    public static BlockState getBlockStateByID(String id){
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(id)).defaultBlockState();
    }

//    public static BlockState createBlockState(String blockId){
//        Identifier identifier = new Identifier(blockId);
//        Block block = Registry.BLOCK.get(identifier);
//
//        if (block != null) {
//            return block.getDefaultState();
//        } else {
//            return null;
//        }
//    }

}
