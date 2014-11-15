package ipsis.buildersguides.block;

import ipsis.buildersguides.reference.Names;
import ipsis.buildersguides.tileentity.TileMultiTargetMarker;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMultiTargetMarker extends  BlockBaseMarker {

    public BlockMultiTargetMarker() {

        super();
        this.setBlockName(Names.Blocks.BLOCK_MTARGET_MARKER);
    }

    /**
     * ITileEntityProvider
     */
    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {

        return new TileMultiTargetMarker();
    }
}
