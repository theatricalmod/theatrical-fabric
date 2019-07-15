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
import dev.theatricalmod.theatrical.blocks.entity.lights.TungstenFixtureBlockEntity;
import dev.theatricalmod.theatrical.blocks.fixtures.base.HangableBlock;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class TungstenLightBlock extends HangableBlock implements BlockEntityProvider, IHasBlockEntity {


    private final VoxelShape BOUNDING = Block.createCuboidShape(0, 0, 0, 1, 1.1, 1);
    private Fixture fixture;

    public TungstenLightBlock(Fixture fixture) {
        super(FabricBlockSettings.of(Material.ANVIL), TheatricalItemGroups.LIGHTS_ITEM_GROUP, new Direction[]{Direction.DOWN, Direction.UP});
        this.fixture = fixture;
    }

//    @Nullable
//    @Override
//    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
//        TileFixture fixture = this.fixture.getFixtureType().getTileClass().get();
//        fixture.setFixture(this.fixture);
//        return fixture;
//    }
//
//    @Override
//    public Class<? extends TileEntity> getTileEntity() {
//        return fixture.getFixtureType().getTileClass().get().getClass();
//    }

//    @Override
//    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state,
//        EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY,
//        float hitZ) {
//        if (!worldIn.isRemote) {
//            if (!playerIn.isSneaking()) {
//                playerIn.openGui(TheatricalMain.instance, GUIID.FIXTURE_FRESNEL.getNid(), worldIn,
//                    pos.getX(), pos.getY(), pos.getZ());
//            }
//        }
//        return super
//            .onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
//    }
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
        return TungstenFixtureBlockEntity.class;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new TungstenFixtureBlockEntity(fixture);
    }

//    @Override
//    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
//        if (world.getTileEntity(pos) instanceof TileTungstenFixture) {
//            TileTungstenFixture tileFresnel = (TileTungstenFixture) world.getTileEntity(pos);
//            if (tileFresnel != null) {
//                return (int) (tileFresnel.getIntensity() * 4 / 255);
//            }
//        }
//        return super.getLightValue(state, world, pos);
//    }

}
