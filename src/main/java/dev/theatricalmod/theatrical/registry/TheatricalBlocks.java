package dev.theatricalmod.theatrical.registry;

import dev.theatricalmod.theatrical.blocks.lights.MovingHeadBlock;
import dev.theatricalmod.theatrical.blocks.sound.TestSpeakerBlock;
import dev.theatricalmod.theatrical.util.Constants;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TheatricalBlocks {

    public static final MovingHeadBlock MOVING_HEAD_BLOCK = new MovingHeadBlock();
    public static final TestSpeakerBlock TEST_SPEAKER_BLOCK = new TestSpeakerBlock();

    public static void init() {
        register("moving_head", MOVING_HEAD_BLOCK);
        register("test_speaker", TEST_SPEAKER_BLOCK);
    }

    private static void register(String id, Block block) {
        Registry.register(Registry.BLOCK, new Identifier(Constants.MOD_ID, id), block);
        Registry.register(Registry.ITEM, new Identifier(Constants.MOD_ID, id), block.asItem());
    }

}
