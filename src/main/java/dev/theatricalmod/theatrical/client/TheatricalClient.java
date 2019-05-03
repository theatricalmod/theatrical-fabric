package dev.theatricalmod.theatrical.client;

import dev.theatricalmod.theatrical.blocks.MovingHeadBlockEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.BlockEntityRendererRegistry;

    public class TheatricalClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.INSTANCE.register(MovingHeadBlockEntity.class, new RenderMovingHead());
    }
}
