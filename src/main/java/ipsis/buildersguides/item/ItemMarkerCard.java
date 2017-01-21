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

    public static final String BASENAME = "markercard";

    public ItemMarkerCard() {

        super();
        setMaxStackSize(16);
        setUnlocalizedName(BASENAME);
        setHasSubtypes(true);
        setRegistryName(Reference.MOD_ID, BASENAME);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initModel() {

        for (MarkerType t : MarkerType.values()) {
            ModelHelper.registerItem(ModItems.itemMarkerCard, t.ordinal(), BASENAME + "." + t.toString().toLowerCase());
            ModelBakery.registerItemVariants(ModItems.itemMarkerCard, new ResourceLocation(Reference.MOD_ID + ":" + BASENAME + "." + t));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {

        for (MarkerType t : MarkerType.values()) {
            subItems.add(new ItemStack(itemIn, 1, t.ordinal()));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + MarkerType.getMarkerType(stack.getItemDamage()).toString().toLowerCase();
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
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {

        if (WorldHelper.isClient(worldIn))
            return super.onItemRightClick(worldIn, playerIn, hand);

        ItemStack stack = playerIn.getHeldItem(hand);

        if (!stack.isEmpty() && playerIn.isSneaking()) {
            MarkerType t = MarkerType.getMarkerType(stack.getItemDamage()).getNext();
            setType(stack, t);
            return new ActionResult(EnumActionResult.SUCCESS, stack);
        }

        return super.onItemRightClick(worldIn, playerIn, hand);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (WorldHelper.isClient(worldIn))
            return EnumActionResult.FAIL;

        ItemStack stack = playerIn.getHeldItem(hand);

        if (!stack.isEmpty() && !playerIn.isSneaking()) {

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
            tooltip.add(StringHelper.localize(Names.TOOLTIP, BASENAME + "." + t.toString().toLowerCase() + ".1"));
            tooltip.add(StringHelper.localize(Names.TOOLTIP, BASENAME + "." + t.toString().toLowerCase() + ".2"));
        } else {
            tooltip.add(StringHelper.localize(Names.TOOLTIP, BASENAME + "." + t.toString().toLowerCase()));
        }
    }
}
