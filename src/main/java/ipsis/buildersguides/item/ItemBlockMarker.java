package ipsis.buildersguides.item;

import ipsis.buildersguides.util.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemBlockMarker extends ItemBlockBG {

    public ItemBlockMarker(Block b) {

        super(b);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        /*
        if (worldIn.isRemote || itemStackIn == null)
            return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand); */

        ItemStack itemStack = playerIn.getHeldItem(handIn);

        if (!itemStack.isEmpty())
            BlockUtils.placeInAir(worldIn, itemStack, playerIn, handIn);

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);

    }
}
