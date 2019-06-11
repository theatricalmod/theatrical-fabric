package dev.theatricalmod.theatrical;

import dev.theatricalmod.theatrical.registry.TheatricalBlockEntities;
import dev.theatricalmod.theatrical.registry.TheatricalBlocks;
import dev.theatricalmod.theatrical.registry.TheatricalColorProviderRegistry;
import dev.theatricalmod.theatrical.registry.TheatricalItems;
import dev.theatricalmod.theatrical.util.Constants;
import net.fabricmc.api.ModInitializer;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Theatrical implements ModInitializer {

    public static SoundEvent TEST_SOUND;

    @Override
    public void onInitialize() {
        TheatricalBlocks.init();
        TheatricalBlockEntities.init();
        TheatricalItems.init();
        TheatricalColorProviderRegistry.init();

        TEST_SOUND = Registry.register(Registry.SOUND_EVENT, new Identifier(Constants.MOD_ID, "test"),
                new SoundEvent(new Identifier(Constants.MOD_ID, "test")));
    }

}
