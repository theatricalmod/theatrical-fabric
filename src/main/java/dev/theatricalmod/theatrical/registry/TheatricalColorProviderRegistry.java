package dev.theatricalmod.theatrical.registry;

import dev.theatricalmod.theatrical.items.attr.fixture.gel.GelItemColorProvider;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.item.Item;

public class TheatricalColorProviderRegistry {

    public static final GelItemColorProvider GEL_ITEM_COLOR_PROVIDER = new GelItemColorProvider();

    public static void init() {
        register(GEL_ITEM_COLOR_PROVIDER, TheatricalItems.GEL_ITEM);
    }

    private static void register(ItemColorProvider itemColorProvider, Item... items) {
        ColorProviderRegistry.ITEM.register(itemColorProvider, items);
    }

    private static void register(BlockColorProvider blockColorProvider, Block... blocks) {
        ColorProviderRegistry.BLOCK.register(blockColorProvider, blocks);
    }

}
