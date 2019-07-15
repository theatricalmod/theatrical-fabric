package dev.theatricalmod.theatrical.client;

import dev.theatricalmod.theatrical.Theatrical;
import dev.theatricalmod.theatrical.api.fixtures.Fixture;
import dev.theatricalmod.theatrical.blocks.entity.AbstractFixtureBlockEntity;
import dev.theatricalmod.theatrical.registry.TheatricalFixtures;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.render.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.fabricmc.fabric.impl.client.model.ModelLoadingRegistryImpl;
import net.minecraft.client.util.ModelIdentifier;

@Environment(EnvType.CLIENT)
public class TheatricalClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.INSTANCE.register(AbstractFixtureBlockEntity.class, new RenderFixture());
        ModelLoadingRegistry.INSTANCE.registerAppender((manager, out) -> Fixture.getRegistry().forEach(fixture -> {
            Theatrical.instance.logger.info("Adding models for fixture: " + fixture.getName().getPath());
            if(fixture.getHookedModelLocation() != null){
                out.accept(fixture.getHookedModelLocation());
            }
            out.accept(fixture.getPanModelLocation());
            out.accept(fixture.getStaticModelLocation());
            out.accept(fixture.getTiltModelLocation());
        }));
    }

}
