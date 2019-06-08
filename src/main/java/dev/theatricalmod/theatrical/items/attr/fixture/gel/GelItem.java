package dev.theatricalmod.theatrical.items.attr.fixture.gel;

import dev.theatricalmod.theatrical.items.group.TheatricalItemGroups;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;

public class GelItem extends Item {

    public GelItem() {
        super(new Item.Settings().group(TheatricalItemGroups.LIGHTING_GELS_ITEM_GROUP));
    }

    @Override
    public void appendStacks(ItemGroup itemGroup_1, DefaultedList<ItemStack> gels) {
        if (itemGroup_1 == TheatricalItemGroups.LIGHTING_GELS_ITEM_GROUP)
            for (GelType gelType : GelType.values()) {
                ItemStack stack = new ItemStack(this);
                CompoundTag tag = stack.getOrCreateTag();
                tag.putInt("gel_id", gelType.getId());
                gels.add(stack);
            }
    }

    @Override
    public String getTranslationKey(ItemStack itemStack_1) {

        if (!itemStack_1.hasTag()) {
            return super.getTranslationKey(itemStack_1) + "." + 1;
        } else {
            return super.getTranslationKey(itemStack_1) + "." + GelType.getGelType(itemStack_1.getTag().getInt("gel_id")).getId();
        }
    }

}
