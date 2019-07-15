package dev.theatricalmod.theatrical.registry;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.theatricalmod.theatrical.Theatrical;
import dev.theatricalmod.theatrical.api.fixtures.CustomFixture;
import dev.theatricalmod.theatrical.api.fixtures.Fixture;
import dev.theatricalmod.theatrical.fixtures.FixtureMovingHead;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TheatricalFixtures {

    public static Gson gson = new Gson();

    //    public static Fixture FRESNEL = new FixtureFresnel();
    public static Fixture MOVING_HEAD = new FixtureMovingHead();
    public static List<Fixture> fixtures;
    public static boolean hasLoaded = false;

    public static void register(){
        Registry.register(Fixture.getRegistry(), MOVING_HEAD.getName(), MOVING_HEAD);
        fixtures.forEach(fixture -> Registry.register(Fixture.getRegistry(),fixture.getName(), fixture));
    }

    public static void init(ResourceManager manager) {
        fixtures = new ArrayList<>();
        List<String> files = new ArrayList<>();
        Identifier identifier = new Identifier("theatrical:lights.json");
        try {
            for (Resource resource : manager.getAllResources(identifier)) {
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse(new InputStreamReader(resource.getInputStream())).getAsJsonObject();
                resource.getInputStream().close();
                if (object.has("lights")) {
                    JsonArray array = object.getAsJsonArray("lights");
                    array.forEach(jsonElement -> files.add(jsonElement.getAsString()));
                }
            }
            files.forEach(s -> {
                try {
                    for (Resource resource : manager.getAllResources(new Identifier("theatrical", "lights/" + s))) {
                        Fixture fixture = fromJson(new InputStreamReader(resource.getInputStream()));
                        fixtures.add(fixture);
                        Theatrical.instance.logger.info("[Theatrical] Loaded light: " + fixture.getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Theatrical.instance.logger.error("[Theatrical] Failed to load light with name " + s);
                }
            });
        } catch (IOException e) {
            Theatrical.instance.logger.error("An error occurred whilst loading lights.");
        }
    }

    public static void initServer() {
        fixtures = TheatricalFixtures.loadFromFiles(Theatrical.instance.fixturesDirectory);
    }

    public static Fixture fromJson(Reader json) {
        CustomFixture fixture = gson.fromJson(json, CustomFixture.class);
        Fixture actualFixture = new Fixture(new Identifier("theatrical", fixture.getName()), fixture.getFixtureType(), fixture.getHangableType(), new ModelIdentifier("theatrical", fixture.getStaticModel()), new ModelIdentifier("theatrical", fixture.getHookedModel()), new ModelIdentifier("theatrical", fixture.getTiltModel()), new ModelIdentifier("theatrical", fixture.getPanModel()), fixture.getTiltRotation(), fixture.getPanRotation(), fixture.getBeamStartPosition(), fixture.getDefaultRotation(), fixture.getBeamWidth(), fixture.getRayTraceRotation(), fixture.getMaxLightDistance(), fixture.getMaxEnergy(), fixture.getEnergyUse(), fixture.getEnergyUseTimer(), fixture.getChannelCount(), fixture.getChannelsDefinition());
        return actualFixture;
    }

    public static List<Fixture> loadFromFiles(File configDir) {
        List<Fixture> fixtures = new ArrayList<>();
        List<File> fileList = Arrays.asList(configDir.listFiles());
        fileList.forEach(lightFile -> {
            if (!lightFile.getName().endsWith(".json")) {
                return;
            }
            try {
                FileReader fileReader = new FileReader(lightFile);
                Fixture fixture = fromJson(fileReader);
                fixtures.add(fixture);
                Theatrical.instance.logger.info("[Theatrical] Loaded light: " + fixture.getName());
            } catch (FileNotFoundException e) {
                Theatrical.instance.logger.error("[Theatrical] Failed to load light with name " + lightFile.getName());
            }
        });
        return fixtures;
    }

}
