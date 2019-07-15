package dev.theatricalmod.theatrical.blocks.entity.lights;

import dev.theatricalmod.theatrical.api.fixtures.Fixture;
import dev.theatricalmod.theatrical.api.fixtures.Gelable;
import dev.theatricalmod.theatrical.blocks.entity.AbstractFixtureBlockEntity;
import dev.theatricalmod.theatrical.blocks.fixtures.TungstenLightBlock;
import dev.theatricalmod.theatrical.items.attr.fixture.gel.GelType;
import dev.theatricalmod.theatrical.registry.TheatricalBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

public class TungstenFixtureBlockEntity extends AbstractFixtureBlockEntity implements Gelable {

    private int ticksSinceUsage = 0;

    public int lastPower = 0;
    public int energyUsage, energyCost;

    private int power;
    private int capacity = 255;
    private int maxReceive = 255;
    private int maxExtract = 255;

    public TungstenFixtureBlockEntity(){
        super(null);
    }

    public TungstenFixtureBlockEntity(Fixture fixture) {
        super(TheatricalBlockEntities.fixtureBlockEntities.get(fixture.getName().getPath()));
        this.setFixture(fixture);
    }

    @Override
    public void setFixture(Fixture fixture) {
        super.setFixture(fixture);
        if (fixture != null) {
            energyCost = fixture.getEnergyUse();
            energyUsage = fixture.getEnergyUseTimer();
        }
    }

    private GelType gelType = GelType.CLEAR;

    @Override
    public GelType getGel() {
        return gelType;
    }

    @Override
    public CompoundTag getNBT(@Nullable CompoundTag nbtTagCompound) {
        nbtTagCompound = super.getNBT(nbtTagCompound);
//        nbtTagCompound.put("items", itemStackHandler.serializeNBT());
//        nbtTagCompound.setTag("power", theatricalPowerStorage.serializeNBT());
        nbtTagCompound.putInt("lastPower", lastPower);
        return nbtTagCompound;
    }

    @Override
    public void readNBT(CompoundTag nbtTagCompound) {
        super.readNBT(nbtTagCompound);
        if (nbtTagCompound.containsKey("items")) {
//            itemStackHandler.deserializeNBT((CompoundTag) nbtTagCompound.getTag("items"));
//            gelType = GelType.getGelType(itemStackHandler.getStackInSlot(0).getMetadata());
        }
//        if (nbtTagCompound.hasKey("power")) {
//            theatricalPowerStorage.deserializeNBT(nbtTagCompound.getCompoundTag("power"));
//        }
        if (nbtTagCompound.containsKey("lastPower")) {
            lastPower = nbtTagCompound.getInt("lastPower");
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (world.isClient) {
            return;
        }
        if (power != lastPower) {
            lastPower = power;
            world.onBlockChanged(pos, world.getBlockState(pos), world.getBlockState(pos));
        }
//        ticksSinceUsage++;
//        if (ticksSinceUsage > 20) {
//            ticksSinceUsage = 0;
//            extractSocapex(getEnergyStored(), false);
//        }
    }

    @Override
    public boolean shouldTrace() {
        return power > 0;
    }

    @Override
    public boolean emitsLight() {
        return false;
    }

    @Override
    public float getIntensity() {
        return lastPower;
    }

//    @Override
//    public int receiveEnergy(int maxReceive, boolean simulate) {
//        if (!canReceive()) {
//            return 0;
//        }
//
//        int energyReceived = Math.min(this.maxReceive, maxReceive);
//        if (!simulate) {
//            power = energyReceived;
//        }
//        return energyReceived;
//    }
//
//    @Override
//    public int extractEnergy(int maxExtract, boolean simulate) {
//        if (!canExtract()) {
//            return 0;
//        }
//
//        int energyExtracted = Math.min(power, Math.min(this.maxExtract, maxExtract));
//        if (!simulate) {
//            power = energyExtracted;
//        }
//        return energyExtracted;
//    }
//
//    @Override
//    public int getEnergyStored() {
//        return power;
//    }
//
//    @Override
//    public int getMaxEnergyStored() {
//        return capacity;
//    }
//
//    @Override
//    public boolean canExtract() {
//        return this.maxExtract > 0;
//    }
//
//    @Override
//    public boolean canReceive() {
//        return this.maxReceive > 0;
//    }

    @Override
    public Class<? extends Block> getBlock() {
        return TungstenLightBlock.class;
    }
}
