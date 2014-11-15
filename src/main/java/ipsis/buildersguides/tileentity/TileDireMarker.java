package ipsis.buildersguides.tileentity;

import ipsis.buildersguides.util.BGColor;
import ipsis.buildersguides.util.BlockPosition;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.HashSet;
import java.util.Set;

public class TileDireMarker extends TileEntity implements ITileInteract {

    @SideOnly(Side.CLIENT)
    Set<BlockPosition> blockList;

    private static final int MAX_HEIGHT = 6;
    private ForgeDirection facing;
    private BGColor color;

    public TileDireMarker() {

        this.facing = ForgeDirection.SOUTH;
        this.color = BGColor.BLACK;
    }

    public Set<BlockPosition> getBlockList() {
        return blockList;
    }


    public BGColor getColor() {

        return this.color;
    }

    public void setColor(BGColor color) {

        this.color = color;
    }

    public void setFacing(ForgeDirection dir) {

        this.facing = dir;
    }

    public ForgeDirection getFacing() {

        return this.facing;
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

        /* Doorway */
        for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
            if (d == ForgeDirection.DOWN || d == ForgeDirection.UP)
                continue;

            if (this.facing == d)
                continue;

            BlockPosition p1 = new BlockPosition(this.xCoord, this.yCoord, this.zCoord, d);
            BlockPosition p2;
            p1.moveForwards(4);
            p2 = p1.copy();
            p2.y += 1;
            blockList.add(p1);
            blockList.add(p2);
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
     * NBT
     */
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {

        super.writeToNBT(nbttagcompound);

        nbttagcompound.setInteger("Color", this.color.ordinal());
        nbttagcompound.setInteger("Facing", this.facing.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {

        super.readFromNBT(nbttagcompound);

        this.color = BGColor.getColor(nbttagcompound.getInteger("Color"));
        this.facing = ForgeDirection.getOrientation(nbttagcompound.getInteger("Facing"));
    }


    /**
     * Packet Update
     */
    @Override
    public Packet getDescriptionPacket() {

        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {

        /* This is a bit of a waste as it means recalculating the blocks
         * but this should only be called infrequently.
         * I really dont want to pass all the target blocks over the network each time.
         */
        readFromNBT(pkt.func_148857_g());
        this.calcBlocks();
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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

    public void rotateAround(ForgeDirection axis, EntityPlayer player) {

        setFacing(getFacing().getRotation(axis));
        this.calcBlocks();
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    /**
     * ITileInteract
     */
    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public boolean canSneakUse() {
        return false;
    }

    @Override
    public boolean canSneakWrench() {
        return false;
    }

    @Override
    public void doUse(EntityPlayer player) {
        this.setColor(this.getColor().getNext());
    }

    @Override
    public void doSneakUse(EntityPlayer player) {

    }

    @Override
    public void doSneakWrench(EntityPlayer player) {

    }
}
