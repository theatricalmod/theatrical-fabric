package dev.theatricalmod.theatrical.items.group;

import dev.theatricalmod.theatrical.registry.TheatricalBlocks;
import dev.theatricalmod.theatrical.registry.TheatricalItems;
import dev.theatricalmod.theatrical.util.Constants;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class TheatricalItemGroups {

    public static final ItemGroup LIGHTS_ITEM_GROUP = FabricItemGroupBuilder
            .create(new Identifier(Constants.MOD_ID, "lights_item_group"))
            .icon(() -> new ItemStack(TheatricalItems.BLANK_GEL_ITEM.asItem()))
            .build();

    public static final ItemGroup LIGHTING_GELS_ITEM_GROUP = FabricItemGroupBuilder
            .create(new Identifier(Constants.MOD_ID, "gels_item_group"))
            .icon(() -> new ItemStack(TheatricalItems.BLANK_GEL_ITEM))
            .build();

}
