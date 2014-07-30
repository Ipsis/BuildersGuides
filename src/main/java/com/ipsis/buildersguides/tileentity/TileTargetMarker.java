package com.ipsis.buildersguides.tileentity;

import com.ipsis.buildersguides.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;

public class TileTargetMarker extends TileBaseMarker {

    public TileTargetMarker() {

        super(true);
    }

    @Override
    public void rotateAround(ForgeDirection axis, EntityPlayer player) {

        setFacing(getFacing().getRotation(axis));
        findTargets(player);
    }

    @Override
    public boolean canSneakWrench() {
        return true;
    }

    @Override
    public void doSneakWrench(EntityPlayer player) {

        findTargets(player);
    }
}
