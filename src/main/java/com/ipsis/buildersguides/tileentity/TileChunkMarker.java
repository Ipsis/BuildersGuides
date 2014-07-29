package com.ipsis.buildersguides.tileentity;

import com.ipsis.buildersguides.util.BGColor;
import com.ipsis.buildersguides.util.BlockPosition;
import com.ipsis.buildersguides.util.LogHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

public class TileChunkMarker extends TileEntity {

    private BlockPosition chunk;
    private boolean isSlimeChunk;
    private BGColor color;

    public TileChunkMarker() {

        this.isSlimeChunk = false;
        this.color = BGColor.BLACK;
    }

    public void setWhereAmI(EntityLivingBase entity) {

        /* Chunks are 16 x 16 */
        this.chunk = new BlockPosition(entity.chunkCoordX << 4, this.yCoord, entity.chunkCoordZ << 4);
    }

    public int getChunkX() {
        if (this.chunk != null)
            return this.chunk.x;
        else
            return 0;
    }

    public int getChunkZ() {
        if (this.chunk != null)
            return this.chunk.z;
        else
            return 0;
    }

    public boolean hasChunkInfo() {
        return this.chunk != null;
    }

    public BGColor getColor() {

        return this.color;
    }

    public void setColor(BGColor color) {

        this.color = color;
    }

    /**
     * NBT
     */
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {

        super.writeToNBT(nbttagcompound);

        nbttagcompound.setInteger("Color", this.color.ordinal());

        NBTTagCompound tc1 = new NBTTagCompound();
        this.chunk.writeToNBT(tc1);
        nbttagcompound.setTag("Chunk", tc1);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {

        super.readFromNBT(nbttagcompound);

        this.color = BGColor.getColor(nbttagcompound.getInteger("Color"));
        this.chunk = new BlockPosition((NBTTagCompound)nbttagcompound.getTag("Chunk"));
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

        readFromNBT(pkt.func_148857_g());
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
}
