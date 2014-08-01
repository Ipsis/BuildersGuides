package com.ipsis.buildersguides.block;

import com.ipsis.buildersguides.tileentity.TileAxisMarker;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAxisMarker extends BlockBaseMarker {

    public BlockAxisMarker() {

        this.setBlockName("axisMarker");
    }

    /**
     * ITileEntityProvider
     */
    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {

        return new TileAxisMarker();
    }
}
