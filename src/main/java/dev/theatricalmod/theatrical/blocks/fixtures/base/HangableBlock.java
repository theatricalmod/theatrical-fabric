package dev.theatricalmod.theatrical.blocks.fixtures.base;

import dev.theatricalmod.theatrical.api.ISupport;
import dev.theatricalmod.theatrical.blocks.base.DirectionalBlock;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlacementEnvironment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class HangableBlock extends DirectionalBlock {

    public Direction[] allowedPlaces;

    public HangableBlock(FabricBlockSettings blockSettings, ItemGroup itemGroup, Direction[] allowedPlaces) {
        super(blockSettings, itemGroup);
        this.allowedPlaces = allowedPlaces;
    }

    @Override
    public boolean canPlaceAtSide(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, BlockPlacementEnvironment blockPlacementEnvironment_1) {
        BlockPos up = blockPos_1.offset(Direction.UP);
        if(blockView_1.getBlockState(up).getBlock() != Blocks.AIR && blockView_1.getBlockState(up).getBlock() instanceof ISupport){
            ISupport support = (ISupport) blockView_1.getBlockState(up).getBlock();
            for(Direction facing : allowedPlaces){
                if (support.getBlockPlacementDirection(blockView_1, blockPos_1, facing).equals(facing)) {
                    return true;
                }
            }
            return false;
        }else{
            return false;
        }
    }

    @Override
    public void neighborUpdate(BlockState blockState, World world, BlockPos pos, Block block, BlockPos pos2, boolean boolean_1) {
        if(!isHanging(world, pos)){
            if(!world.isClient) {
                FallingBlockEntity fallingBlock = new FallingBlockEntity(world, pos.getX(), pos.getY(), pos.getZ(), world.getBlockState(pos));
                fallingBlock.dropItem = false;
                world.spawnEntity(fallingBlock);
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
    }

    public boolean isHanging(World world, BlockPos pos){
        BlockPos up = pos.offset(Direction.UP);
        return world.getBlockState(up).getBlock() instanceof ISupport;
    }

}
