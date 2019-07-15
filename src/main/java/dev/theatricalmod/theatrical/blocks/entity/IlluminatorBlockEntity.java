package dev.theatricalmod.theatrical.blocks.entity;

import dev.theatricalmod.theatrical.api.fixtures.IFixture;
import dev.theatricalmod.theatrical.blocks.base.IlluminatorBlock;
import dev.theatricalmod.theatrical.registry.TheatricalBlockEntities;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class IlluminatorBlockEntity extends BaseBlockEntity implements Tickable {

    private BlockPos controller;


    public IlluminatorBlockEntity() {
        super(TheatricalBlockEntities.ILLUMINATOR);
    }

    public CompoundTag getNBT(@Nullable CompoundTag nbtTagCompound) {
        if (nbtTagCompound == null) {
            nbtTagCompound = new CompoundTag();
        }
        if (controller != null) {
            nbtTagCompound.putInt("controllerX", controller.getX());
            nbtTagCompound.putInt("controllerY", controller.getY());
            nbtTagCompound.putInt("controllerZ", controller.getZ());
        }
        return nbtTagCompound;
    }

    public void readNBT(CompoundTag nbtTagCompound) {
        int x = nbtTagCompound.getInt("controllerX");
        int y = nbtTagCompound.getInt("controllerY");
        int z = nbtTagCompound.getInt("controllerZ");
        controller = new BlockPos(x, y, z);
    }

    public BlockPos getController() {
        return controller;
    }

    public void setController(BlockPos controller) {
        this.controller = controller;
        this.markDirty();
        if (!world.isClient) {
            world.onBlockChanged(pos, world.getBlockState(pos), world.getBlockState(pos));
        }
    }

    @Override
    public void tick() {
        if(!world.isClient) {
            if (controller != null) {
                BlockEntity blockEntity = world.getBlockEntity(controller);
                if (blockEntity instanceof AbstractFixtureBlockEntity) {
                    AbstractFixtureBlockEntity fixture = (AbstractFixtureBlockEntity) blockEntity;
                    if (!pos.equals(fixture.getLightBlock())) {
                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    }else{
                        int lightLevel = 0;
                        if (fixture instanceof IFixture) {
                            IFixture iFixture = (IFixture) fixture;
                            if (iFixture != null) {
                                float val = (iFixture.getIntensity() / 255F);
                                int thing = (int) (val * 15F);
                                lightLevel = thing;
                            }
                        }
                        world.setBlockState(pos, world.getBlockState(pos).with(IlluminatorBlock.LIGHT, lightLevel));
                    }
                } else {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                }
            } else {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
    }
}
