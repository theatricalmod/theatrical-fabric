package dev.theatricalmod.theatrical;

import dev.theatricalmod.theatrical.blocks.lights.MovingHeadBlock;
import dev.theatricalmod.theatrical.registry.TheatricalBlockEntities;
import dev.theatricalmod.theatrical.registry.TheatricalBlocks;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Theatrical implements ModInitializer {

    @Override
    public void onInitialize() {
        TheatricalBlockEntities.init();
        TheatricalBlocks.init();
    }

}
