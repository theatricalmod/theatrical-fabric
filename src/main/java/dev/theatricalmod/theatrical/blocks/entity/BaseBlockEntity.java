package dev.theatricalmod.theatrical.blocks.entity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.network.packet.BlockEntityUpdateS2CPacket;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

public class BaseBlockEntity extends BlockEntity {

    public BaseBlockEntity(BlockEntityType<?> blockEntityType_1) {
        super(blockEntityType_1);
    }

    public CompoundTag getNBT(@Nullable CompoundTag nbtTagCompound) {
        if (nbtTagCompound == null) {
            nbtTagCompound = new CompoundTag();
        }
        return nbtTagCompound;
    }

    public void readNBT(CompoundTag nbtTagCompound) {

    }

    @Override
    public void fromTag(CompoundTag compoundTag_1) {
        readNBT(compoundTag_1);
        super.fromTag(compoundTag_1);
    }

    @Override
    public CompoundTag toTag(CompoundTag compoundTag_1) {
        CompoundTag tag = getNBT(compoundTag_1);
        return super.toTag(tag);
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return new BlockEntityUpdateS2CPacket(getPos(), 1, getNBT(null));
    }
}
