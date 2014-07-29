package com.ipsis.buildersguides.tileentity;

import com.ipsis.buildersguides.util.BlockPosition;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TileDireMarker extends TileEntity {

    @SideOnly(Side.CLIENT)
    Set<BlockPosition> blockList;

    private static final int MAX_HEIGHT = 6;

    public TileDireMarker() {

    }

    public Set<BlockPosition> getBlockList() {
        return blockList;
    }

    public void calcBlocks() {

        if (!worldObj.isRemote)
            return;

        blockList = new HashSet<BlockPosition>();

        for (int yc = 0; yc < MAX_HEIGHT; yc++) {
            for (int xc = -4; xc <= 4; xc++) {
                if (yc > 1 || (yc <= 1 && xc != 0)) {
                    blockList.add(new BlockPosition(this.xCoord + xc, this.yCoord + yc, this.zCoord - 4));
                    blockList.add(new BlockPosition(this.xCoord + xc, this.yCoord + yc, this.zCoord + 4));
                }
            }

            for (int zc = -4; zc <= 4; zc++) {
                if (yc > 1 || (yc <= 1 && zc != 0)) {
                    blockList.add(new BlockPosition(this.xCoord - 4, this.yCoord + yc, this.zCoord + zc));
                    blockList.add(new BlockPosition(this.xCoord + 4, this.yCoord + yc, this.zCoord + zc));
                }
            }
        }

        /* Roof */
        for (int xc = -3; xc <= 3; xc++) {
                blockList.add(new BlockPosition(this.xCoord + xc, this.yCoord + MAX_HEIGHT - 1, this.zCoord - 3));
                blockList.add(new BlockPosition(this.xCoord + xc, this.yCoord + MAX_HEIGHT - 1, this.zCoord + 3));
        }

        for (int zc = -3; zc <= 3; zc++) {
                blockList.add(new BlockPosition(this.xCoord - 3, this.yCoord + MAX_HEIGHT - 1, this.zCoord + zc));
                blockList.add(new BlockPosition(this.xCoord + 3, this.yCoord + MAX_HEIGHT - 1, this.zCoord + zc));
        }

        for (int d = -2; d <= 2; d++) {
            blockList.add(new BlockPosition(this.xCoord + d, this.yCoord + MAX_HEIGHT - 1, this.zCoord));
            blockList.add(new BlockPosition(this.xCoord, this.yCoord + MAX_HEIGHT - 1, this.zCoord + d));
        }
    }

    /**
     * Need to render the block when it is not in view.
     */
    @Override
    public AxisAlignedBB getRenderBoundingBox() {

        return AxisAlignedBB.getBoundingBox(
                xCoord - 16, yCoord - 16, zCoord - 16,
                xCoord + 16, yCoord + 16, zCoord + 16);
    }
}
