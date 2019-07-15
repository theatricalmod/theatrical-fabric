package dev.theatricalmod.theatrical.blocks.base;

import dev.theatricalmod.theatrical.api.fixtures.IFixture;
import dev.theatricalmod.theatrical.blocks.entity.IlluminatorBlockEntity;
import dev.theatricalmod.theatrical.blocks.fixtures.base.IHasBlockEntity;
import dev.theatricalmod.theatrical.items.group.TheatricalItemGroups;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class IlluminatorBlock extends BaseBlock implements BlockEntityProvider, IHasBlockEntity {

    private final VoxelShape EMPTY = Block.createCuboidShape(0, 0, 0, 0, 0, 0);

    public static IntProperty LIGHT = IntProperty.of("light", 0, 15);

    public IlluminatorBlock() {
        super(FabricBlockSettings.of(Material.AIR).lightLevel(15).strength(-1F, 3600000).dropsNothing().noCollision(), null);
        this.setDefaultState(this.getStateFactory().getDefaultState().with(LIGHT, 0));
    }

    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(LIGHT);
    }


    @Override
    public VoxelShape getCollisionShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityContext entityContext_1) {
        return EMPTY;
    }

    @Override
    public boolean isOpaque(BlockState blockState_1) {
        return true;
    }

    @Override
    public boolean isSimpleFullBlock(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return false;
    }

    @Override
    public boolean canReplace(BlockState blockState_1, ItemPlacementContext itemPlacementContext_1) {
        return true;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new IlluminatorBlockEntity();
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState_1) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public Class<? extends BlockEntity> getBlockEntity() {
        return IlluminatorBlockEntity.class;
    }

    @Override
    public int getLuminance(BlockState blockState_1) {
        return blockState_1.contains(LIGHT) ? blockState_1.get(LIGHT) : 0;
    }
}
