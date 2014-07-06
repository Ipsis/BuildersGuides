package com.ipsis.buildersguides.block;

import com.ipsis.buildersguides.creative.CreativeTab;
import com.ipsis.buildersguides.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockBG extends Block {

    public BlockBG() {

        super(Material.iron);
        this.setCreativeTab(CreativeTab.BG_TAB);
    }

    protected String iconName;

    @Override
    public Block setBlockName(String name) {

        iconName = name;
        name = Reference.MOD_ID + ":" + name;
        return super.setBlockName(name);
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {

        blockIcon = iconRegister.registerIcon(Reference.MOD_ID + ":" + iconName);
    }
}
