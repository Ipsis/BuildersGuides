package ipsis.buildersguides.item;

import ipsis.buildersguides.reference.Names;
import ipsis.buildersguides.tileentity.ITileInteract;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * TODO implement IToolWrench
 */
public class ItemAllenKey extends ItemBG {

    public ItemAllenKey() {

        super();
        this.setUnlocalizedName(Names.Items.ITEM_ALLEN_KEY);
        this.setMaxStackSize(1);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {

        Block block = world.getBlock(x, y, z);
        if (block == null)
            return false;

        if (!world.isRemote && player.isSneaking()) {

            TileEntity te = world.getTileEntity(x, y, z);
            if (te != null && te instanceof ITileInteract && ((ITileInteract) te).canSneakWrench()) {
                ((ITileInteract) te).doSneakWrench(player);
                return true;
            }
        }

        /* Rotate the block in the standard way */
        if (block.rotateBlock(world, x, y, z, ForgeDirection.getOrientation(side))) {
            player.swingItem();
            return !world.isRemote;
        }

        return false;
    }

    public boolean canWrench(EntityPlayer player, int x, int y, int z) {

        return true;
    }

    public void wrenchUsed(EntityPlayer player, int x, int y, int z) {

    }

}
