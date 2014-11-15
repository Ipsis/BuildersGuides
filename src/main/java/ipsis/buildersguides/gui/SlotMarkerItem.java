package ipsis.buildersguides.gui;

import ipsis.buildersguides.item.BGItems;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotMarkerItem extends Slot {

    public SlotMarkerItem(IInventory inventory, int x, int y, int z) {

        super(inventory, x, y, z);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {

        return itemStack.getItem() == BGItems.itemMarker;
    }
}
