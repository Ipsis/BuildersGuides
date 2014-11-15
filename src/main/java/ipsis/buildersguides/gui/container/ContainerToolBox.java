package ipsis.buildersguides.gui.container;

import ipsis.buildersguides.block.BGBlocks;
import ipsis.buildersguides.gui.SlotAllenKey;
import ipsis.buildersguides.gui.SlotMarker;
import ipsis.buildersguides.gui.SlotMarkerItem;
import ipsis.buildersguides.gui.SlotTarget;
import ipsis.buildersguides.item.BGItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Based off Pahimar's ContainerAlchemicalBag.java
 */
public class ContainerToolBox extends Container {

    private final EntityPlayer entityPlayer;
    private final InventoryToolBox inventoryToolBox;

    public ContainerToolBox(EntityPlayer entityPlayer, InventoryToolBox inventoryToolBox) {

        this.entityPlayer = entityPlayer;
        this.inventoryToolBox = inventoryToolBox;

        /* Add the slots */
        this.addSlotToContainer(new SlotAllenKey(this.inventoryToolBox, 0, 14, 12)); /* Allen Key */
        this.addSlotToContainer(new SlotMarkerItem(this.inventoryToolBox, 1, 14, 30)); /* Marker Items */
        this.addSlotToContainer(new SlotTarget(this.inventoryToolBox, 2, 14, 48)); /* Targets */

        /* These should only allow marker blocks */
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 5; x++)
                this.addSlotToContainer(new SlotMarker(this.inventoryToolBox, 3 + x + y * 5, 42 + x * 18, 24 + y * 18));
        }


        /* Player inventory */
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++)
                this.addSlotToContainer(new Slot(entityPlayer.inventory, x + y * 9 + 9, 6 + x * 18, 97 + y * 18));
        }

		/* Player hotbar */
        for (int x = 0; x < 9; x++)
            this.addSlotToContainer(new Slot(entityPlayer.inventory, x, 6 + x * 18, 155));
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {

        /* You are holding it, you cannot be that far away */
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int i) {

        Slot slot = getSlot(i);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            ItemStack result = stack.copy();

            if (i < this.inventoryToolBox.getSizeInventory()) {

                /* toolbox to player */
                int toolBoxSlots = this.inventoryToolBox.getSizeInventory();
                if (!mergeItemStack(stack, toolBoxSlots, this.inventorySlots.size(), false))
                    return null;

            }else {

                /* player to toolbox */
                if (stack.getItem() == BGItems.itemAllenKey) {
                    /* must go in slot 0 */
                    if (!mergeItemStack(stack, 0, 1, false))
                        return null;

                } else if (stack.getItem() == BGItems.itemMarker) {
                    /* must go in slot 1 */
                    if (!mergeItemStack(stack, 1, 2, false))
                        return null;

                } else if (stack.getItem() == Item.getItemFromBlock(BGBlocks.blockTarget)) {
                    /* must go in slot 2 */
                    if (!mergeItemStack(stack, 2, 3, false))
                        return null;

                } else if (BGBlocks.isValidMarker(stack)) {
                    /* must go in slots 3 -> 12 */
                    if (!mergeItemStack(stack, 3, 13, false))
                        return null;

                } else {
                    return null;
                }
            }

            if (stack.stackSize == 0)
                slot.putStack(null);
            else
                slot.onSlotChanged();

            slot.onPickupFromSlot(entityPlayer, stack);
            return result;
        }

        return null;

    }

    public void saveInventory(EntityPlayer entityPlayer) {

        inventoryToolBox.onGuiSaved(entityPlayer);
    }

    @Override
    public void onContainerClosed(EntityPlayer entityPlayer)
    {
        super.onContainerClosed(entityPlayer);

        if (!entityPlayer.worldObj.isRemote)
            saveInventory(entityPlayer);
    }
}
