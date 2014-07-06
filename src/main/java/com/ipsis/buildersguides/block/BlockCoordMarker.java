package com.ipsis.buildersguides.block;

import com.ipsis.buildersguides.tileentity.TileCoordMarker;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCoordMarker extends BlockBG implements ITileEntityProvider {

    public BlockCoordMarker() {

        super();
        setBlockName("coordMarker");
    }

    @Override
    public boolean isOpaqueCube() {

        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {

        return new TileCoordMarker();
    }
}
