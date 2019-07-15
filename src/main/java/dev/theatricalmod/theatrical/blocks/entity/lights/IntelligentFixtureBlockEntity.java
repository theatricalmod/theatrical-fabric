package dev.theatricalmod.theatrical.blocks.entity.lights;

import dev.theatricalmod.theatrical.api.fixtures.Fixture;
import dev.theatricalmod.theatrical.api.fixtures.RGBable;
import dev.theatricalmod.theatrical.blocks.entity.AbstractFixtureBlockEntity;
import dev.theatricalmod.theatrical.blocks.fixtures.IntelligentFixtureBlock;
import dev.theatricalmod.theatrical.registry.TheatricalBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

public class IntelligentFixtureBlockEntity extends AbstractFixtureBlockEntity implements RGBable {

    private int red, green, blue = 0;

    public int power = 0;
    private int maxPower;
    private int ticksSinceUsage = 0;
    public int energyUsage, energyCost, channelStartPoint = 0;


    public IntelligentFixtureBlockEntity(){
        super(null);
    }

    public IntelligentFixtureBlockEntity(Fixture fixture) {
        super(TheatricalBlockEntities.fixtureBlockEntities.get(fixture.getName().getPath()));
        this.setFixture(fixture);
    }

    @Override
    public void setFixture(Fixture fixture) {
        super.setFixture(fixture);
        if (fixture != null) {
            this.maxPower = fixture.getMaxEnergy();
            this.energyCost = fixture.getEnergyUse();
            this.energyUsage = fixture.getEnergyUseTimer();
//            this.getIdmxReceiver().setChannelCount(fixture.getChannelCount());
        }
    }

//    public TileMovingHead(int channelCount, int channelStartPoint, int maxPower, int energyCost, int energyUsage) {
//        super(channelCount, channelStartPoint);
//        this.maxPower = maxPower;
//        this.energyCost = energyCost;
//        this.energyUsage = energyUsage;
//    }



    @Override
    public CompoundTag getNBT(@Nullable CompoundTag nbtTagCompound) {
        nbtTagCompound = super.getNBT(nbtTagCompound);
        nbtTagCompound.putInt("red", red);
        nbtTagCompound.putInt("green", green);
        nbtTagCompound.putInt("blue", blue);
        nbtTagCompound.putInt("power", power);
        return nbtTagCompound;
    }

    @Override
    public void readNBT(CompoundTag nbtTagCompound) {
        super.readNBT(nbtTagCompound);
        red = nbtTagCompound.getInt("red");
        green = nbtTagCompound.getInt("green");
        blue = nbtTagCompound.getInt("blue");
        power = nbtTagCompound.getInt("power");
    }

    public void setRed(int red) {
        if (power >= energyCost) {
            this.red = red;
        }
    }

    public void setGreen(int green) {
        if (power >= energyCost) {
            this.green = green;
        }
    }

    public void setBlue(int blue) {
        if (power >= energyCost) {
            this.blue = blue;
        }
    }

    @Override
    public boolean shouldTrace() {
        return power > 0;
    }

    @Override
    public boolean emitsLight() {
        return false;
    }
//
//    @Override
//    public int receiveEnergy(int maxReceive, boolean simulate) {
//        if (!canReceive()) {
//            return 0;
//        }
//
//        int energyReceived = Math.min(getMaxEnergyStored() - getEnergyStored(), Math.min(Integer.MAX_VALUE, maxReceive));
//        if (!simulate) {
//            power += energyReceived;
//            markDirty();
//            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
//        }
//        return energyReceived;
//    }
//
//    @Override
//    public int extractEnergy(int maxExtract, boolean simulate) {
//        return 0;
//    }

//    @Override
//    public int getEnergyStored() {
//        return power;
//    }
//
//    @Override
//    public int getMaxEnergyStored() {
//        if (getFixture() != null) {
//            return getFixture().getMaxEnergy();
//        }
//        return 0;
//    }
//
//    @Override
//    public boolean canExtract() {
//        return false;
//    }
//
//    @Override
//    public boolean canReceive() {
//        return getEnergyStored() < getMaxEnergyStored();
//    }

    @Override
    public void setPan(int pan) {
        if (power >= energyCost) {
            super.setPan(pan);
        }
    }

    @Override
    public void setFocus(int focus) {
        if (power >= energyCost) {
            super.setFocus(focus);
        }
    }

    @Override
    public void setLightBlock(BlockPos lightBlock) {
        if (power >= energyCost) {
            super.setLightBlock(lightBlock);
        }
    }

    @Override
    public void setTilt(int tilt) {
        if (power >= energyCost) {
            super.setTilt(MathHelper.clamp(tilt, -90, 90));
        }
    }

//    public int getRed() {
//        if (getFixture() != null && getFixture().getChannelsDefinition() != null) {
//            return convertByteToInt(getCapability(DMXReceiver.CAP, EnumFacing.SOUTH).getChannel(getFixture().getChannelsDefinition().getChannel(ChannelType.RED)));
//        }
//        return 0;
//    }
//
//    public int getGreen() {
//        if (getFixture() != null && getFixture().getChannelsDefinition() != null) {
//            return convertByteToInt(getCapability(DMXReceiver.CAP, EnumFacing.SOUTH).getChannel(getFixture().getChannelsDefinition().getChannel(ChannelType.GREEN)));
//        }
//        return 0;
//    }
//
//    public int getBlue() {
//        if (getFixture() != null && getFixture().getChannelsDefinition() != null) {
//            return convertByteToInt(getCapability(DMXReceiver.CAP, EnumFacing.SOUTH).getChannel(getFixture().getChannelsDefinition().getChannel(ChannelType.BLUE)));
//        }
//        return 0;
//    }

    @Override
    public int getPan() {
        if (this.power < this.energyCost) {
            return prevPan;
        }
//        if (getFixture() != null && getFixture().getChannelsDefinition() != null) {
//            return (int) ((convertByteToInt(getCapability(DMXReceiver.CAP, EnumFacing.SOUTH).getChannel(getFixture().getChannelsDefinition().getChannel(ChannelType.PAN))) * 360) / 255F);
//        }
        return prevPan;
    }

    @Override
    public int getTilt() {
        if (this.power < this.energyCost) {
            return prevTilt;
        }
//        if (getFixture() != null && getFixture().getChannelsDefinition() != null) {
//            return (int) ((convertByteToInt(getCapability(DMXReceiver.CAP, EnumFacing.SOUTH).getChannel(getFixture().getChannelsDefinition().getChannel(ChannelType.TILT))) * 180) / 255F) - 90;
//        }
        return prevTilt;
    }

    @Override
    public int getFocus() {
//        if (getFixture() != null && getFixture().getChannelsDefinition() != null) {
//            return convertByteToInt(getCapability(DMXReceiver.CAP, EnumFacing.SOUTH).getChannel(getFixture().getChannelsDefinition().getChannel(ChannelType.FOCUS)));
//        }
        return prevFocus;
    }

    @Override
    public float getIntensity() {
        if (this.power < this.energyCost) {
            return 0;
        }
//        if (getFixture() != null && getFixture().getChannelsDefinition() != null) {
//            return convertByte(getCapability(DMXReceiver.CAP, EnumFacing.SOUTH).getChannel(getFixture().getChannelsDefinition().getChannel(ChannelType.INTENSITY)));
//        }
        return 0;
    }

    public float convertByte(byte val) {
        return val & 0xFF;
    }

    public int convertByteToInt(byte val) {
        return val & 0xFF;
    }

    @Override
    public void tick() {
        super.tick();
        ticksSinceUsage++;
        if (ticksSinceUsage >= energyUsage) {
            ticksSinceUsage = 0;
            if ((power - energyCost) >= 0) {
                power -= energyCost;
            }
        }
        prevTilt = getTilt();
        prevPan = getPan();
    }

    @Override
    public int getColorHex() {
//        return (getRed() << 16) | (getGreen() << 8) | getBlue();
        return 0;
    }

    @Override
    public Class<? extends Block> getBlock() {
        return IntelligentFixtureBlock.class;
    }

    @Override
    public float getRayTraceRotation() {
        if (getFixture() != null) {
            return world.getBlockState(pos).get(IntelligentFixtureBlock.FLIPPED) ? getFixture().getRayTraceRotation() : 0;
        }
        return 0;
    }
}
