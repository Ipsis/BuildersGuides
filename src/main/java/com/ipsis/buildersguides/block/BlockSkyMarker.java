package com.ipsis.buildersguides.block;

import com.ipsis.buildersguides.tileentity.TileSkyMarker;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSkyMarker extends BlockBaseMarker {

    public BlockSkyMarker() {

        this.setBlockName("skyMarker");
    }

    /**
     * ITileEntityProvider
     */
    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {

        return new TileSkyMarker();
    }
}
