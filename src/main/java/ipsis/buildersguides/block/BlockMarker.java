package ipsis.buildersguides.block;

import ipsis.buildersguides.common.UnlistedPropertyBoolean;
import ipsis.buildersguides.common.UnlistedPropertyEnumFacing;
import ipsis.buildersguides.init.ModBlocks;
import ipsis.buildersguides.item.ItemMallet;
import ipsis.buildersguides.manager.MarkerManager;
import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.init.ModItems;
import ipsis.buildersguides.item.ItemMarkerCard;
import ipsis.buildersguides.reference.Reference;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.BlockUtils;
import ipsis.buildersguides.util.ItemStackHelper;
import ipsis.buildersguides.util.WorldHelper;
import ipsis.oss.client.ModelHelper;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMarker extends BlockContainerBG {

    public static final String BASENAME = "marker";

    public static final UnlistedPropertyBoolean NORTH = new UnlistedPropertyBoolean("NORTH");
    public static final UnlistedPropertyBoolean SOUTH = new UnlistedPropertyBoolean("SOUTH");
    public static final UnlistedPropertyBoolean EAST = new UnlistedPropertyBoolean("EAST");
    public static final UnlistedPropertyBoolean WEST = new UnlistedPropertyBoolean("WEST");
    public static final UnlistedPropertyBoolean UP = new UnlistedPropertyBoolean("UP");
    public static final UnlistedPropertyBoolean DOWN = new UnlistedPropertyBoolean("DOWN");
    public static final UnlistedPropertyEnumFacing FACING = new UnlistedPropertyEnumFacing("FACING");

    public BlockMarker() {

        super(Material.ground, BASENAME);
        setRegistryName(Reference.MOD_ID_LOWER, BASENAME);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initModel() {

        ModelHelper.registerBlock(ModBlocks.blockMarker, BASENAME);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        IUnlistedProperty[] unlistedProperties = new IUnlistedProperty[]{
                NORTH, SOUTH, EAST, WEST, UP, DOWN, FACING
        };
        return new ExtendedBlockState(this, new IProperty[0], unlistedProperties);
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        if (state instanceof IExtendedBlockState) {
            IExtendedBlockState extendedBlockState = (IExtendedBlockState)state;
            TileEntityMarker te = (TileEntityMarker)world.getTileEntity(pos);
            boolean north = te.isFaceEnabled(EnumFacing.NORTH);
            boolean south = te.isFaceEnabled(EnumFacing.SOUTH);
            boolean east = te.isFaceEnabled(EnumFacing.EAST);
            boolean west = te.isFaceEnabled(EnumFacing.WEST);
            boolean up = te.isFaceEnabled(EnumFacing.UP);
            boolean down = te.isFaceEnabled(EnumFacing.DOWN);
            EnumFacing f = te.getFacing();

            return extendedBlockState
                    .withProperty(NORTH, north)
                    .withProperty(SOUTH, south)
                    .withProperty(EAST, east)
                    .withProperty(WEST, west)
                    .withProperty(UP, up)
                    .withProperty(DOWN, down)
                    .withProperty(FACING, f.ordinal());
        }

        return state;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {

        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        return new TileEntityMarker();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {

        return false;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState state) {

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
            ((TileEntityMarker) te).setFacing(BlockPistonBase.getFacingFromEntity(pos, placer));
            BlockUtils.markBlockForUpdate(worldIn, pos);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {

        TileEntity te = worldIn.getTileEntity(pos);
        if (te == null || !(te instanceof TileEntityMarker))
            return false;

        if (heldItem == null || heldItem.getItem() != ModItems.itemMallet)
            return false;

        if (WorldHelper.isServer(worldIn)) {
            // side is the side of the block hit, but we want to act on the opposite side
            // ie. hitting the front face, pushes blocks out the back
            ItemMallet.MalletMode currMode = ItemMallet.getMode(heldItem);
            MarkerType t = ((TileEntityMarker) te).getType();
            MarkerManager.handleMalletMode(worldIn, (TileEntityMarker) te, playerIn, side.getOpposite(), currMode);
        }

        return true;
    }

    @Override
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {

        /** TODO need the player for this to work with the MarkerManager
         *
        TileEntity te = world.getTileEntity(pos);
        if (te == null || !(te instanceof TileEntityMarker))
            return false;

        if (WorldHelper.isServer(world)) {
            ((TileEntityMarker) te).rotateTile(axis);
            return true;
        } */

        return false;
    }
}
