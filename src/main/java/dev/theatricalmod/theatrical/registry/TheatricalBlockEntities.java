package dev.theatricalmod.theatrical.registry;

import dev.theatricalmod.theatrical.blocks.entity.lights.MovingHeadBlockEntity;
import dev.theatricalmod.theatrical.util.Constants;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TheatricalBlockEntities {

    public static final BlockEntityType<MovingHeadBlockEntity> MOVING_HEAD = BlockEntityType.Builder.create(MovingHeadBlockEntity::new).build(null);

    public static void init() {
        register("moving_head", MOVING_HEAD);
    }

    private static void register(String id, BlockEntityType<? extends BlockEntity> type) {
        Registry.register(Registry.BLOCK_ENTITY, new Identifier(Constants.MOD_ID, id), type);
    }
}
