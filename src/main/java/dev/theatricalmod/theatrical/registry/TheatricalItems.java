package dev.theatricalmod.theatrical.registry;

import dev.theatricalmod.theatrical.items.attr.fixture.gel.BlankGel;
import dev.theatricalmod.theatrical.items.attr.fixture.gel.GelItem;
import dev.theatricalmod.theatrical.util.Constants;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TheatricalItems {

    public static final BlankGel BLANK_GEL_ITEM = new BlankGel();
    public static final GelItem GEL_ITEM = new GelItem();

    public static void init() {
        register("blank_gel_item", BLANK_GEL_ITEM);
        register("gel_item", GEL_ITEM);
    }

    private static void register(String id, Item item) {
        Registry.register(Registry.ITEM, new Identifier(Constants.MOD_ID, id), item);
    }

}
