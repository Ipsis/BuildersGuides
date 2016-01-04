package ipsis.buildersguides.item;

import ipsis.buildersguides.BuildersGuides;
import ipsis.buildersguides.util.UnlocalizedName;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBG extends Item {

    public ItemBG() {
        super();
        setCreativeTab(BuildersGuides.tabBG);
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
