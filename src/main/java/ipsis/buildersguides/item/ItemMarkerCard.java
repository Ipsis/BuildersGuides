package ipsis.buildersguides.item;

import ipsis.buildersguides.MarkerType;
import ipsis.buildersguides.init.ModItems;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.WorldHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
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

    public static ItemStack getItemStack(MarkerType t) {
        return new ItemStack(ModItems.itemMarkerCard, 1, t.ordinal());
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {

        if (WorldHelper.isClient(world))
            return false;

        MarkerType t = MarkerType.getMarkerType(stack.getItemDamage());
        if (t == MarkerType.BLANK)
            return false;

        TileEntity te = world.getTileEntity(pos);
        if (te != null && te instanceof TileEntityMarker) {

            if (((TileEntityMarker) te).getType() == MarkerType.BLANK) {
                ((TileEntityMarker) te).setType(t);
                world.markBlockForUpdate(pos);
                stack.stackSize = player.capabilities.isCreativeMode ? stack.stackSize : stack.stackSize - 1;
            }
        }

        return true;
    }
}
