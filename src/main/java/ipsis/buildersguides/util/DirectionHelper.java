package ipsis.buildersguides.util;

import net.minecraft.block.BlockPistonBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class DirectionHelper {

    /**
     * Get the direction that is facing the player
     */
    public static ForgeDirection getFacing(EntityLivingBase entityLiving) {

        int facing = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        if (facing == 0)
            return ForgeDirection.NORTH;
        else if (facing == 1)
            return ForgeDirection.EAST;
        else if (facing == 2)
            return ForgeDirection.SOUTH;
        else
            return ForgeDirection.WEST;
    }

    public static ForgeDirection getFacing6(World world, int x, int y, int z, EntityLivingBase entityLiving) {

        int d = BlockPistonBase.determineOrientation(world, x, y, z, entityLiving);
        return ForgeDirection.getOrientation(d);
    }

    public static boolean isY(ForgeDirection dir) {

        if (dir == ForgeDirection.UP || dir == ForgeDirection.DOWN)
            return true;

        return false;
    }

    public static boolean isZ(ForgeDirection dir) {

        if (dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH)
            return true;

        return false;
    }

    public static boolean isX(ForgeDirection dir) {

        if (dir == ForgeDirection.EAST || dir == ForgeDirection.WEST)
            return true;

        return false;
    }
}
