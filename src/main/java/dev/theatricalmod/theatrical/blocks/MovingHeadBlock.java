package dev.theatricalmod.theatrical.blocks;

import dev.theatricalmod.theatrical.utils.MovingHeadLight;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import therealfarfetchd.illuminate.client.api.Lights;

public class MovingHeadBlock extends Block implements BlockEntityProvider {

    public MovingHeadBlock() {
        super(Settings.of(Material.STONE));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView var1) {
        return new MovingHeadBlockEntity();
    }

    @Override
    public boolean isSimpleFullBlock(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return false;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState_1) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onPlaced(World world_1, BlockPos blockPos_1, BlockState blockState_1, LivingEntity livingEntity_1, ItemStack itemStack_1) {
        if(world_1.getBlockEntity(blockPos_1) != null){
            if(world_1.isClient){
                MinecraftClient.getInstance().execute(() -> Lights.getInstance().add(new MovingHeadLight((MovingHeadBlockEntity) world_1.getBlockEntity(blockPos_1))));
            }
        }
    }
}
