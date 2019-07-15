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

import dev.theatricalmod.theatrical.registry.TheatricalBlocks;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class BaseBlock extends Block {

    private Item item;

    public BaseBlock(FabricBlockSettings settings, ItemGroup itemGroups) {
        super(settings.build());
        if(itemGroups != null){
            this.item = new BlockItem(this, new Item.Settings().group(itemGroups));
        }
    }

    @Override
    public Item asItem() {
        return item;
    }
}
