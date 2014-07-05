package com.ipsis.buildersguides.block;

import com.ipsis.buildersguides.creative.CreativeTab;
import com.ipsis.buildersguides.reference.Reference;
import com.ipsis.buildersguides.tileentity.TileBGMarker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMarker extends BlockContainer {

    public BlockMarker(TileBGMarker.Type type) {

        super(Material.iron);
        this.setCreativeTab(CreativeTab.BG_TAB);
        this.type = type;
    }

    private String iconName;
    private TileBGMarker.Type type;

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

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {

        return new TileBGMarker(type);
    }
}
