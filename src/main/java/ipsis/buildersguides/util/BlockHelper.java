package ipsis.buildersguides.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

/**
 * A straight copy from CoFHLIB.
 * https://github.com/CoFH/CoFHLib/blob/master/src/main/java/cofh/util/BlockHelper.java
 *
 */
public final class BlockHelper {

    private BlockHelper() {

    }

    public static byte[] rotateType = new byte[4096];
    public static final int[][] SIDE_COORD_MOD = { { 0, -1, 0 }, { 0, 1, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { -1, 0, 0 }, { 1, 0, 0 } };
    public static float[][] SIDE_COORD_AABB = { { 1, -2, 1 }, { 1, 2, 1 }, { 1, 1, 1 }, { 1, 1, 2 }, { 1, 1, 1 }, { 2, 1, 1 } };
    public static final byte[] SIDE_LEFT = { 4, 5, 5, 4, 2, 3 };
    public static final byte[] SIDE_RIGHT = { 5, 4, 4, 5, 3, 2 };
    public static final byte[] SIDE_OPPOSITE = { 1, 0, 3, 2, 5, 4 };
    public static final byte[] SIDE_ABOVE = { 3, 2, 1, 1, 1, 1 };
    public static final byte[] SIDE_BELOW = { 2, 3, 0, 0, 0, 0 };

    // These assume facing is towards negative - looking AT side 1, 3, or 5.
    public static final byte[] ROTATE_CLOCK_Y = { 0, 1, 4, 5, 3, 2 };
    public static final byte[] ROTATE_CLOCK_Z = { 5, 4, 2, 3, 0, 1 };
    public static final byte[] ROTATE_CLOCK_X = { 2, 3, 1, 0, 4, 5 };

    public static final byte[] ROTATE_COUNTER_Y = { 0, 1, 5, 4, 2, 3 };
    public static final byte[] ROTATE_COUNTER_Z = { 4, 5, 2, 3, 1, 0 };
    public static final byte[] ROTATE_COUNTER_X = { 3, 2, 0, 1, 4, 5 };

    public static final byte[] INVERT_AROUND_Y = { 0, 1, 3, 2, 5, 4 };
    public static final byte[] INVERT_AROUND_Z = { 1, 0, 2, 3, 5, 4 };
    public static final byte[] INVERT_AROUND_X = { 1, 0, 3, 2, 4, 5 };

    // Map which gives relative Icon to use on a block which can be placed on any side.
    public static final byte[][] ICON_ROTATION_MAP = new byte[6][];

    static {
        ICON_ROTATION_MAP[0] = new byte[] { 0, 1, 2, 3, 4, 5 };
        ICON_ROTATION_MAP[1] = new byte[] { 1, 0, 2, 3, 4, 5 };
        ICON_ROTATION_MAP[2] = new byte[] { 3, 2, 0, 1, 4, 5 };
        ICON_ROTATION_MAP[3] = new byte[] { 3, 2, 1, 0, 5, 4 };
        ICON_ROTATION_MAP[4] = new byte[] { 3, 2, 5, 4, 0, 1 };
        ICON_ROTATION_MAP[5] = new byte[] { 3, 2, 4, 5, 1, 0 };
    }

    public static MovingObjectPosition getCurrentMovingObjectPosition(EntityPlayer player, double distance) {

        Vec3 posVec = Vec3.createVectorHelper(player.posX, player.posY, player.posZ);
        Vec3 lookVec = player.getLook(1);
        posVec.yCoord += player.getEyeHeight();
        lookVec = posVec.addVector(lookVec.xCoord * distance, lookVec.yCoord * distance, lookVec.zCoord * distance);
        return player.worldObj.rayTraceBlocks(posVec, lookVec);
    }

    public static MovingObjectPosition getCurrentMovingObjectPosition(EntityPlayer player) {

        return getCurrentMovingObjectPosition(player, player.capabilities.isCreativeMode ? 5.0F : 4.5F);
    }

    public static int getCurrentMousedOverSide(EntityPlayer player) {

        MovingObjectPosition mouseOver = getCurrentMovingObjectPosition(player);
        return mouseOver == null ? 0 : mouseOver.sideHit;
    }

    public static int determineXZPlaceFacing(EntityLivingBase living) {

        int quadrant = MathHelper.floor_double(living.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        switch (quadrant) {
            case 0:
                return 2;
            case 1:
                return 5;
            case 2:
                return 3;
            case 3:
                return 4;
        }
        return 3;
    }

    /* COORDINATE TRANSFORM */
    public static int[] getAdjacentCoordinatesForSide(MovingObjectPosition pos) {

        return getAdjacentCoordinatesForSide(pos.blockX, pos.blockY, pos.blockZ, pos.sideHit);
    }

    public static int[] getAdjacentCoordinatesForSide(int x, int y, int z, int side) {

        return new int[] { x + SIDE_COORD_MOD[side][0], y + SIDE_COORD_MOD[side][1], z + SIDE_COORD_MOD[side][2] };
    }

    public static AxisAlignedBB getAdjacentAABBForSide(int x, int y, int z, int side) {

        return AxisAlignedBB.getBoundingBox(x + SIDE_COORD_MOD[side][0], y + SIDE_COORD_MOD[side][1], z + SIDE_COORD_MOD[side][2],
                x + SIDE_COORD_AABB[side][0], y + SIDE_COORD_AABB[side][1], z + SIDE_COORD_AABB[side][2]);
    }

    public static int getLeftSide(int side) {

        return SIDE_LEFT[side];
    }

    public static int getRightSide(int side) {

        return SIDE_RIGHT[side];
    }

    public static int getOppositeSide(int side) {

        return SIDE_OPPOSITE[side];
    }

    public static int getAboveSide(int side) {

        return SIDE_ABOVE[side];
    }

    public static int getBelowSide(int side) {

        return SIDE_BELOW[side];
    }

}
