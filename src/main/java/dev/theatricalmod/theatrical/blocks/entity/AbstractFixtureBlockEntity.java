package dev.theatricalmod.theatrical.blocks.entity;

import dev.theatricalmod.theatrical.api.fixtures.Fixture;
import dev.theatricalmod.theatrical.api.fixtures.HangableType;
import dev.theatricalmod.theatrical.api.fixtures.IFixture;
import dev.theatricalmod.theatrical.api.fixtures.FixtureModelProvider;
import dev.theatricalmod.theatrical.blocks.base.DirectionalBlock;
import dev.theatricalmod.theatrical.blocks.base.IlluminatorBlock;
import dev.theatricalmod.theatrical.blocks.fixtures.base.HangableBlock;
import dev.theatricalmod.theatrical.registry.TheatricalBlockEntities;
import dev.theatricalmod.theatrical.registry.TheatricalBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.RayTraceContext.FluidHandling;
import net.minecraft.world.RayTraceContext.ShapeType;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractFixtureBlockEntity extends BaseBlockEntity implements IFixture, Tickable,
    FixtureModelProvider {

    private Fixture fixture;
    private double distance = 0;
    private int pan, tilt = 0;
    private int focus = 6;

    private long timer = 0;

    public int prevTilt, prevPan, prevFocus = 0;

    private BlockPos lightBlock;

    private BlockEntityType entityType;

    public AbstractFixtureBlockEntity(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
        this.entityType = blockEntityType;
    }

    public void setFixture(Fixture fixture) {
        this.fixture = fixture;
    }

    public Fixture getFixture() {
        return fixture;
    }


    @Override
    public BlockEntityType<?> getType() {
        if(this.entityType == null){
            entityType = TheatricalBlockEntities.fixtureBlockEntities.get(fixture.getName().getPath());
        }
        return entityType;
    }

    @Override
    public CompoundTag getNBT(@Nullable CompoundTag nbtTagCompound) {
        if (nbtTagCompound == null) {
            nbtTagCompound = new CompoundTag();
        }
        nbtTagCompound.putInt("pan", this.pan);
        nbtTagCompound.putInt("tilt", this.tilt);
        nbtTagCompound.putInt("focus", this.focus);
        nbtTagCompound.putInt("prevPan", prevPan);
        nbtTagCompound.putInt("prevTilt", prevTilt);
        nbtTagCompound.putInt("prevFocus", prevFocus);
        nbtTagCompound.putLong("timer", timer);
        nbtTagCompound.putDouble("distance", distance);
        if (getFixture() != null) {
            nbtTagCompound.putString("fixture_type", getFixture().getName().toString());
        }
        return nbtTagCompound;
    }

    @Override
    public void readNBT(CompoundTag nbtTagCompound) {
        pan = nbtTagCompound.getInt("pan");
        tilt = nbtTagCompound.getInt("tilt");
        focus = nbtTagCompound.getInt("focus");
        prevPan = nbtTagCompound.getInt("prevPan");
        prevTilt = nbtTagCompound.getInt("prevTilt");
        prevFocus = nbtTagCompound.getInt("prevFocus");
        timer = nbtTagCompound.getLong("timer");
        distance = nbtTagCompound.getDouble("distance");
        if (nbtTagCompound.containsKey("fixture_type")) {
            setFixture(Fixture.getRegistry().get(new Identifier(nbtTagCompound.getString("fixture_type"))));
            entityType = TheatricalBlockEntities.fixtureBlockEntities.get(fixture.getName().getPath());
        } else {
            setFixture(null);
        }
    }

    public int getPan() {
        return pan;
    }

    public void setPan(int pan) {
        this.pan = pan;
        this.markDirty();
        if (!world.isClient) {
            world.onBlockChanged(pos, world.getBlockState(pos), world.getBlockState(pos));
        }
    }

    public int getTilt() {
        return tilt;
    }

    public void setTilt(int tilt) {
        this.tilt = tilt;
        this.markDirty();
        if (!world.isClient) {
            world.onBlockChanged(pos, world.getBlockState(pos), world.getBlockState(pos));
        }
    }

    public int getFocus() {
        return focus;
    }

    public void setFocus(int focus) {
        this.focus = focus;
        this.markDirty();
        if (!world.isClient) {
            world.onBlockChanged(pos, world.getBlockState(pos), world.getBlockState(pos));
        }
    }

    public BlockPos getLightBlock() {
        return lightBlock;
    }

    public void setLightBlock(BlockPos lightBlock) {
        this.lightBlock = lightBlock;
    }

//    @Override
//    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
//        int prevLightValue = (int) (getIntensity() * 15F / 255F);
//        NBTTagCompound tag = pkt.getNbtCompound();
//        readNBT(tag);
//        int newLightValue = (int) (getIntensity() * 15F / 255F);
//        if (prevLightValue != newLightValue) {
//            if (world != null && lightBlock != null) {
//                world.checkLightFor(EnumSkyBlock.BLOCK, lightBlock);
//            }
//        }
//    }

//    @Override
//    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState,
//        IBlockState newSate) {
//        return (oldState.getBlock() != newSate.getBlock());
//    }

//    public boolean canInteractWith(EntityPlayer playerIn) {
//        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
//    }

    public final Vec3d getVectorForRotation(float pitch, float yaw) {
        float f = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3d((double) (f1 * f2), (double) f3, (double) (f * f2));
    }

    public double doRayTrace() {
        if (!this.getBlock().isInstance(world.getBlockState(pos).getBlock())) {
            return 0;
        }
        Direction direction = world.getBlockState(pos).get(
            DirectionalBlock.FACING);
        float horizontalAngle = direction.getOpposite().asRotation();
        float lookingAngle = -(horizontalAngle + getPan());
        float tilt = getTilt() + getDefaultRotation() + getRayTraceRotation();
        Vec3d look = getVectorForRotation(-tilt, lookingAngle);
        double distance = getMaxLightDistance();
        BlockPos start = pos.add(0.5 + (look.x * 0.65), 0.5 + (look.y * 0.65), 0.5 + (look.z * 0.65));
        BlockPos blockPos = start.add(look.x * distance, look.y * distance, look.z * distance);
        BlockHitResult result = world.rayTrace(new RayTraceContext(new Vec3d(start), new Vec3d(blockPos), ShapeType.COLLIDER, FluidHandling.NONE, null));
        BlockPos lightPos = blockPos;
        if (result != null) {
            distance = result.getPos().distanceTo(new Vec3d(pos));
            if (!result.getBlockPos().equals(pos)) {
                if (!result.getBlockPos().equals(getLightBlock())) {
                    lightPos = result.getBlockPos().offset(direction.getOpposite(), 1);
                } else {
                    return distance;
                }
            }
        }
        if (lightPos.equals(pos)) {
            return distance;
        }
        if (!(world.getBlockState(lightPos).isAir()) && !(world
            .getBlockState(lightPos).getBlock() instanceof IlluminatorBlock)) {
            lightPos = lightPos.offset(Direction.getFacing((float) look.x, (float) look.y, (float) look.z), 1);
        }
        distance = new Vec3d(lightPos).distanceTo(new Vec3d(pos));
        if (lightPos.equals(lightBlock)) {
            if (world.getBlockState(lightBlock).isAir()) {
                if (this.emitsLight()) {
                    world.setBlockState(lightPos,
                        TheatricalBlocks.ILLUMINATOR_BLOCK.getDefaultState(), 3);
                    BlockEntity blockEntity = world.getBlockEntity(lightPos);
                    if (blockEntity != null) {
                        IlluminatorBlockEntity illuminator = (IlluminatorBlockEntity) blockEntity;
                        illuminator.setController(pos);
                        if (world.isClient) {
//                           TODO: TheatricalPacketHandler.INSTANCE
//                                .sendToServer(new UpdateIlluminatorPacket(lightPos, pos));
                        }
                    }
                }
            }
            return distance;
        }
        if (getLightBlock() != null && getLightBlock() != lightPos && world.getBlockState(getLightBlock()).getBlock() instanceof IlluminatorBlock) {
            world.setBlockState(getLightBlock(), Blocks.AIR.getDefaultState());
        }
        if ((!(world.getBlockState(lightPos).isAir()) && !(world.getBlockState(lightPos).getBlock() instanceof IlluminatorBlock))) {
            return distance;
        }
        setLightBlock(lightPos);
        if (this.emitsLight()) {
            world.setBlockState(lightPos,
                TheatricalBlocks.ILLUMINATOR_BLOCK.getDefaultState(), 3);
            BlockEntity blockEntity = world.getBlockEntity(lightPos);
            if (blockEntity != null) {
                IlluminatorBlockEntity illuminator = (IlluminatorBlockEntity) blockEntity;
                illuminator.setController(pos);
                if (lightBlock != null) {
                    world.getLightLevel(LightType.BLOCK, lightBlock);
                }
                if (world.isClient) {
//                 TODO:   TheatricalPacketHandler.INSTANCE
//                        .sendToServer(new UpdateIlluminatorPacket(lightPos, pos));
                }
            }
        }
        return distance;
    }

    @Override
    public void tick() {
        prevFocus = focus;
        prevPan = pan;
        prevTilt = tilt;
        timer++;
        if (timer >= 20) {
            if (shouldTrace()) {
                this.distance = doRayTrace();
                markDirty();
            }
            timer = 0;
        }
    }

    @Override
    public float getIntensity() {
        return 0;
    }

    @Override
    public Class<? extends Block> getBlock() {
        return null;
    }


    @Override
    public float getMaxLightDistance() {
        if (fixture != null) {
            return fixture.getMaxLightDistance();
        }
        return 10;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public HangableType getHangType() {
        if (getFixture() != null) {
            return getFixture().getHangableType();
        }
        return HangableType.NONE;
    }

    @Override
    public BakedModel getStaticModel() {
        if (fixture != null) {
            Block block = getWorld().getBlockState(pos).getBlock();
            if (block instanceof HangableBlock && ((HangableBlock) block).isHanging(world, pos)) {
                return getFixture().getHookedModel();
            }
            return getFixture().getStaticModel();
        }
        return null;
    }

    @Override
    public BakedModel getTiltModel() {
        if (fixture != null) {
            return getFixture().getTiltModel();
        }
        return null;
    }

    @Override
    public BakedModel getPanModel() {
        if (fixture != null) {
            return getFixture().getPanModel();
        }
        return null;
    }

    @Override
    public float[] getTiltRotationPosition() {
        if (getFixture() == null) {
            return new float[3];
        }
        return getFixture().getTiltRotationPosition();
    }

    @Override
    public float[] getPanRotationPosition() {
        if (getFixture() == null) {
            return new float[3];
        }
        return getFixture().getPanRotationPosition();
    }

    @Override
    public float getDefaultRotation() {
        if (getFixture() == null) {
            return 0;
        }
        return getFixture().getDefaultRotation();
    }

    @Override
    public float[] getBeamStartPosition() {
        if (getFixture() == null) {
            return new float[3];
        }
        return getFixture().getBeamStartPosition();
    }

    @Override
    public float getBeamWidth() {
        if (getFixture() == null) {
            return 0;
        }
        return getFixture().getBeamWidth();
    }

    @Override
    public float getRayTraceRotation() {
        if (getFixture() == null) {
            return 0;
        }
        return getFixture().getRayTraceRotation();
    }


}
