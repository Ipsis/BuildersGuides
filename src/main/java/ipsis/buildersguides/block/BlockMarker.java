package ipsis.buildersguides.block;

import ipsis.buildersguides.item.ItemMallet;
import ipsis.buildersguides.manager.MarkerManager;
import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.init.ModItems;
import ipsis.buildersguides.item.ItemMarkerCard;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.ItemStackHelper;
import ipsis.buildersguides.util.WorldHelper;
import ipsis.oss.LogHelper;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import sun.rmi.runtime.Log;

public class BlockMarker extends BlockContainerBG {

    public static final String BASENAME = "marker";

    public BlockMarker() {
        super(Material.ground, BASENAME);
    }

    @Override
    public int getRenderType() { return 3; }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TileEntityMarker();
    }

    @Override
    public boolean isOpaqueCube() {

        return false;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {

        TileEntity te = worldIn.getTileEntity(pos);
        if (te != null && te instanceof TileEntityMarker) {
            if (((TileEntityMarker) te).getType() != MarkerType.BLANK)
                ItemStackHelper.spawnInWorld(worldIn, pos, ItemMarkerCard.getItemStack(((TileEntityMarker) te).getType()));
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {

        TileEntity te = worldIn.getTileEntity(pos);
        if (te != null && te instanceof TileEntityMarker) {
            ((TileEntityMarker) te).setFacing(BlockPistonBase.getFacingFromEntity(worldIn, pos, placer));
            worldIn.markBlockForUpdate(pos);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {

        TileEntity te = worldIn.getTileEntity(pos);
        if (te == null || !(te instanceof TileEntityMarker))
            return false;

        ItemStack heldItem = playerIn.getCurrentEquippedItem();
        if (heldItem == null || heldItem.getItem() != ModItems.itemMallet)
            return false;

        if (WorldHelper.isServer(worldIn)) {
            // side is the side of the block hit, but we want to act on the opposite side
            // ie. hitting the front face, pushes blocks out the back
            ItemMallet.MalletMode currMode = ItemMallet.getMode(heldItem);
            MarkerType t = ((TileEntityMarker) te).getType();
            MarkerManager.handleMalletMode(worldIn, (TileEntityMarker) te, side.getOpposite(), currMode, playerIn.isSneaking());
        }

        return true;
    }

    @Override
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {

        TileEntity te = world.getTileEntity(pos);
        if (te == null || !(te instanceof TileEntityMarker))
            return false;

        if (WorldHelper.isServer(world)) {
            ((TileEntityMarker) te).rotateTile(axis);
            return true;
        }

        return false;
    }
}
