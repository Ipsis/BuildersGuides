package com.ipsis.buildersguides.item;

import com.ipsis.buildersguides.tileentity.IWrenchable;
import com.ipsis.buildersguides.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * TODO implement IToolWrench
 */
public class ItemAllenKey extends ItemBG {

    public ItemAllenKey() {

        super();
        this.setUnlocalizedName("allenKey");
        this.setMaxStackSize(1);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {

        if (world.isRemote)
            return false;

        Block block = world.getBlock(x, y, z);
        if (block == null)
            return false;

        if (player.isSneaking()) {

                TileEntity te = world.getTileEntity(x, y, z);
                if (te != null && te instanceof IWrenchable) {
                    ((IWrenchable) te).shiftLeftWrench();
                    return true;
                }

                return false;
        } else {

            if (block.rotateBlock(world, x, y, z, ForgeDirection.getOrientation(side))) {
                player.swingItem();
                return true;
            }
        }

        return false;
    }

    public boolean canWrench(EntityPlayer player, int x, int y, int z) {

        return true;
    }

    public void wrenchUsed(EntityPlayer player, int x, int y, int z) {

    }

}
