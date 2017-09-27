package ipsis.buildersguides.item;

import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.init.ModItems;
import ipsis.buildersguides.reference.Names;
import ipsis.buildersguides.reference.Reference;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.BlockUtils;
import ipsis.buildersguides.util.StringHelper;
import ipsis.buildersguides.util.WorldHelper;
import ipsis.buildersguides.oss.client.ModelHelper;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
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
        setRegistryName(Reference.MOD_ID_LOWER, BASENAME);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initModel() {

        for (MarkerType t : MarkerType.values()) {
            ModelHelper.registerItem(ModItems.itemMarkerCard, t.ordinal(), BASENAME + "." + t);
            ModelBakery.registerItemVariants(ModItems.itemMarkerCard, new ResourceLocation(Reference.MOD_ID + ":" + BASENAME + "." + t));
        }
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

    public static void setType(ItemStack stack, MarkerType t) {

        if (stack != null) {
            stack.setItemDamage(t.ordinal());
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {

        if (WorldHelper.isClient(worldIn))
            return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);

        if (playerIn.isSneaking()) {
            MarkerType t = MarkerType.getMarkerType(itemStackIn.getItemDamage()).getNext();
            setType(itemStackIn, t);
            return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
        }

        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (WorldHelper.isClient(worldIn))
            return EnumActionResult.FAIL;

        if (!playerIn.isSneaking()) {

            MarkerType t = MarkerType.getMarkerType(stack.getItemDamage());
            if (t == MarkerType.BLANK)
                return EnumActionResult.FAIL;

            TileEntity te = worldIn.getTileEntity(pos);
            if (te != null && te instanceof TileEntityMarker) {

                if (((TileEntityMarker) te).getType() == MarkerType.BLANK) {
                    ((TileEntityMarker) te).resetToType(t);
                    BlockUtils.markBlockForUpdate(worldIn, pos);
                    stack.setCount(playerIn.capabilities.isCreativeMode ? stack.getCount() : stack.getCount() - 1);
                }
            }
        }

        return EnumActionResult.SUCCESS;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {

        MarkerType t = MarkerType.getMarkerType(stack.getItemDamage());

        if (t == MarkerType.CENTER || t == MarkerType.GHOSTSTAIRS || t == MarkerType.RANGE) {
            tooltip.add(StringHelper.localize(Names.TOOLTIP, BASENAME + "." + t.toString() + ".1"));
            tooltip.add(StringHelper.localize(Names.TOOLTIP, BASENAME + "." + t.toString() + ".2"));
        } else {
            tooltip.add(StringHelper.localize(Names.TOOLTIP, BASENAME + "." + t.toString()));
        }
    }
}
