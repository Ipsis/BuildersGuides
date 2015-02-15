package ipsis.buildersguides.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.buildersguides.util.BlockPosition;
import ipsis.buildersguides.util.LogHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import sun.org.mozilla.javascript.internal.ast.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TileGhostMarker extends TileEntity {

    private int[] sizes = new int[6];

    public TileGhostMarker() {

        for (int i = 0; i < sizes.length; i++)
            sizes[i] = 0;
    }

    public void pullSide(ForgeDirection dir) {

        sizes[dir.ordinal()]--;
        if (sizes[dir.ordinal()] < 0)
            sizes[dir.ordinal()] = 0;

        LogHelper.info("pushSide: " + dir + ":" + sizes[dir.ordinal()]);
    }

    public void pushSide(ForgeDirection dir) {

        sizes[dir.ordinal()]++;
        if (sizes[dir.ordinal()] > 64)
            sizes[dir.ordinal()] = 64;

        LogHelper.info("pullSide: " + dir + ":" + sizes[dir.ordinal()]);
    }

    public List<BlockPosition> getBlockList() {

        List<BlockPosition> blocks = new ArrayList<BlockPosition>();

        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            int len = sizes[dir.ordinal()];
            for (int i = 0; i < len; i++) {
                BlockPosition b = new BlockPosition(this.xCoord, this.yCoord, this.zCoord);
                b.step(dir, i + 1);
                blocks.add(b);
            }
        }

        return blocks;
    }
}
