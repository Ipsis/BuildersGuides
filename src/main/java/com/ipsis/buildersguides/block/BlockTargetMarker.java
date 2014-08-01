package com.ipsis.buildersguides.block;

import com.ipsis.buildersguides.tileentity.TileTargetMarker;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTargetMarker extends BlockFacedMarker {

    public BlockTargetMarker() {

        super();
        this.setBlockName("targetMarker");
    }

    /**
     * ITileEntityProvider
     */
    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {

        return new TileTargetMarker();
    }
}
