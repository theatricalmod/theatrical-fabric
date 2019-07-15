package dev.theatricalmod.theatrical.registry;

import dev.theatricalmod.theatrical.api.fixtures.Fixture;
import dev.theatricalmod.theatrical.api.fixtures.FixtureType;
import dev.theatricalmod.theatrical.blocks.base.BaseBlock;
import dev.theatricalmod.theatrical.blocks.base.IlluminatorBlock;
import dev.theatricalmod.theatrical.blocks.fixtures.IntelligentFixtureBlock;
import dev.theatricalmod.theatrical.blocks.fixtures.TungstenLightBlock;
import dev.theatricalmod.theatrical.blocks.sound.TestSpeakerBlock;
import dev.theatricalmod.theatrical.util.Constants;
import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TheatricalBlocks {

    public static final IlluminatorBlock ILLUMINATOR_BLOCK = new IlluminatorBlock();
    public static final TestSpeakerBlock TEST_SPEAKER_BLOCK = new TestSpeakerBlock();

    public static final HashMap<String, BaseBlock> fixtureBlocks = new HashMap<>();

    public static void init() {
        register("illuminator", ILLUMINATOR_BLOCK);
        register("test_speaker", TEST_SPEAKER_BLOCK);
        for (Fixture fixture : Fixture.getRegistry()) {
            BaseBlock  baseBlock = null;
            if (fixture.getFixtureType() == FixtureType.TUNGSTEN) {
                baseBlock = new TungstenLightBlock(fixture);
            } else if (fixture.getFixtureType() == FixtureType.INTELLIGENT) {
                baseBlock = new IntelligentFixtureBlock(fixture);
            }
            if(baseBlock != null) {
                register(fixture.getName().getPath(), baseBlock);
                fixtureBlocks.put(fixture.getName().getPath(), baseBlock);
            }
        }
    }

    private static void register(String id, Block block) {
        Registry.register(Registry.BLOCK, new Identifier(Constants.MOD_ID, id), block);
        if(block.asItem() != null) {
            Registry.register(Registry.ITEM, new Identifier(Constants.MOD_ID, id), block.asItem());
        }
    }

}
