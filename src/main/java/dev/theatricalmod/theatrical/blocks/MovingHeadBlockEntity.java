package dev.theatricalmod.theatrical.blocks;

import dev.theatricalmod.theatrical.registry.TheatricalBlockEntities;
import dev.theatricalmod.theatrical.utils.MovingHeadLight;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Tickable;
import net.minecraft.world.World;
import therealfarfetchd.illuminate.client.api.Lights;

public class MovingHeadBlockEntity extends BlockEntity implements Tickable {
    private float tilt, pan, intensity;

    private int ticks = 0;

    public MovingHeadBlockEntity() {
        super(TheatricalBlockEntities.MOVING_HEAD);
        tilt = 90F;
        pan = 0F;
    }

    public float getTilt() {
        return tilt;
    }

    public float getPan() {
        return pan;
    }

    public float getIntensity() {
        return intensity;
    }

    public float[] getPanRotationPosition() {
        return new float[]{0.5F, -.5F, -.5F};
    }

    public float[] getTiltRotationPosition() {
        return new float[]{0.5F, -.6F, -.5F};
    }

    @Override
    public void tick() {
            ticks++;
            if(ticks > 1){
                ticks = 0;
                pan++;
//                tilt++;
            }
    }
}
