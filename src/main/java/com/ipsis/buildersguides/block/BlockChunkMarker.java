package com.ipsis.buildersguides.block;

import com.ipsis.buildersguides.reference.Reference;
import com.ipsis.buildersguides.tileentity.TileChunkMarker;
import com.ipsis.buildersguides.util.LogHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockChunkMarker extends BlockBG implements ITileEntityProvider {

    public BlockChunkMarker() {

        super();
        setBlockName("chunkMarker");
    }

    @Override
    public boolean isOpaqueCube() {

        return false;
    }

    @SideOnly(Side.CLIENT)
    private IIcon sideIcon;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(Reference.MOD_ID + ":base");
        sideIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int metadata) {

        if (side == ForgeDirection.DOWN.ordinal() || side == ForgeDirection.UP.ordinal())
            return this.blockIcon;

        return sideIcon;
    }

    /**
     * ITileEntityProvider
     */
    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {

        return new TileChunkMarker();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {

        if (!world.isRemote) {
            if (world.getTileEntity(x, y, z) instanceof TileChunkMarker) {

                TileChunkMarker te = (TileChunkMarker) world.getTileEntity(x, y, z);
                te.setWhereAmI();
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x,	int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ) {

        if (!world.isRemote) {

            TileEntity te = world.getTileEntity(x, y, z);
            if (te != null && te instanceof TileChunkMarker) {
                TileChunkMarker teMarker = (TileChunkMarker) te;
                if (!entityPlayer.isSneaking()) {
                    teMarker.doUse(entityPlayer);
                    world.markBlockForUpdate(x, y, z);
                }
            }
        }

        return true;
    }
}
