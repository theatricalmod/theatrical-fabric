package dev.theatricalmod.theatrical.blocks.lights;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class Par64Block extends Block implements BlockEntityProvider {

    public Par64Block() {
        super(FabricBlockSettings.of(Material.STONE).build());
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return null;
    }

}
