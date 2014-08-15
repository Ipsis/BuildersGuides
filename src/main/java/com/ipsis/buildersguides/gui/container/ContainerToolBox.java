package com.ipsis.buildersguides.gui.container;

import com.ipsis.buildersguides.gui.SlotAllenKey;
import com.ipsis.buildersguides.gui.SlotMarker;
import com.ipsis.buildersguides.gui.SlotMarkerItem;
import com.ipsis.buildersguides.gui.SlotTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
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
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slot) {

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
