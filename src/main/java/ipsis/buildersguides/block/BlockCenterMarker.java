package ipsis.buildersguides.block;

import ipsis.buildersguides.reference.Names;
import ipsis.buildersguides.tileentity.TileCenterMarker;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCenterMarker extends BlockBaseMarker {

    public BlockCenterMarker() {

        this.setBlockName(Names.Blocks.BLOCK_CENTER_MARKER);
    }

    /**
     * ITileEnityProvider
     */
    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {

        return new TileCenterMarker();
    }

}
