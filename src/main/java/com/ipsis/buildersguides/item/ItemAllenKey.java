package com.ipsis.buildersguides.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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
        if (block == null || player.isSneaking())
            return false;

        if (block.rotateBlock(world, x, y, z, ForgeDirection.getOrientation(side)))
            player.swingItem();

        return true;
    }

}
