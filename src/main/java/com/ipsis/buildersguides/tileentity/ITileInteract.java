package com.ipsis.buildersguides.tileentity;

import net.minecraft.entity.player.EntityPlayer;

public interface ITileInteract {

    public abstract boolean canUse();
    public abstract boolean canSneakUse();
    public abstract boolean canSneakWrench();

    public abstract void doUse(EntityPlayer player);
    public abstract void doSneakUse(EntityPlayer player);
    public abstract void doSneakWrench(EntityPlayer player);
}
