package com.ipsis.buildersguides.item;

import com.ipsis.buildersguides.creative.CreativeTab;
import com.ipsis.buildersguides.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBG extends Item {

    public ItemBG() {

        this(null);
    }

    private String iconName;
    private String info;

    public ItemBG(String info) {

        super();
        this.setCreativeTab(CreativeTab.BG_TAB);
        this.setNoRepair();
        this.info = info;
    }

    @Override
    public Item setUnlocalizedName(String name) {

        iconName = name;
        name = Reference.MOD_ID + ":" + name;
        return super.setUnlocalizedName(name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {

        itemIcon = iconRegister.registerIcon(Reference.MOD_ID + ":" + iconName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack,	EntityPlayer entityPlayer, List info, boolean useExtraInformation) {

        if (this.info != null)
            info.add(this.info);
    }
}
