package ipsis.buildersguides.block;

import ipsis.buildersguides.reference.Names;
import ipsis.buildersguides.tileentity.TileGhostMarker;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGhostMarker extends BlockBG implements ITileEntityProvider{

    public BlockGhostMarker() {

        super();
        this.setBlockName(Names.Blocks.BLOCK_GHOST_MARKER);
    }


    @Override
    public boolean onBlockActivated(World world, int x,	int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ) {

        /*if (!world.isRemote) */{
            TileEntity te = world.getTileEntity(x, y, z);
            if (te instanceof TileGhostMarker) {
                if (entityPlayer.isSneaking())
                    ((TileGhostMarker) te).pullSide(ForgeDirection.getOrientation(side).getOpposite());
                else
                    ((TileGhostMarker) te).pushSide(ForgeDirection.getOrientation(side).getOpposite());
            }
        }

        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileGhostMarker();
    }

    @Override
    public boolean isOpaqueCube() {

        return false;
    }
}
