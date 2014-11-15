package ipsis.buildersguides.block;

import ipsis.buildersguides.reference.Names;
import ipsis.buildersguides.tileentity.TileSkyMarker;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSkyMarker extends BlockBaseMarker {

    public BlockSkyMarker() {

        this.setBlockName(Names.Blocks.BLOCK_SKY_MARKER);
    }

    /**
     * ITileEntityProvider
     */
    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {

        return new TileSkyMarker();
    }
}
