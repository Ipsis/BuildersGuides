package ipsis.buildersguides.block;

import ipsis.buildersguides.reference.Names;
import ipsis.buildersguides.tileentity.TileMultiRangeMarker;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMultiRangeMarker extends BlockBaseMarker {

    public BlockMultiRangeMarker() {

        this.setBlockName(Names.Blocks.BLOCK_MRANGE_MARKER);
    }

    /**
     * ITileEnityProvider
     */
    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        
        return new TileMultiRangeMarker();
    }
}
