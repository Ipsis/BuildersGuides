package ipsis.buildersguides.tileentity;

import ipsis.buildersguides.util.BGColor;
import ipsis.buildersguides.util.BlockPosition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.chunk.Chunk;

public class TileChunkMarker extends TileEntity implements ITileInteract {

    private BlockPosition chunk;
    private boolean isSlimeChunk;
    private BGColor color;

    public TileChunkMarker() {

        this.isSlimeChunk = false;
        this.color = BGColor.BLACK;
    }

    public void setWhereAmI() {

        /* Block location to chunk */
        /* Straight from the EntitySlime.java logic */
        Chunk currChunk = this.worldObj.getChunkFromBlockCoords(MathHelper.floor_double(this.xCoord), MathHelper.floor_double(this.zCoord));
        this.chunk = new BlockPosition(currChunk.xPosition << 4, this.yCoord, currChunk.zPosition << 4);

        if (currChunk.getRandomWithSeed(987234911L).nextInt(10) == 0)
            this.isSlimeChunk = true;
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

    public boolean isSlimeChunk() {
        return this.isSlimeChunk;
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
