package ipsis.buildersguides.tileentity;

import ipsis.buildersguides.util.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileAxisMarker extends TileMultiMarker {

    public TileAxisMarker() {

        super(false);
        mode = BGAxis.X_Y_Z;
    }

    private BGAxis mode;

    @Override
    public BlockPosition getCenter(ForgeDirection dir) {

        return null;
    }

    @Override
    protected void setCenter(ForgeDirection dir, BlockPosition pos) {

        /* NOOP */
    }
    @Override
    public boolean hasCenter() {

        return false;
    }

    @Override
    public boolean hasCenter(ForgeDirection dir) {

        return false;
    }

    @Override
    public void findTargets(EntityPlayer player) {

        setTarget(ForgeDirection.DOWN, new BlockPosition(this.xCoord, this.yCoord - MAX_DISTANCE, this.zCoord));
        setTarget(ForgeDirection.UP, new BlockPosition(this.xCoord, this.yCoord + MAX_DISTANCE, this.zCoord));
        setTarget(ForgeDirection.EAST, new BlockPosition(this.xCoord + MAX_DISTANCE, this.yCoord, this.zCoord));
        setTarget(ForgeDirection.WEST, new BlockPosition(this.xCoord - MAX_DISTANCE, this.yCoord, this.zCoord));
        setTarget(ForgeDirection.SOUTH, new BlockPosition(this.xCoord, this.yCoord, this.zCoord + this.MAX_DISTANCE));
        setTarget(ForgeDirection.NORTH, new BlockPosition(this.xCoord, this.yCoord, this.zCoord - this.MAX_DISTANCE));

        worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }

    @Override
    public BlockPosition getTarget(ForgeDirection dir) {

        if (!this.mode.isValidDir(this.mode, dir))
            return null;

        return super.getTarget(dir);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {

        super.readFromNBT(nbttagcompound);

        this.mode = BGAxis.getAxis(nbttagcompound.getInteger("Mode"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {

        super.writeToNBT(nbttagcompound);

        nbttagcompound.setInteger("Mode", this.mode.ordinal());
    }

    @Override
    public boolean canSneakUse() {
        return true;
    }

    @Override
    public void doSneakUse(EntityPlayer player) {

        /* Change the axis */
        this.mode = this.mode.getNext();
        ChatHelper.displaySimpleMessage(player, this.mode.toString());
        worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }
}
