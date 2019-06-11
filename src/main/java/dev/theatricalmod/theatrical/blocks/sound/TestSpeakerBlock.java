package dev.theatricalmod.theatrical.blocks.sound;

import dev.theatricalmod.theatrical.Theatrical;
import dev.theatricalmod.theatrical.blocks.entity.sound.TestSpeakerBlockEntity;
import dev.theatricalmod.theatrical.items.group.TheatricalItemGroups;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class TestSpeakerBlock extends Block implements BlockEntityProvider {

    private final Item item;

    public TestSpeakerBlock() {
        super(FabricBlockSettings.of(Material.STONE).build());
        this.item = new BlockItem(this, new Item.Settings().group(TheatricalItemGroups.LIGHTS_ITEM_GROUP));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new TestSpeakerBlockEntity();
    }

    @Override
    public void onPlaced(World world_1, BlockPos blockPos_1, BlockState blockState_1, LivingEntity livingEntity_1, ItemStack itemStack_1) {
        BlockEntity be = world_1.getBlockEntity(blockPos_1);
        if (be instanceof TestSpeakerBlockEntity) {
            ((TestSpeakerBlockEntity) be).playSound(Theatrical.TEST_SOUND);
        }
        super.onPlaced(world_1, blockPos_1, blockState_1, livingEntity_1, itemStack_1);
    }

    @Override
    public void onBroken(IWorld iWorld_1, BlockPos blockPos_1, BlockState blockState_1) {
        BlockEntity be = iWorld_1.getBlockEntity(blockPos_1);
        if (be instanceof TestSpeakerBlockEntity) {
//            ((TestSpeakerBlockEntity) be).getAudibleTo().forEach(()->);
        }
        super.onBroken(iWorld_1, blockPos_1, blockState_1);
    }

    @Override
    public Item asItem() {
        return this.item;
    }

}
