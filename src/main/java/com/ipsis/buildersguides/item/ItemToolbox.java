package com.ipsis.buildersguides.item;

import com.ipsis.buildersguides.BuildersGuides;
import com.ipsis.buildersguides.reference.Reference;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.UUID;

public class ItemToolbox extends ItemBG {

    public ItemToolbox() {

        super();
        this.setUnlocalizedName("toolBox");
        this.setMaxStackSize(1);
    }

    private void openGui(ItemStack itemStack, EntityPlayer entityPlayer) {

		/* Add a UUID */
        if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey(Reference.UUID_MS) && itemStack.getTagCompound().hasKey(Reference.UUID_LS)) {
			/* do nothing */
        } else {

            if (!itemStack.hasTagCompound())
                itemStack.stackTagCompound = new NBTTagCompound();

            UUID uuid = UUID.randomUUID();
            itemStack.stackTagCompound.setLong(Reference.UUID_MS, uuid.getMostSignificantBits());
            itemStack.stackTagCompound.setLong(Reference.UUID_LS, uuid.getLeastSignificantBits());
        }

        entityPlayer.openGui(BuildersGuides.instance, 0, entityPlayer.worldObj,
                (int)entityPlayer.posX, (int)entityPlayer.posY, (int)entityPlayer.posZ);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {

        if (world.isRemote)
            return itemStack;

        if (!entityPlayer.isSneaking())
            openGui(itemStack, entityPlayer);

        return itemStack;
    }
}
