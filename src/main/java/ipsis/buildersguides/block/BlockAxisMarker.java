package ipsis.buildersguides.block;

import ipsis.buildersguides.reference.Names;
import ipsis.buildersguides.tileentity.TileAxisMarker;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAxisMarker extends BlockBaseMarker {

    public BlockAxisMarker() {

        this.setBlockName(Names.Blocks.BLOCK_AXIS_MARKER);
    }

    /**
     * ITileEntityProvider
     */
    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {

        return new TileAxisMarker();
    }
}
