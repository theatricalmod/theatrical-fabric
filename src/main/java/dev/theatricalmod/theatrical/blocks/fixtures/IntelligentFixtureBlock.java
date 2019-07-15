/*
 * Copyright 2018 Theatrical Team (James Conway (615283) & Stuart (Rushmead)) and it's contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.theatricalmod.theatrical.blocks.fixtures;

import dev.theatricalmod.theatrical.api.fixtures.Fixture;
import dev.theatricalmod.theatrical.blocks.base.BaseBlock;
import dev.theatricalmod.theatrical.blocks.entity.AbstractFixtureBlockEntity;
import dev.theatricalmod.theatrical.blocks.entity.lights.IntelligentFixtureBlockEntity;
import dev.theatricalmod.theatrical.blocks.fixtures.base.HangableBlock;
import dev.theatricalmod.theatrical.blocks.fixtures.base.IHasBlockEntity;
import dev.theatricalmod.theatrical.items.group.TheatricalItemGroups;
import dev.theatricalmod.theatrical.util.FixtureLight;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import therealfarfetchd.illuminate.client.api.Lights;

public class IntelligentFixtureBlock extends HangableBlock implements BlockEntityProvider, IHasBlockEntity {

    private Fixture fixture;

    private final VoxelShape BOUNDING = Block.createCuboidShape(0, 0, 0, 1, 1.1, 1);

    public static final BooleanProperty FLIPPED = BooleanProperty.of("flipped");

    public IntelligentFixtureBlock(Fixture fixture) {
        super(FabricBlockSettings.of(Material.ANVIL), TheatricalItemGroups.LIGHTS_ITEM_GROUP, new Direction[]{Direction.DOWN, Direction.UP});
        this.fixture = fixture;
    }

    @Override
    public boolean onBlockAction(BlockState blockState_1, World world_1, BlockPos blockPos_1, int int_1, int int_2) {
        //Do the open GUI
        return super.onBlockAction(blockState_1, world_1, blockPos_1, int_1, int_2);
    }

//    @Override
//    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state,
//        EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY,
//        float hitZ) {
//        if (!worldIn.isRemote) {
//            if (!playerIn.isSneaking()) {
//                playerIn.openGui(TheatricalMain.instance, GUIID.FIXTURE_MOVING_HEAD.getNid(), worldIn,
//                    pos.getX(), pos.getY(), pos.getZ());
//            }
//        }
//        return super
//            .onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
//    }


    @Override
    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
        return super.getPlacementState(itemPlacementContext_1).with(FLIPPED, itemPlacementContext_1.getPlayerLookDirection() == Direction.UP);
    }


    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateBuilder) {
        super.appendProperties(stateBuilder);
        stateBuilder.add(FLIPPED);
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState_1) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean isOpaque(BlockState blockState_1) {
        return false;
    }

    @Override
    public boolean isSideInvisible(BlockState blockState_1, BlockState blockState_2, Direction direction_1) {
        return false;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityContext entityContext_1) {
        return BOUNDING;
    }

    @Override
    public Class<? extends BlockEntity> getBlockEntity() {
        return IntelligentFixtureBlockEntity.class;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new IntelligentFixtureBlockEntity(fixture);
    }

    @Override
    public void onPlaced(World world_1, BlockPos blockPos_1, BlockState blockState_1, LivingEntity livingEntity_1, ItemStack itemStack_1) {
        if (world_1.getBlockEntity(blockPos_1) instanceof AbstractFixtureBlockEntity) {
            if (world_1.isClient) {
                MinecraftClient.getInstance().execute(() -> Lights.getInstance().add(
                    new FixtureLight((AbstractFixtureBlockEntity) world_1.getBlockEntity(blockPos_1),  this)));
            }
        }
    }
}
