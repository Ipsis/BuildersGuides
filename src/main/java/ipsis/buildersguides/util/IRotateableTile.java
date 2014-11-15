package ipsis.buildersguides.util;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * A straight copy from CoFHLIB.
 * https://github.com/CoFH/CoFHLib/blob/master/src/main/java/cofh/util/position/IRotateableTile.java
 *
 */
public interface IRotateableTile {

    public boolean canRotate();

    public boolean canRotate(ForgeDirection axis);

    public void rotate(ForgeDirection axis);

    public void rotateDirectlyTo(int facing);

    public ForgeDirection getDirectionFacing();

}
