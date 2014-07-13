package com.ipsis.buildersguides.block;

import com.ipsis.buildersguides.tileentity.TileRangeMarker;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockRangeMarker extends BlockBG implements ITileEntityProvider {

    public BlockRangeMarker() {

        super();
        setBlockName("rangeMarker");
    }

    @Override
    public boolean isOpaqueCube() {

        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {

        return new TileRangeMarker();
    }
}
