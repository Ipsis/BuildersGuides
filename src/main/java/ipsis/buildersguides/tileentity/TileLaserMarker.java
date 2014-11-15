package ipsis.buildersguides.tileentity;

import ipsis.buildersguides.util.BlockPosition;
import ipsis.buildersguides.util.ChatHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileLaserMarker extends TileBaseMarker implements ITileInteract {

    private int length = this.MAX_DISTANCE;

    public TileLaserMarker() {

        super(false);
    }

    public int getLength() {
        return this.length;
    }

    public void setNextLength() {

        this.length += 8;
        if (this.length > 64)
            this.length = 8;
    }

    @Override
    public void findTargets(EntityPlayer player) {

        BlockPosition b = new BlockPosition(this.xCoord, this.yCoord, this.zCoord, this.getFacing());
        b.moveForwards(this.length);
        setTarget(this.getFacing(), b);
        worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }

    @Override
    protected void findCenter(ForgeDirection dir) {

        /* NOOP */
    }

    @Override
    public void rotateAround(ForgeDirection axis, EntityPlayer player) {

        setFacing(getFacing().getRotation(axis));
        findTargets(player);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {

        super.readFromNBT(nbttagcompound);

        this.length = nbttagcompound.getInteger("Length");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {

        super.writeToNBT(nbttagcompound);

        nbttagcompound.setInteger("Length", this.length);
    }

    /**
     * ITileInteract
     */
    @Override
    public boolean canSneakUse() {
        return true;
    }

    @Override
    public void doSneakUse(EntityPlayer player) {

        this.setNextLength();
        ChatHelper.displaySimpleMessage(player, Integer.toString(this.length) + " blocks");
        this.findTargets(player);
    }
}
