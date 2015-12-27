package ipsis.buildersguides.item;

import ipsis.buildersguides.MarkerType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemMarkerCard extends ItemBG {

    public static final String BASENAME = "markerCard";

    public ItemMarkerCard() {

        super();
        setMaxStackSize(16);
        setUnlocalizedName(BASENAME);
        setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {

        for (MarkerType t : MarkerType.values()) {
            subItems.add(new ItemStack(itemIn, 1, t.ordinal()));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + MarkerType.getMarkerType(stack.getItemDamage());
    }
}
