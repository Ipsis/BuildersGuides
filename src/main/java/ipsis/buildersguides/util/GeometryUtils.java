package ipsis.buildersguides.util;

import ipsis.buildersguides.tileentity.TileEntityMarker;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class GeometryUtils {

    /**
     * Algorithm from Graphics Gems - Spheres-To-Voxels Conversion
     */
    public static void calcDome(TileEntityMarker te, int posX, int posY, int posZ, int radius) {

        int x = 0;
        int y = radius;
        int d = 2 * (1 - radius);
        int limit = 0;

        while (y >= limit) {

            if (d < 0) {
                int s = (2 * d) + (2 * y) - 1;
                if (s > 0) {
                    calcCircle(te, posX, posY + y, posZ, x);
                    x = x + 1;
                    y = y - 1;
                    d = d + (2 * x) - (2 * y) + 2;
                } else {
                    x = x + 1;
                    d = d + (2 * x) + 1;
                }
            } else if (d > 0) {
                int s = (2 * d) - (2 * x) - 1;
                if (s > 0) {
                    calcCircle(te, posX, posY + y, posZ, x);
                    y = y - 1;
                    d = d - (2 * y) + 1;
                } else {
                    calcCircle(te, posX, posY + y, posZ, x);
                    x = x + 1;
                    y = y - 1;
                    d = d + (2 * x) - (2 * y) + 2;
                }
            } else {
                calcCircle(te, posX, posY + y, posZ, x);
                x = x + 1;
                y = y - 1;
                d = d + (2 * x) - (2 * y) + 2;
            }
        }
    }
    public static void calcCylinder(TileEntityMarker te, int posX, int posY, int posZ, int radius, int height) {

        for (int i = 0; i < height; i++)
            calcCircle(te, posX, posY + i, posZ, radius);
    }

    public static void calcSphere(TileEntityMarker te, int posX, int posY, int posZ, int radius) {

        calcDome(te, posX, posY, posZ, radius);

        /**
         * Now do the bottom half
         */
        Set<BlockPos> bottomList = new HashSet<BlockPos>();
        for (BlockPos p : te.getBlockList()) {
            int newYPos = te.getPos().getY() - (p.getY() - te.getPos().getY());
            BlockPos p2 = new BlockPos(p.getX(), newYPos, p.getZ());
            bottomList.add(p2);
        }

        te.addToBlockList(bottomList);
    }

    /**
     * This is the code from the Bresenham circle algorithm
     * members.chello.at/~easyfilter/bresenham.html
     */
    public static void calcCircle(TileEntityMarker te, int posX, int posY, int posZ, int radius) {

        int xm = posX;
        int ym = posZ;

        int r = radius;
        int x = -r;
        int y = 0;
        int err = 2 - 2 * r;

        do {
            te.addToBlockList(new BlockPos(xm - x, posY, ym + y));
            te.addToBlockList(new BlockPos(xm - y, posY, ym - x));
            te.addToBlockList(new BlockPos(xm + x, posY, ym - y));
            te.addToBlockList(new BlockPos(xm + y, posY, ym + x));

            r = err;
            if (r <= y)
                err += ++y * 2 + 1;
            if (r > x || err > y)
                err += ++x * 2 + 1;
        } while (x < 0);
    }

    public static void calcLine(TileEntityMarker te, int posX0, int posZ0, int posX1, int posZ1, int posY) {

        int x0 = posX0;
        int y0 = posZ0;
        int x1 = posX1;
        int y1 = posZ1;

        int dx = Math.abs(x1 - x0);
        int sx = x0 < x1 ? 1 : -1;
        int dy = -Math.abs(y1 - y0);
        int sy = y0 < y1 ? 1 : -1;
        int err = dx + dy;
        int e2;

        for (;;) {
            te.addToBlockList(new BlockPos(x0, posY, y0));
            e2 = 2 * err;
            if (e2 >= dy) {
                if (x0 == x1)
                    break;

                err += dy;
                x0 += sx;
            }

            if (e2 <= dx) {
                if (y0 == y1)
                    break;

                err += dx;
                y0 += sy;
            }
        }
    }

    /**
     * This is the code from the Bresenham ellipse algorithm
     * members.chello.at/~easyfilter/bresenham.html
     */
    public static void calcEllipse(TileEntityMarker te, int posX0, int posZ0, int posX1, int posZ1, int posY) {

        int x0 = posX0;
        int y0 = posZ0;
        int x1 = posX1;
        int y1 = posZ1;

        int a = Math.abs(x1 - x0);
        int b = Math.abs(y1 - y0);
        int b1 = b & 1;
        long dx = 4 * (1 - a) * b * b; /* values of diameter */
        long dy = 4 * (b1 + 1) * a * a; /* error increment */
        long err = dx + dy + b1 * a * a; /* error of 1.step */
        long e2;

        // If called with swapped points
        if (x0 > x1) {
            x0 = x1;
            x1 += a;
        }
        if (y0 > y1)
            y0 = y1;

        y0 += (b + 1) / 2;
        y1 = y0 - b1;

        a *= 8 * a;
        b1 = 8 * b * b;

        do {
            te.addToBlockList(new BlockPos(x1, posY, y0));
            te.addToBlockList(new BlockPos(x0, posY, y0));
            te.addToBlockList(new BlockPos(x0, posY, y1));
            te.addToBlockList(new BlockPos(x1, posY, y1));

            e2 = 2 * err;
            if (e2 <= dy) {
                y0++;
                y1--;
                err += dy += a;
            }

            if (e2 >= dx || 2 * err > dy) {
                x0++;
                x1--;
                err += dx += b1;
            }
        } while (x0 <= x1);

        while (y0 - y1 < b) {
            te.addToBlockList(new BlockPos(x0 - 1, posY, y0));
            te.addToBlockList(new BlockPos(x1 + 1, posY, y0++));
            te.addToBlockList(new BlockPos(x0 - 1, posY, y1));
            te.addToBlockList(new BlockPos(x1 + 1, posY, y1--));
        }
    }
}
