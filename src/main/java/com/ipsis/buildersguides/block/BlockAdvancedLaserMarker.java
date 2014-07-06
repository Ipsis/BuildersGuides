package com.ipsis.buildersguides.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAdvancedLaserMarker extends BlockBG implements ITileEntityProvider {

    public BlockAdvancedLaserMarker() {

        super();
        setBlockName("advancedLaserMarker");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {

        return null;
    }
}
