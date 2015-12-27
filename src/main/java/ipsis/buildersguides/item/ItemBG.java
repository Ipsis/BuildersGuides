package ipsis.buildersguides.item;

import ipsis.buildersguides.BuildersGuides;
import ipsis.buildersguides.reference.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBG extends Item {

    public ItemBG() {
        super();
        setCreativeTab(BuildersGuides.tabBG);
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("item.%s%s", Reference.MOD_ID + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return String.format("item.%s%s", Reference.MOD_ID + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}
