package com.ipsis.buildersguides.tileentity;

import com.ipsis.buildersguides.util.BlockPosition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;

public class TileSkyMarker extends TileMultiMarker {

    public TileSkyMarker() {

        super(false);
    }

    @Override
    public void findTargets(EntityPlayer player) {

        BlockPosition b = new BlockPosition(this.xCoord, this.yCoord, this.zCoord, ForgeDirection.UP);
        int diff = player.worldObj.getHeight() - this.yCoord;
        b.moveForwards(diff);
        setTarget(ForgeDirection.UP, b);
    }

    @Override
    protected void findCenter(ForgeDirection dir) {

        /* NOOP */
    }
}
