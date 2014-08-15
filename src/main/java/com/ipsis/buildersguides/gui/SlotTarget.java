package com.ipsis.buildersguides.gui;


import com.ipsis.buildersguides.block.BGBlocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotTarget extends Slot {

    public SlotTarget(IInventory inventory, int x, int y, int z) {

        super(inventory, x, y, z);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {

        return  itemStack.isItemEqual(new ItemStack(BGBlocks.blockTarget));
    }
}
