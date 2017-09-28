package ipsis.buildersguides.util;

import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class ItemStackHelper {

    private static Random RANDOM = new Random();

    public static void spawnInWorld(World world, BlockPos pos, ItemStack itemStack) {

        InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
    }
}
