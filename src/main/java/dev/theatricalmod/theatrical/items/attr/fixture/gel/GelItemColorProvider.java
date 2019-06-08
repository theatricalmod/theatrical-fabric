package dev.theatricalmod.theatrical.items.attr.fixture.gel;

import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.item.ItemStack;

public class GelItemColorProvider implements ItemColorProvider {

    @Override
    public int getColor(ItemStack itemStack, int i) {
        GelType gelType;
        if (!itemStack.hasTag())
            gelType = GelType.getGelType(1);
        else
            gelType = GelType.getGelType(itemStack.getTag().getInt("gel_id"));
        int a = 0xAA;
        int r = gelType.getHex() / 10000;
        int g = (gelType.getHex() - r) / 100;
        int b = gelType.getHex() - g;
        return a << 8 | r << 6 | g << 4 | b;
    }

}
