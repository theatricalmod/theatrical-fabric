package dev.theatricalmod.theatrical.registry;

import dev.theatricalmod.theatrical.api.fixtures.Fixture;
import dev.theatricalmod.theatrical.api.fixtures.FixtureType;
import dev.theatricalmod.theatrical.blocks.entity.IlluminatorBlockEntity;
import dev.theatricalmod.theatrical.blocks.entity.lights.IntelligentFixtureBlockEntity;
import dev.theatricalmod.theatrical.blocks.entity.lights.TungstenFixtureBlockEntity;
import dev.theatricalmod.theatrical.blocks.entity.sound.TestSpeakerBlockEntity;
import dev.theatricalmod.theatrical.util.Constants;
import java.util.HashMap;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TheatricalBlockEntities {

    public static final BlockEntityType<IlluminatorBlockEntity> ILLUMINATOR = BlockEntityType.Builder.create(IlluminatorBlockEntity::new, TheatricalBlocks.ILLUMINATOR_BLOCK).build(null);
    public static final BlockEntityType<TestSpeakerBlockEntity> TEST_SPEAKER = BlockEntityType.Builder.create(TestSpeakerBlockEntity::new, TheatricalBlocks.TEST_SPEAKER_BLOCK).build(null);

    public static final HashMap<String, BlockEntityType> fixtureBlockEntities = new HashMap<>();

    public static void init() {
        register("illuminator", ILLUMINATOR);
        register("test_speaker", TEST_SPEAKER);
        for (Fixture fixture : Fixture.getRegistry()) {
            BlockEntityType blockEntityType = null;
            if(fixture.getFixtureType() == FixtureType.TUNGSTEN){
                blockEntityType = BlockEntityType.Builder.create(TungstenFixtureBlockEntity::new, TheatricalBlocks.fixtureBlocks.get(fixture.getName().getPath())).build(null);
            }else if(fixture.getFixtureType() == FixtureType.INTELLIGENT){
                blockEntityType = BlockEntityType.Builder.create(IntelligentFixtureBlockEntity::new, TheatricalBlocks.fixtureBlocks.get(fixture.getName().getPath())).build(null);
            }
            if(blockEntityType != null) {
                register(fixture.getName().getPath(), blockEntityType);
                fixtureBlockEntities.put(fixture.getName().getPath(), blockEntityType);
            }
        }
    }

    private static void register(String id, BlockEntityType<? extends BlockEntity> type) {
        Registry.register(Registry.BLOCK_ENTITY, new Identifier(Constants.MOD_ID, id), type);
    }

}
