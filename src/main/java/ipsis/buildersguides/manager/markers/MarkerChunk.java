package ipsis.buildersguides.manager.markers;

import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.oss.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class MarkerChunk extends Marker {
    @Override
    public boolean isMatch(MarkerType t) {
        return t == MarkerType.CHUNK;
    }

    @Override
    public void handleHammer(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {
        /* NOOP */
        LogHelper.info("handleHammer: CHUNK");
    }

    @Override
    public void handleConfig(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {
        /* NOOP */
        LogHelper.info("handleConfig: CHUNK");
    }

    @Override
    public boolean isFaceEnabled(TileEntityMarker te, EnumFacing f) {
        return false;
    }
}
