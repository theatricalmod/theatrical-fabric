package dev.theatricalmod.theatrical;

import dev.theatricalmod.theatrical.blocks.MovingHeadBlock;
import dev.theatricalmod.theatrical.registry.TheatricalBlockEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.launch.common.FabricLauncherBase;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Theatrical implements ModInitializer {

	public static final MovingHeadBlock MOVING_HEAD = new MovingHeadBlock();

	@Override
	public void onInitialize() {
		System.out.println("Hello Fabric world!");
		Registry.register(Registry.BLOCK, new Identifier("theatrical", "moving_head"), MOVING_HEAD);
		Registry.register(Registry.ITEM, new Identifier("theatrical", "moving_head"), new BlockItem(MOVING_HEAD, new Item.Settings().itemGroup(ItemGroup.MISC)));
		TheatricalBlockEntities.init();
	}
}
