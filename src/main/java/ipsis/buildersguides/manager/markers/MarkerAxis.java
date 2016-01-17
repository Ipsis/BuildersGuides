package ipsis.buildersguides.manager.markers;

import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.oss.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class MarkerAxis extends Marker {

    @Override
    public boolean isMatch(MarkerType t) {
        return t == MarkerType.AXIS;
    }

    @Override
    public void handleHammer(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {
        /* NOOP */
        LogHelper.info("handleHammer: AXIS");
    }

    @Override
    public void handleConfig(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {
        /* NOOP */
        LogHelper.info("handleConfig: AXIS");
    }

    @Override
    public boolean isFaceEnabled(TileEntityMarker te, EnumFacing f) {
        return true;
    }


    @Override
    public void handleServerUpdate(TileEntityMarker te) {

        te.clearBlocklist();
        te.clearCenterList();

        for (EnumFacing f : EnumFacing.VALUES) {
            for (int i = 1; i <= 64; i ++) {
                te.addToBlockList(new BlockPos(te.getPos().add(f.getFrontOffsetX() * i, f.getFrontOffsetY() * i, f.getFrontOffsetZ() * i)));
            }
        }
    }
}
