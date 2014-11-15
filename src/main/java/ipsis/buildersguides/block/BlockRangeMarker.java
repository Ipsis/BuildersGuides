package ipsis.buildersguides.block;

import ipsis.buildersguides.tileentity.TileRangeMarker;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockRangeMarker extends BlockFacedMarker {

    public BlockRangeMarker() {

        super();
        this.setBlockName("rangeMarker");
    }

    /**
     * ITileEntityProvider
     */
    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {

        return new TileRangeMarker();
    }
}
