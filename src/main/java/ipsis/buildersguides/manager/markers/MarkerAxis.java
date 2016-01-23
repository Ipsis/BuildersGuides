package ipsis.buildersguides.manager.markers;

import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;


/**
 * Axis Marker
 *
 * Displays the three dimensional axes
 * Can disable each face
 * Default to all faces active
 */
public class MarkerAxis extends Marker {

    private final static int MAX_AXIS_DISTANCE = 64;

    @Override
    public boolean isMatch(MarkerType t) {
        return t == MarkerType.AXIS;
    }

    @Override
    public void handleHammer(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {

        if (entityPlayer.isSneaking()) {
            te.setV(side, 0);
        } else {
            te.setV(side, 1);
        }
        worldIn.markBlockForUpdate(te.getPos());
    }

    @Override
    public void handleConfig(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {
        /* NOOP */
    }

    @Override
    public boolean isFaceEnabled(TileEntityMarker te, EnumFacing f) {
        return te.hasValidV(f);
    }

    @Override
    public void initServerMarker(TileEntityMarker te) {

        for (EnumFacing f : EnumFacing.values())
            te.setV(f, 1);

        te.getWorld().markBlockForUpdate(te.getPos());
    }

    @Override
    public void handleServerUpdate(TileEntityMarker te) {

        te.clearClientData();

        for (EnumFacing f : EnumFacing.VALUES) {
            if (te.isFaceEnabled(f)) {
                for (int i = 1; i <= MAX_AXIS_DISTANCE; i++) {
                    te.addToBlockList(new BlockPos(te.getPos().add(f.getFrontOffsetX() * i, f.getFrontOffsetY() * i, f.getFrontOffsetZ() * i)));
                }
            }
        }
    }
}
