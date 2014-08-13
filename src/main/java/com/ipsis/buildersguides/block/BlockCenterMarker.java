package com.ipsis.buildersguides.block;

import com.ipsis.buildersguides.tileentity.TileBaseMarker;
import com.ipsis.buildersguides.tileentity.TileCenterMarker;
import com.ipsis.buildersguides.tileentity.TileMultiRangeMarker;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCenterMarker extends BlockBaseMarker {

    public BlockCenterMarker() {

        this.setBlockName("centerMarker");
    }

    /**
     * ITileEnityProvider
     */
    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {

        return new TileCenterMarker();
    }

}
