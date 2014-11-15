package ipsis.buildersguides.block;

import ipsis.buildersguides.reference.Names;
import ipsis.buildersguides.tileentity.TileLaserMarker;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockLaserMarker extends BlockFacedMarker {

    public BlockLaserMarker() {

        this.setBlockName(Names.Blocks.BLOCK_LASER_MARKER);
    }

    /**
     * ITileEntityProvider
     */
    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {

        return new TileLaserMarker();
    }
}
