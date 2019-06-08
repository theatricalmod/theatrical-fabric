package dev.theatricalmod.theatrical;

import dev.theatricalmod.theatrical.registry.TheatricalBlockEntities;
import dev.theatricalmod.theatrical.registry.TheatricalBlocks;
import dev.theatricalmod.theatrical.registry.TheatricalColorProviderRegistry;
import dev.theatricalmod.theatrical.registry.TheatricalItems;
import net.fabricmc.api.ModInitializer;

public class Theatrical implements ModInitializer {

    @Override
    public void onInitialize() {
        TheatricalBlocks.init();
        TheatricalBlockEntities.init();
        TheatricalItems.init();
        TheatricalColorProviderRegistry.init();
    }

}
