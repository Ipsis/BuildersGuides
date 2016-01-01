package ipsis.buildersguides.manager.markers;

import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.oss.LogHelper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.List;

public class SpacingMarker extends Marker {
    @Override
    public boolean isMatch(MarkerType t) {

        return t == MarkerType.SPACING;
    }

    @Override
    public void handleHammer(World worldIn, TileEntityMarker te, EnumFacing side, boolean isSneaking) {

        LogHelper.info("handleHammer: SPACING");

        if (isSneaking) {
            te.setV(side, te.getV(side) - 1);
            if (te.getV(side) < 0)
               te.setV(side, 0);
        } else {
            te.setV(side, te.getV(side) + 1);
        }
        worldIn.markBlockForUpdate(te.getPos());
    }

    @Override
    public void handleConfig(World worldIn, TileEntityMarker te, EnumFacing side, boolean isSneaking) {

        LogHelper.info("handleConfig: SPACING");
    }

    @Override
    public void handleServerUpdate(TileEntityMarker te) {

        List<BlockPos> blockList = te.getBlockList();
        blockList.clear();

        for (EnumFacing f : EnumFacing.values()) {
            int v = te.getV(f);
            if (v != 0) {
                for (int i = (v + 1); i < 64; i += (v + 1)) {
                    BlockPos p = te.getPos().offset(f.getOpposite(), i);
                    te.addToBlockList(p);
//                    blockList.add(p);
                }
            }
        }
    }
}
