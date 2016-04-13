package ipsis.buildersguides.item;

import ipsis.buildersguides.util.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemBlockMarker extends ItemBlock {

    public ItemBlockMarker(Block b) {

        super(b);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {

        if (worldIn.isRemote || itemStackIn == null)
            return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);

        BlockUtils.placeInAir(worldIn, itemStackIn, playerIn, EnumFacing.DOWN);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);

    }
}
