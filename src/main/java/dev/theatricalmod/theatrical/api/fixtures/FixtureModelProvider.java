package dev.theatricalmod.theatrical.api.fixtures;

import net.minecraft.client.render.model.BakedModel;

public interface IFixtureModelProvider {

    HangableType getHangType();

    BakedModel getStaticModel();

    BakedModel getTiltModel();

    BakedModel getPanModel();

    float[] getTiltRotationPosition();

    float[] getPanRotationPosition();

    float getDefaultRotation();

    float[] getBeamStartPosition();

    float getBeamWidth();

    float getRayTraceRotation();

}
