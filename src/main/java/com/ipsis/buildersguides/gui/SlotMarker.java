package com.ipsis.buildersguides.gui;

import com.ipsis.buildersguides.block.BGBlocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotMarker extends Slot {

    public SlotMarker(IInventory inventory, int x, int y, int z) {

        super(inventory, x, y, z);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {

        return BGBlocks.isValidMarker(itemStack);
    }
}
