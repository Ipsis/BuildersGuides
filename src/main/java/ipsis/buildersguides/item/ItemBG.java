package ipsis.buildersguides.item;

import ipsis.buildersguides.BuildersGuides;
import ipsis.buildersguides.oss.client.ModelHelper;
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
    public void initModel(Item item, String name) {

        ModelHelper.registerItem(item, name);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {

    }
}
