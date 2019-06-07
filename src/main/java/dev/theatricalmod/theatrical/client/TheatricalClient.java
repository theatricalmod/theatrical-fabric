package dev.theatricalmod.theatrical.client;

import dev.theatricalmod.theatrical.blocks.entity.lights.MovingHeadBlockEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.BlockEntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class TheatricalClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.INSTANCE.register(MovingHeadBlockEntity.class, new RenderMovingHead());
    }

}
