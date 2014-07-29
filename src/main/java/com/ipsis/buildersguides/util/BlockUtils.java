package com.ipsis.buildersguides.util;

import com.ipsis.buildersguides.block.BlockTarget;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLever;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockUtils {

    public static boolean isXAligned(BlockPosition a, BlockPosition b) {
        return a.x == b.x;
    }

    public static boolean isYAligned(BlockPosition a, BlockPosition b) {
        return a.y == b.y;
    }

    public static boolean isZAligned(BlockPosition a, BlockPosition b) {
        return a.z == b.z;
    }

    public static boolean isOnXYPlane(BlockPosition a, BlockPosition b) {
        return a == b ? a.z == b.z : false;
    }

    public static boolean isOnXZPlane(BlockPosition a, BlockPosition b) {
        return a == b ? a.y == b.y : false;
    }

    public static boolean isOnYZPlane(BlockPosition a, BlockPosition b) {
        return a == b ? a.x == b.x : false;
    }

    public static boolean isOneDirection(BlockPosition a, BlockPosition b) {

        if ((isXAligned(a, b) && isYAligned(a,b) && !isZAligned(a, b)) ||
                (isXAligned(a, b) && !isYAligned(a,b) && isZAligned(a, b)) ||
                (!isXAligned(a, b) && isYAligned(a,b) && isZAligned(a, b)))
            return true;
        else
            return false;
    }

    /**
     * Get the number of blocks between two block
     * Only if the two blocks are aligned along one axis
     */
    public static int blocksBetween(BlockPosition a, BlockPosition b) {

        if (a.equals(b))
            return 0;

        if ((a.x == b.x && a.y == b.y) || (a.x == b.x && a.z == b.z) || (a.y == b.y && a.z == b.z))
            return Math.abs((a.x - b.x) + (a.y - b.y) + (a.z - b.z)) - 1;

        return 0;
    }

    public static BlockPosition getFirstBlock(World world, int x, int y, int z, ForgeDirection facing, int maxDistance, boolean isMarker) {

        if (facing == ForgeDirection.UNKNOWN)
            return null;

        BlockPosition r = null;
        BlockPosition p = new BlockPosition(x, y, z, facing);
        for (int i = 0; i < maxDistance; i++) {

            p.moveForwards(1);
            Block b = world.getBlock(p.x, p.y, p.z);

            if (b == null || world.isAirBlock(p.x, p.y, p.z) || b instanceof BlockLever)
                continue;

            if ((isMarker && b instanceof BlockTarget) || !isMarker) {
                r = p;
                break;
            }
        }

        /* Must be at least 1 block between */
        p = new BlockPosition(x, y, z, facing);
        p.moveForwards(1);
        if (r != null && r.equals(p))
            r = null;

        return r;
    }

    public static BlockPosition getCenterBlock(int x, int y, int z, int x1, int y1, int z1, ForgeDirection facing) {

        if (facing == ForgeDirection.UNKNOWN)
            return null;

        int count = numBlocksBetween(x, y, z, x1, y1, z1);
        if (count == 0 || count == 1 || count % 2 == 0)
            return null;

        BlockPosition center = new BlockPosition(x, y, z, facing);
        center.moveForwards((count / 2) + 1);
        return center;
    }

    public static int numBlocksBetween(int x, int y, int z, int x1, int y1, int z1) {

        if (x == x1 && y == y1 && z == z1)
            return 0;

        /* Must be a change in only one direction */
        if (x == x1 && y == y1 || y == y1 && z == z1 || x == x1 && z == z1) {
            int d = Math.abs((x - x1) + (y - y1) + (z - z1)) - 1;
            if (d < 0)
                d = 0;

            return d;
        }

        return 0;
    }

}
