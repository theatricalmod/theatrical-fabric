package dev.theatricalmod.theatrical.api.fixtures;

import dev.theatricalmod.theatrical.api.ChannelsDefinition;
import dev.theatricalmod.theatrical.util.Constants;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import org.jetbrains.annotations.Nullable;

public class Fixture {

    private static Registry<Fixture> REGISTRY;

    static {
        Identifier registryName = new Identifier(Constants.MOD_ID, "fixtures");
      //  REGISTRY = Registry.register(Registry.REGISTRIES, registryName, new SimpleRegistry<>());
    }

    public static Registry<Fixture> getRegistry() {
        return REGISTRY;
    }


    private Identifier name;
    private FixtureType fixtureType;
    private HangableType hangableType;
    private Identifier staticModelLocation;
    private Identifier hookedModelLocation;
    private Identifier tiltModelLocation;
    private Identifier panModelLocation;
    private float[] tiltRotationPosition;
    private float[] panRotationPosition;
    private float[] beamStartPosition;
    private float defaultRotation;
    private float beamWidth;
    private float rayTraceRotation;
    private float maxLightDistance;
    private int maxEnergy;
    private int energyUse;
    private int energyUseTimer;
    private int channelCount;
    private ChannelsDefinition channelsDefinition;

    private BakedModel staticModel;
    private BakedModel hookedModel;
    private BakedModel tiltModel;
    private BakedModel panModel;

    /**
     * An instance of a fixture
     *
     * @param name                 Name of Fixture
     * @param fixtureType          The Type of Fixture
     * @param hangableType         How the fixture hangs
     * @param staticModelLocation  The location of the static model
     * @param hookedModelLocation  The location of the hooked model (null if {@link dev.theatricalmod.theatrical.api.fixtures.HangableType.NONE})
     * @param tiltModelLocation    The location of the model that tilts
     * @param panModelLocation     The Location of the model that pans
     * @param tiltRotationPosition The middle of the tilt rotation area
     * @param panRotationPosition  The middle of the pan rotation area
     * @param beamStartPosition    The location of where the beam starts
     * @param defaultRotation      The default rotation of the model
     * @param beamWidth            The width of the beam
     * @param rayTraceRotation     Any extra raytracing rotation
     */
    public Fixture(
            Identifier name, FixtureType fixtureType, HangableType hangableType, Identifier staticModelLocation,
            @Nullable Identifier hookedModelLocation, Identifier tiltModelLocation, Identifier panModelLocation, float[] tiltRotationPosition,
            float[] panRotationPosition, float[] beamStartPosition, float defaultRotation, float beamWidth,
            float rayTraceRotation, float maxLightDistance, int maxEnergy, int energyUse, int energyUseTimer,
            int channelCount, ChannelsDefinition channelsDefinition
    ) {
        this.name = name;
        this.fixtureType = fixtureType;
        this.hangableType = hangableType;
        this.staticModelLocation = staticModelLocation;
        if (this.hookedModelLocation != null) // nullable for fixtures with hangingtype NONE
            this.hookedModelLocation = hookedModelLocation;
        this.tiltModelLocation = tiltModelLocation;
        this.panModelLocation = panModelLocation;
        this.tiltRotationPosition = tiltRotationPosition;
        this.panRotationPosition = panRotationPosition;
        this.beamStartPosition = beamStartPosition;
        this.defaultRotation = defaultRotation;
        this.beamWidth = beamWidth;
        this.rayTraceRotation = rayTraceRotation;
        this.maxLightDistance = maxLightDistance;
        this.energyUse = energyUse;
        this.energyUseTimer = energyUseTimer;
        this.channelCount = channelCount;
        this.channelsDefinition = channelsDefinition;
        this.maxEnergy = maxEnergy;
    }

    public Identifier getName() {
        return name;
    }

    public HangableType getHangableType() {
        return hangableType;
    }

    public Identifier getStaticModelLocation() {
        return staticModelLocation;
    }

    public Identifier getTiltModelLocation() {
        return tiltModelLocation;
    }

    public Identifier getPanModelLocation() {
        return panModelLocation;
    }

    public Identifier getHookedModelLocation() {
        return hookedModelLocation;
    }

    public float[] getTiltRotationPosition() {
        return tiltRotationPosition;
    }

    public float[] getPanRotationPosition() {
        return panRotationPosition;
    }

    public float[] getBeamStartPosition() {
        return beamStartPosition;
    }

    public float getDefaultRotation() {
        return defaultRotation;
    }

    public float getBeamWidth() {
        return beamWidth;
    }

    public float getRayTraceRotation() {
        return rayTraceRotation;
    }

    public FixtureType getFixtureType() {
        return fixtureType;
    }

    public BakedModel getStaticModel() {
        return staticModel;
    }

    public void setStaticModel(BakedModel staticModel) {
        this.staticModel = staticModel;
    }

    public BakedModel getTiltModel() {
        return tiltModel;
    }

    public void setTiltModel(BakedModel tiltModel) {
        this.tiltModel = tiltModel;
    }

    public BakedModel getPanModel() {
        return panModel;
    }

    public void setPanModel(BakedModel panModel) {
        this.panModel = panModel;
    }

    public BakedModel getHookedModel() {
        return hookedModel;
    }

    public void setHookedModel(BakedModel hookedModel) {
        this.hookedModel = hookedModel;
    }

    public float getMaxLightDistance() {
        return maxLightDistance;
    }

    public int getEnergyUse() {
        return energyUse;
    }

    public int getEnergyUseTimer() {
        return energyUseTimer;
    }

    public int getChannelCount() {
        return channelCount;
    }

    public ChannelsDefinition getChannelsDefinition() {
        return channelsDefinition;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

}
