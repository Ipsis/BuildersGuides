package ipsis.buildersguides.item;

import ipsis.buildersguides.util.UnlocalizedName;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockBG extends ItemBlock {

    public ItemBlockBG(Block b) {
        super(b);
    }

    @Override
    public String getUnlocalizedName() {
        return UnlocalizedName.getUnlocalizedNameItem(super.getUnlocalizedName());
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return this.getUnlocalizedName();
    }
}
