package dev.theatricalmod.theatrical.api;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public interface ISupport {

    Direction getBlockPlacementDirection(BlockView world, BlockPos pos, Direction facing);

    float[] getLightTransforms(World world, BlockPos pos, Direction facing);

}
