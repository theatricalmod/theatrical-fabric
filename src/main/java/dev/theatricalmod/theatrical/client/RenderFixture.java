package dev.theatricalmod.theatrical.client;

import com.mojang.blaze3d.platform.GlStateManager;
import dev.theatricalmod.theatrical.api.ISupport;
import dev.theatricalmod.theatrical.api.fixtures.HangableType;
import dev.theatricalmod.theatrical.blocks.base.DirectionalBlock;
import dev.theatricalmod.theatrical.blocks.entity.AbstractFixtureBlockEntity;
import dev.theatricalmod.theatrical.blocks.fixtures.IntelligentFixtureBlock;
import dev.theatricalmod.theatrical.blocks.fixtures.base.HangableBlock;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.GlAllocationUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.lwjgl.opengl.GL11;

public class RenderFixture extends BlockEntityRenderer<AbstractFixtureBlockEntity> {

    private static float lastBrightnessX, lastBrightnessY;
    private BlockModelRenderer blockModelRenderer;
    private int hookDisplayList = -1;
    private int lightHandleList = -1;
    private int lightBodyList = -1;

    @Override
    public void render(AbstractFixtureBlockEntity blockEntity_1, double x, double y, double z, float partial_ticks, int int_1) {
        if (blockModelRenderer == null) {
            blockModelRenderer = MinecraftClient.getInstance().getBlockRenderManager().getModelRenderer();
        }
        if(blockEntity_1.getFixture() == null){
            return;
        }
        BlockState state = blockEntity_1.getWorld().getBlockState(blockEntity_1.getPos());
        if (!(state.getBlock() instanceof DirectionalBlock)) {
            return;
        }
        boolean isFlipped = false;
        if (blockEntity_1.getBlock() == IntelligentFixtureBlock.class) {
            isFlipped = state.get(IntelligentFixtureBlock.FLIPPED);
        }
        boolean isHanging = ((HangableBlock) state.getBlock()).isHanging(getWorld(), blockEntity_1.getPos());
        Direction direction = state
            .get(DirectionalBlock.FACING);
        GlStateManager.pushMatrix();
        GlStateManager.translated(x, y, z);
        GlStateManager.normal3f(0F, 1F, 0F);
        GlStateManager.translatef(0F, 1F, 1F);
        GlStateManager.disableLighting();
        float[] pans = blockEntity_1.getPanRotationPosition();
        GlStateManager.translatef(pans[0], pans[1], pans[2]);
        GlStateManager.rotatef(blockEntity_1.getPan(), 0, 1, 0);
        GlStateManager.translatef(-pans[0], -pans[1], -pans[2]);
        float[] tilts = blockEntity_1.getTiltRotationPosition();
        GlStateManager.translatef(tilts[0], tilts[1], tilts[2]);
        GlStateManager.rotatef(blockEntity_1.getTilt(), 1, 0, 0);
        GlStateManager.translatef(-tilts[0], -tilts[1], -tilts[2]);
        renderLight(blockEntity_1, direction, partial_ticks, isFlipped, state, isHanging);
        GlStateManager.rotatef(90F, 1F, 0F, 0);
        GlStateManager.translatef(0F, -1.3F, .2F);
        renderLightBeam(blockEntity_1, partial_ticks, 0.4F, 4, 10, 0xFFD7D3, x, y, z);
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    public void renderLight(AbstractFixtureBlockEntity te, Direction direction, float partialTicks, boolean isFlipped, BlockState state, boolean isHanging) {
        if (te.getHangType() == HangableType.BRACE_BAR && isHanging) {
            GlStateManager.translatef(0, 0.175F, 0);
        }
        if (te.getHangType() == HangableType.HOOK_BAR && isHanging) {
            GlStateManager.translatef(0, 0.05F, 0);
        }
        GlStateManager.translatef(0.5F, 0, -.5F);
        GlStateManager.rotatef(direction.asRotation() , 0, 1, 0);
        GlStateManager.translatef(-.5F, 0, 0.5F);
        GlStateManager.translatef(0.5F, -.5F, -0.5F);
        GlStateManager.rotatef(isFlipped ? 180 : 0, 0, 0, 1);
        GlStateManager.translatef(-.5F, .5F, 0.5F);
        if (te.getHangType() == HangableType.BRACE_BAR && isHanging) {
            if (te.getWorld().getBlockState(te.getPos().offset(Direction.UP)).getBlock() instanceof ISupport) {
                ISupport support = (ISupport) te.getWorld().getBlockState(te.getPos().offset(Direction.UP)).getBlock();
                float[] transforms = support.getLightTransforms(te.getWorld(), te.getPos(), direction);
                GlStateManager.translatef(transforms[0], transforms[1], transforms[2]);
            } else {
                GlStateManager.translatef(0, 0.19F, 0);
            }
        }
//        if (te.getHangType() == HangableType.BRACE_BAR && isHanging) {
//            if (te.getWorld().getBlockState(te.getPos().offset(Direction.UP)).getProperties().containsKey(BlockBar.AXIS)) {
//                Direction facing = te.getWorld().getBlockState(te.getPos().offset(Direction.UP)).getValue(BlockBar.AXIS);
//                GlStateManager.translatef(0.5F, 0, -.5F);
//                GlStateManager.rotatef(facing.(), 0, 1, 0);
//                GlStateManager.translatef(-.5F, 0, 0.5F);
//            }
//        }
        renderHookBar(te, state);
//        if (te.getHangType() == HangableType.BRACE_BAR && isHanging) {
//            if (te.getWorld().getBlockState(te.getPos().offset(EnumFacing.UP)).getProperties().containsKey(BlockBar.AXIS)) {
//                EnumFacing facing = te.getWorld().getBlockState(te.getPos().offset(EnumFacing.UP)).getValue(BlockBar.AXIS);
//                GlStateManager.translatef(0.5F, 0, -.5F);
//                GlStateManager.rotate(-facing.getHorizontalAngle(), 0, 1, 0);
//                GlStateManager.translatef(-.5F, 0, 0.5F);
//            }
//        }
        float[] pans = te.getPanRotationPosition();
        GlStateManager.translatef(pans[0], pans[1], pans[2]);
        GlStateManager.rotatef(te.prevPan + (te.getPan() - te.prevPan) * partialTicks, 0, 1, 0);
        GlStateManager.translatef(-pans[0], -pans[1], -pans[2]);
        renderLightHandle(te, state);
        float[] tilts = te.getTiltRotationPosition();
        GlStateManager.translatef(tilts[0], tilts[1], tilts[2]);
        GlStateManager.rotatef(te.prevTilt + (te.getTilt() - te.prevTilt) * partialTicks,
            1, 0, 0);
        GlStateManager.translatef(-tilts[0], -tilts[1], -tilts[2]);
        renderLightBody(te, state);
        GlStateManager.translatef(0.5F, 0, -.5F);
        GlStateManager.rotatef(te.getDefaultRotation(), 1, 0, 0);
        GlStateManager.translatef(-.5F, 0, 0.5F);
    }

    public void renderModel(BakedModel model, AbstractFixtureBlockEntity te, BlockState state) {
        BlockPos pos = te.getPos();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBufferBuilder();
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepthTest();
        bufferbuilder.begin(GL11.GL_QUADS, VertexFormats.POSITION_UV_LMAP_COLOR);
        bufferbuilder.setOffset(-pos.getX(), -1 - pos.getY(), -1 - pos.getZ());
        bufferbuilder.color(255, 255, 255, 255);
        te.getWorld().getProfiler().push("blockDispatcher");
        blockModelRenderer
            .tesselate(getWorld(), model, state, pos, bufferbuilder, false, new Random(), 0);
        te.getWorld().getProfiler().pop();
        bufferbuilder.setOffset(0, 0, 0);
        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public void renderHookBar(AbstractFixtureBlockEntity te, BlockState state) {
        if (hookDisplayList == -1) {
            hookDisplayList = GlAllocationUtils.genLists(1);
            GlStateManager.newList(this.hookDisplayList, 4864);
            renderModel(te.getStaticModel(), te, state);
            GlStateManager.endList();
        } else {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.depthMask(true);
            GlStateManager.enableDepthTest();
            GlStateManager.callList(this.hookDisplayList);
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    public void renderLightHandle(AbstractFixtureBlockEntity te, BlockState state) {
        if (lightHandleList == -1) {
            lightHandleList = GlAllocationUtils.genLists(1);
            GlStateManager.newList(this.lightHandleList, 4864);
            renderModel(te.getPanModel(), te, state);
            GlStateManager.endList();
        } else {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.depthMask(true);
            GlStateManager.enableDepthTest();
            GlStateManager.callList(this.lightHandleList);
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    public void renderLightBody(AbstractFixtureBlockEntity te, BlockState state) {
        if (lightBodyList == -1) {
            lightBodyList = GlAllocationUtils.genLists(1);
            GlStateManager.newList(this.lightBodyList, 4864);
            renderModel(te.getTiltModel(), te, state);
            GlStateManager.endList();
        } else {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.depthMask(true);
            GlStateManager.enableDepthTest();
            GlStateManager.callList(this.lightBodyList);
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }


    public void renderLightBeam(AbstractFixtureBlockEntity tileFresnel, float partialTicks,
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
