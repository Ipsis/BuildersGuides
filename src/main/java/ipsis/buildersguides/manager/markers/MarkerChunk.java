package ipsis.buildersguides.manager.markers;

import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * Chunk Marker
 *
 * Displays the current chunk boundary and displays the center blocks in the xz plane
 */
public class MarkerChunk extends Marker {
    @Override
    public boolean isMatch(MarkerType t) {
        return t == MarkerType.CHUNK;
    }

    @Override
    public void handleHammer(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {
        /* NOOP */
    }

    @Override
    public void handleConfig(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {
        /* NOOP */
    }

    @Override
    public boolean isFaceEnabled(TileEntityMarker te, EnumFacing f) {
        return false;
    }
}
