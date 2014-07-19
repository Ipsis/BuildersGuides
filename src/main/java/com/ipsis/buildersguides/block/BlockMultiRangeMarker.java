package com.ipsis.buildersguides.block;

import com.ipsis.buildersguides.tileentity.TileMultiRangeMarker;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMultiRangeMarker extends BlockBaseMarker {

    public BlockMultiRangeMarker() {

        this.setBlockName("multiRangeMarker");
    }

    /**
     * ITileEnityProvider
     */
    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        
        return new TileMultiRangeMarker();
    }
}
