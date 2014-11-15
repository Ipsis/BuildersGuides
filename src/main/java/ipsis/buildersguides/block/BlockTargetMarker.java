package ipsis.buildersguides.block;

import ipsis.buildersguides.reference.Names;
import ipsis.buildersguides.tileentity.TileTargetMarker;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTargetMarker extends BlockFacedMarker {

    public BlockTargetMarker() {

        super();
        this.setBlockName(Names.Blocks.BLOCK_TARGET_MARKER);
    }

    /**
     * ITileEntityProvider
     */
    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {

        return new TileTargetMarker();
    }
}
