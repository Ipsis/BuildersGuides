package ipsis.buildersguides.item;

import ipsis.buildersguides.BuildersGuides;
import ipsis.buildersguides.util.UnlocalizedName;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBG extends Item {

    public ItemBG() {
        super();
        setCreativeTab(BuildersGuides.tabBG);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {

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
