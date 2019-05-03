package dev.theatricalmod.theatrical.client;

import com.mojang.blaze3d.platform.GlStateManager;
import dev.theatricalmod.theatrical.blocks.MovingHeadBlockEntity;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import org.lwjgl.opengl.GL11;

public class RenderMovingHead extends BlockEntityRenderer<MovingHeadBlockEntity> {

    private static float lastBrightnessX, lastBrightnessY;

    @Override
    public void render(MovingHeadBlockEntity blockEntity_1, double x, double y, double z, float partial_ticks, int int_1) {
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.translated(x, y, z);
        GlStateManager.normal3f(0F, 1F, 0F);
        GlStateManager.translatef(0F, 1F, 1F);

        float[] pans = blockEntity_1.getPanRotationPosition();
        GlStateManager.translatef(pans[0], pans[1], pans[2]);
        GlStateManager.rotatef(blockEntity_1.getPan(), 0, 1, 0);
        GlStateManager.translatef(-pans[0], -pans[1], -pans[2]);
        float[] tilts = blockEntity_1.getTiltRotationPosition();
        GlStateManager.translatef(tilts[0], tilts[1], tilts[2]);
        GlStateManager.rotatef(blockEntity_1.getTilt(), 1, 0, 0);
        GlStateManager.translatef(-tilts[0], -tilts[1], -tilts[2]);
        super.render(blockEntity_1, x, y, z, partial_ticks, int_1);
        GlStateManager.rotatef(90F, 1F, 0F, 0);
        GlStateManager.translatef(0F, -1.3F, .2F);
        renderLightBeam(blockEntity_1, partial_ticks, 0.4F, 4, 10, 0xFFD7D3, x, y, z);
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }


    public void renderLightBeam(MovingHeadBlockEntity tileFresnel, float partialTicks,
        float alpha, double beamSize, double length, int color, double x, double y, double z) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder render = tessellator.getBufferBuilder();
//        render.setOffset(x, y, z);
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        int a = (int) (alpha * 255);
//        pushMaxBrightness();

        //Open GL Stuff
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(false);
        GlStateManager.translated(0.5, 0.8, 0);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        int func = GL11.glGetInteger(GL11.GL_ALPHA_TEST_FUNC);
        float ref = GL11.glGetFloat(GL11.GL_ALPHA_TEST_REF);
        GlStateManager.alphaFunc(GL11.GL_ALWAYS, 0);
        GlStateManager.enableCull();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.disableTexture();

        render.begin(GL11.GL_QUADS, VertexFormats.POSITION_COLOR);
        double width = beamSize;
        double endMultiplier = width * 4;

        //Do the actual beam vertexes
        render.vertex((width * endMultiplier) / 32D, (width * endMultiplier) / 32D, -length).color(r, g, b, 0)
            .next();
        render.vertex(width / 32D, width / 32D, 0).color(r, g, b, a).next();
        render.vertex(width / 32D, (-width) / 32D, 0).color(r, g, b, a).next();
        render.vertex((width * endMultiplier) / 32D, (-width * endMultiplier) / 32D, -length).color(r, g, b, 0)
            .next();

        render.vertex((-width * endMultiplier) / 32D, (-width * endMultiplier) / 32D, -length).color(r, g, b, 0)
            .next();
        render.vertex((-width) / 32D, (-width) / 32D, 0).color(r, g, b, a).next();
        render.vertex(-width / 32D, width / 32D, 0).color(r, g, b, a).next();
        render.vertex((-width * endMultiplier) / 32D, (width * endMultiplier) / 32D, -length).color(r, g, b, 0)
            .next();

        render.vertex((-width * endMultiplier) / 32D, (width * endMultiplier) / 32D, -length).color(r, g, b, 0)
            .next();
        render.vertex((-width) / 32D, width / 32D, 0).color(r, g, b, a).next();
        render.vertex(width / 32D, width / 32D, 0).color(r, g, b, a).next();
        render.vertex((width * endMultiplier) / 32D, (width * endMultiplier) / 32D, -length).color(r, g, b, 0)
            .next();

        render.vertex((width * endMultiplier) / 32D, (-width * endMultiplier) / 32D, -length).color(r, g, b, 0)
            .next();
        render.vertex(width / 32D, (-width) / 32D, 0).color(r, g, b, a).next();
        render.vertex((-width) / 32D, (-width) / 32D, 0).color(r, g, b, a).next();
        render.vertex((-width * endMultiplier) / 32D, (-width * endMultiplier) / 32D, -length).color(r, g, b, 0)
            .next();
//        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//        render.setOffset(0, 0, 0);
        tessellator.draw();

        //OpenGL Stuff
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.enableTexture();
        GlStateManager.alphaFunc(func, ref);
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO);
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();

//        popBrightness();
    }
}
