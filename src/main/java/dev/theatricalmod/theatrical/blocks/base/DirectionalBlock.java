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

package dev.theatricalmod.theatrical.blocks.base;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.Direction;

public class DirectionalBlock extends BaseBlock {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    public DirectionalBlock(FabricBlockSettings settings, ItemGroup itemGroup) {
        super(settings, itemGroup);
        BlockState blockState = this.stateFactory.getDefaultState().with(FACING, Direction.NORTH);
        this.setDefaultState(blockState);
    }

    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING);
    }

    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
        return (BlockState)this.getDefaultState().with(FACING, itemPlacementContext_1.getPlayerFacing().getOpposite());
    }
}
