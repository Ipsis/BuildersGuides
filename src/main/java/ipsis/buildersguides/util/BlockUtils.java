package ipsis.buildersguides.util;

import ipsis.oss.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockUtils {

    /**
     * Return the block position of the n'th non-air block in the facing direction
     * Return the currPos value if no block found within maxDistance blcoks away
     */
    public static BlockPos getNthBlock(World world, BlockPos currPos, EnumFacing facing, int step) {
        return getNthBlock(world, currPos, facing, step, 64);
    }

    public static BlockPos getNthBlock(World world, BlockPos currPos, EnumFacing facing, int step, int maxDistance) {

        int count = 0;
        BlockPos found = new BlockPos(currPos);
        for (int i = 1; i <= maxDistance; i++) {
            BlockPos c = currPos.offset(facing, i);
            if (!world.isAirBlock(c)) {
                count ++;
                if (count == step) {
                    found = new BlockPos(c);
                    LogHelper.info("getNthBlock: " + found);
                    break;
                }
            }
        }
        return found;
    }

    public static List<BlockPos> getCenterBlockList(BlockPos p1, BlockPos p2, EnumFacing facing) {

        List<BlockPos> centerList = new ArrayList<>();
        int count = numBlocksBetween(p1, p2);
        if (count == 0) {
            /* no center */
        } else if (count % 2 == 0) {
            /* even */
            count /= 2;
            centerList.add(p1.offset(facing, count));
            centerList.add(p1.offset(facing, count + 1));
        } else {
            /* odd */
            centerList.add(p1.offset(facing, (count / 2) + 1));
        }

        LogHelper.info("getCenterBlockList: " + count + " " + centerList);
        return centerList;
    }

    /**
     * Number of blocks between the two positions
     */
    public static int numBlocksBetween(BlockPos p1, BlockPos p2) {

        if (p1.equals(p2))
            return 0;

        /* Must be a change in only one direction */
        if ((p1.getX() == p2.getX() && p1.getY() == p2.getY()) ||
            (p1.getY() == p2.getY() && p1.getZ() == p2.getZ()) ||
            (p1.getX() == p2.getX() && p1.getZ() == p2.getZ())) {

            int d = Math.abs((p1.getX() - p2.getX()) + (p1.getY() - p2.getY()) + (p1.getZ() - p2.getZ())) - 1;
            if (d < 0)
                d = 0;

            return d;
        }

        return 0;
    }
}
