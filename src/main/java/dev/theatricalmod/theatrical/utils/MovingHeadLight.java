package dev.theatricalmod.theatrical.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import dev.theatricalmod.theatrical.Theatrical;
import dev.theatricalmod.theatrical.blocks.MovingHeadBlockEntity;
import dev.theatricalmod.theatrical.registry.TheatricalBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import therealfarfetchd.illuminate.client.api.Light;
import therealfarfetchd.illuminate.client.api.Lights;
import therealfarfetchd.qcommon.croco.Vec3;

public class MovingHeadLight implements Light {

    private MovingHeadBlockEntity be;
    private float tilt = 0, pan = 0;

    private boolean wait =  true;

    public MovingHeadLight(MovingHeadBlockEntity be) {
        this.be = be;
        tilt = be.getTilt();
        pan = be.getPan();
    }

    public int getTexture(){
        TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
        Identifier id = new Identifier(Constants.MOD_ID, "textures/test.png");
        textureManager.bindTexture(id);
        GlStateManager.bindTexture(0);
        return textureManager.getTexture(id).getGlId();
    }

    @Override
    public therealfarfetchd.qcommon.croco.Vec3 getPos() {
        BlockPos pos = be.getPos();
        return new Vec3(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
    }

    @Override
    public int getTex() {
        return getTexture();
    }


    @Override
    public float getPitch() {
        return 90 + tilt;
    }

    @Override
    public float getYaw() {
        return pan;
    }

    @Override
    public void prepare(float delta) {
        BlockState state = MinecraftClient.getInstance().world.getBlockState(be.getPos());

        if (state.getBlock() != Theatrical.MOVING_HEAD) {
            if (!wait) Lights.getInstance().remove(this);
            return;
        } else {
            wait = false;
        }
        tilt = be.getTilt();
        pan = be.getPan();
    }


    @Override
    public float getAspect() {
        return 768/576F;
    }

    @Override
    public float getFov() {
        return 10F;
    }

    @Override
    public float getNear() {
        return 0.8660254f;
    }

    @Override
    public float getRoll() {
        return 0F;
    }

    @Override
    public float getFar() {
        return 50F;
    }
}
