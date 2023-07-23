package uk.co.jordsta95.infiniores.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import uk.co.jordsta95.infiniores.blockentity.OreSpawnerBlockEntity;
import uk.co.jordsta95.infiniores.blockentity.util.TickableBlockEntity;
import uk.co.jordsta95.infiniores.init.BlockEntityInit;
import javax.annotation.Nullable;

public class OreSpawnerBlock extends Block implements EntityBlock {

    public OreSpawnerBlock(Properties properties){
        super(properties);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state){
        BlockEntity entity = BlockEntityInit.ORE_SPAWNER_BLOCK_ENTITY.get().create(pos, state);
        if(entity instanceof OreSpawnerBlockEntity blockEntity) {
            blockEntity.generateData();
        }
        return entity;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return TickableBlockEntity.getTickerHelper(level);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND){
            BlockEntity e = level.getBlockEntity(pos);
            if(e instanceof OreSpawnerBlockEntity blockEntity){
                blockEntity.incrementTier();
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }
        return super.use(state, level, pos, player, hand, hit);
    }
}
