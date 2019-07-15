package dev.theatricalmod.theatrical;

import dev.theatricalmod.theatrical.api.fixtures.Fixture;
import dev.theatricalmod.theatrical.registry.TheatricalBlockEntities;
import dev.theatricalmod.theatrical.registry.TheatricalBlocks;
import dev.theatricalmod.theatrical.registry.TheatricalColorProviderRegistry;
import dev.theatricalmod.theatrical.registry.TheatricalFixtures;
import dev.theatricalmod.theatrical.registry.TheatricalItems;
import dev.theatricalmod.theatrical.util.Constants;
import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.fabric.impl.resources.ResourceManagerHelperImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Theatrical implements ModInitializer {

    public static Theatrical instance;


    public Logger logger = LogManager.getLogger();
    public SoundEvent TEST_SOUND;
    public File fixturesDirectory;

    @Override
    public void onInitialize() {
        instance = this;
        fixturesDirectory = new File(FabricLoader.getInstance().getConfigDirectory(), "theatrical/fixtures");
        if(!fixturesDirectory.exists()){
            fixturesDirectory.mkdir();
        }
        ResourceManagerHelperImpl.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return new Identifier("theatrical:lights");
            }

            @Override
            public Collection<Identifier> getFabricDependencies() {
                return Collections.singletonList(ResourceReloadListenerKeys.MODELS);
            }

            @Override
            public void apply(ResourceManager var1) {
                BakedModelManager bakedModelManager = MinecraftClient.getInstance().getBakedModelManager();

                Fixture.getRegistry().forEach(fixture -> {
                    if(fixture.getHookedModelLocation() != null){
                        fixture.setHookedModel(bakedModelManager.getModel(fixture.getHookedModelLocation()));
                    }
                    fixture.setTiltModel(bakedModelManager.getModel(fixture.getTiltModelLocation()));
                    fixture.setStaticModel(bakedModelManager.getModel(fixture.getStaticModelLocation()));
                    fixture.setPanModel(bakedModelManager.getModel(fixture.getPanModelLocation()));
                });
                TheatricalBlocks.init();
                TheatricalBlockEntities.init();
            }
        });
//        if(FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT){
//            TheatricalFixtures.initServer();
//            TheatricalFixtures.register();
//            TheatricalBlocks.init();
//            TheatricalBlockEntities.init();
//        }
        TheatricalItems.init();
        TheatricalColorProviderRegistry.init();

        TEST_SOUND = Registry.register(Registry.SOUND_EVENT, new Identifier(Constants.MOD_ID, "test"),
                new SoundEvent(new Identifier(Constants.MOD_ID, "test")));
    }

}
