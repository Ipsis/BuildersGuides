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
import net.minecraft.client.util.ITooltipFlag;
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

import javax.annotation.Nullable;
import java.util.List;

public class ItemMarkerCard extends ItemBG {

    public static final String BASENAME = "markercard";

    public ItemMarkerCard() {

        super();
        setMaxStackSize(16);
        setHasSubtypes(true);
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
    public String getUnlocalizedName(ItemStack stack) {

        int idx = stack.getItemDamage() % MarkerType.values().length;
        return super.getUnlocalizedName() + "." + MarkerType.getMarkerType(idx).toString().toLowerCase();
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {

        if (isInCreativeTab(tab)) {
            for (MarkerType t : MarkerType.values()) {
                items.add(new ItemStack(this, 1, t.ordinal()));
            }
        }
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
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        if (WorldHelper.isClient(worldIn))
            return super.onItemRightClick(worldIn, playerIn, handIn);

        ItemStack itemStack = playerIn.getHeldItem(handIn);
        if (playerIn.isSneaking() && !itemStack.isEmpty()) {
            MarkerType t = MarkerType.getMarkerType(itemStack.getItemDamage()).getNext();
            setType(itemStack, t);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (WorldHelper.isClient(worldIn))
            return EnumActionResult.FAIL;

        ItemStack itemStack = player.getHeldItem(hand);

        if (!player.isSneaking() && !itemStack.isEmpty()) {

            MarkerType t = MarkerType.getMarkerType(itemStack.getItemDamage());
            if (t == MarkerType.BLANK)
                return EnumActionResult.FAIL;

            TileEntity te = worldIn.getTileEntity(pos);
            if (te != null && te instanceof TileEntityMarker) {

                if (((TileEntityMarker) te).getType() == MarkerType.BLANK) {
                    ((TileEntityMarker) te).resetToType(t);
                    BlockUtils.markBlockForUpdate(worldIn, pos);
                    itemStack.setCount(player.capabilities.isCreativeMode ? itemStack.getCount() : itemStack.getCount() - 1);
                }
            }
        }

        return EnumActionResult.SUCCESS;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        MarkerType t = MarkerType.getMarkerType(stack.getItemDamage());

        if (t == MarkerType.CENTER || t == MarkerType.GHOSTSTAIRS || t == MarkerType.RANGE) {
            tooltip.add(StringHelper.localize(Names.TOOLTIP, BASENAME + "." + t.toString().toLowerCase() + ".1"));
            tooltip.add(StringHelper.localize(Names.TOOLTIP, BASENAME + "." + t.toString().toLowerCase() + ".2"));
        } else {
            tooltip.add(StringHelper.localize(Names.TOOLTIP, BASENAME + "." + t.toString().toLowerCase()));
        }
    }
}
