package dev.theatricalmod.theatrical.fixtures;

import dev.theatricalmod.theatrical.api.ChannelsDefinition;
import dev.theatricalmod.theatrical.api.fixtures.Fixture;
import dev.theatricalmod.theatrical.api.fixtures.FixtureType;
import dev.theatricalmod.theatrical.api.fixtures.HangableType;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

public class FixtureFresnel extends Fixture {

    public FixtureFresnel() {
        super(new Identifier("theatrical", "fresnel"), FixtureType.TUNGSTEN, HangableType.HOOK_BAR,
            new ModelIdentifier("theatrical", "block/fresnel/fresnel_hook_bar"), new ModelIdentifier("theatrical", "block/fresnel/fresnel_hook"), new ModelIdentifier("theatrical", "block/fresnel/fresnel_body_only"),
            new ModelIdentifier("theatrical", "block/fresnel/fresnel_handle_only"),
            new float[]{0.7F, -.75F, -.64F}, new float[]{0.5F, 0, -.6F}, new float[]{0F, -1.5F, -1F}, 0, 0.25F, 0, 25, 0,
            0, 0, 0, new ChannelsDefinition());
    }
}
