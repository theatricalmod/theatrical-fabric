package dev.theatricalmod.theatrical.items.attr.fixture.gel;

import dev.theatricalmod.theatrical.items.group.TheatricalItemGroups;
import net.minecraft.item.Item;

public class BlankGel extends Item {

    public BlankGel() {
        super(new Item.Settings().group(TheatricalItemGroups.LIGHTING_GELS_ITEM_GROUP));
    }

}
