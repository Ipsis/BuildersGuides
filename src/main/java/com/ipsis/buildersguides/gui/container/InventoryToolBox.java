package com.ipsis.buildersguides.gui.container;

import com.ipsis.buildersguides.block.BGBlocks;
import com.ipsis.buildersguides.block.BlockTarget;
import com.ipsis.buildersguides.item.BGItems;
import com.ipsis.buildersguides.item.ItemAllenKey;
import com.ipsis.buildersguides.item.ItemMarker;
import com.ipsis.buildersguides.reference.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.UUID;

public class InventoryToolBox implements IInventory {

    public ItemStack parentStack;
    private ItemStack[] inventory;

    public InventoryToolBox(ItemStack itemStack) {

        parentStack = itemStack;
        inventory = new ItemStack[13];

        /* Load the inventory from that stored in the itemstack */
        readFromNBT(itemStack.getTagCompound());
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int count) {

        if (inventory[slot] == null)
            return null;

        if (inventory[slot].stackSize <= count) {
            ItemStack s = inventory[slot];
            inventory[slot] = null;
            markDirty();
            return s;
        }

        ItemStack s = inventory[slot].splitStack(count);
        if (inventory[slot].stackSize <= 0)
            inventory[slot] = null;

        markDirty();
        return s;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {

        ItemStack s = inventory[slot];
        if (s != null)
            inventory[slot] = null;

        return s;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {

        inventory[slot] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit())
            stack.stackSize = getInventoryStackLimit();

        markDirty();
    }


    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {

        /* Can this actually be called, as the toolbox can never have items piped into it? */
        return true;
    }

    /**
     * Save the pouch contents in the parent stack in the players inventory
     */
    public void onGuiSaved(EntityPlayer entityPlayer) {

        parentStack = findParentItemStack(entityPlayer);

        if (parentStack != null)
            save();
    }

    /**
     * Search the players inventory for the matching parent stack
     */
    public ItemStack findParentItemStack(EntityPlayer entityPlayer) {

    	/* Can only find the parent if the parent currently has a UUID */
        if (parentStack.hasTagCompound() && parentStack.getTagCompound().hasKey(Reference.UUID_MS) && parentStack.getTagCompound().hasKey(Reference.UUID_LS)) {

            UUID parentUUID = new UUID(parentStack.getTagCompound().getLong(Reference.UUID_MS), parentStack.getTagCompound().getLong(Reference.UUID_LS));
            for (int i = 0; i < entityPlayer.inventory.getSizeInventory(); i++)
            {
                ItemStack itemStack = entityPlayer.inventory.getStackInSlot(i);

                if (itemStack != null && itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey(Reference.UUID_MS) && itemStack.getTagCompound().hasKey(Reference.UUID_LS)) {

                	/* This is the matching parent stack */
                    if (itemStack.getTagCompound().getLong(Reference.UUID_MS) == parentUUID.getMostSignificantBits() &&
                            itemStack.getTagCompound().getLong(Reference.UUID_LS) == parentUUID.getLeastSignificantBits()) {

                        return itemStack;
                    }
                }
            }
        }

        return null;
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {

        if (nbttagcompound != null) {
            NBTTagList nbttaglist = nbttagcompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < nbttaglist.tagCount(); i++) {
                NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
                int slot = nbttagcompound1.getByte("Slot") & 0xff;
                if (slot >= 0 && slot < inventory.length) {
                    setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(nbttagcompound1));
                }
            }
        }
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {

        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < inventory.length; i++) {
            ItemStack stack = getStackInSlot(i);

            if (stack != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                stack.writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Items", nbttaglist);
    }

    public void save() {

        NBTTagCompound nbtTagCompound = parentStack.getTagCompound();

        if (nbtTagCompound == null)
        {
            nbtTagCompound = new NBTTagCompound();

            UUID uuid = UUID.randomUUID();
            nbtTagCompound.setLong(Reference.UUID_MS, uuid.getMostSignificantBits());
            nbtTagCompound.setLong(Reference.UUID_LS, uuid.getLeastSignificantBits());
        }

        writeToNBT(nbtTagCompound);
        parentStack.setTagCompound(nbtTagCompound);
    }
}
