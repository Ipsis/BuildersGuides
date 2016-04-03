package ipsis.buildersguides.manager.markers;

import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.reference.Names;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.StringHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * This marker is the center of the shape, unlike the other ones
 */
public class MarkerShape extends Marker {
    @Override
    public boolean isMatch(MarkerType t) {

        return t == MarkerType.SHAPE;
    }

    @Override
    public void handleHammer(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {

        ShapeMode m = ShapeMode.getMode(te.getMode());
        if (m == ShapeMode.CIRCLE)
            handleHammerCircle(te, entityPlayer.isSneaking(), side);
        else if (m == ShapeMode.ELLIPSE)
            handleHammerEllipse(te, entityPlayer.isSneaking(), side);

        worldIn.markBlockForUpdate(te.getPos());
    }

    void handleHammerEllipse(TileEntityMarker te, boolean isSneaking, EnumFacing side) {

        if (side == EnumFacing.UP || side == EnumFacing.DOWN)
            return;

        int currRadius = te.getV(side);
        if (isSneaking) {
            currRadius -= 1;
            if (currRadius < 0) currRadius = 0;
        } else {
            currRadius += 1;
        }

        te.setV(side, currRadius);
        te.setV(side.getOpposite(), currRadius);
    }

    void handleHammerCircle(TileEntityMarker te, boolean isSneaking, EnumFacing side) {

        int currRadius = te.getV(side);
        if (isSneaking) {
            currRadius -= 1;
            if (currRadius < 0) currRadius = 0;
        } else {
            currRadius += 1;
        }

        for (EnumFacing f : EnumFacing.VALUES)
            te.setV(f, currRadius);
    }

    @Override
    public void handleConfig(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {

        if (entityPlayer.isSneaking()) {
            super.handleConfig(worldIn, te, entityPlayer, side);
        } else {
            ShapeMode m = ShapeMode.getMode(te.getMode());
            m = m.getNext();
            te.setMode(m.ordinal());
            entityPlayer.addChatComponentMessage(new ChatComponentText(m.getTranslatedMode()));

            /* clear all the values when you change shape */
            for (EnumFacing f : EnumFacing.VALUES)
                te.setV(f, 0);

            worldIn.markBlockForUpdate(te.getPos());
        }
    }

    @Override
    public String getMode(TileEntityMarker te) {

        return ShapeMode.getMode(te.getMode()).getTranslatedMode();
    }

    @Override
    public void handleServerUpdate(TileEntityMarker te) {
        super.handleServerUpdate(te);

        te.clearClientData();
        ShapeMode shapeMode = ShapeMode.getMode(te.getMode());
        if (shapeMode == ShapeMode.CIRCLE) {
            calcCircle(te, te.getPos().getX(), te.getPos().getY(), te.getPos().getZ(), te.getV(EnumFacing.SOUTH));
        } else if (shapeMode == ShapeMode.ELLIPSE) {

            int xRadius = te.getV(EnumFacing.EAST);
            int zRadius = te.getV(EnumFacing.NORTH);

            if (xRadius > 0 && zRadius > 0) {
                int x0 = te.getPos().getX() - xRadius;
                int z0 = te.getPos().getZ() - zRadius;
                int x1 = te.getPos().getX() + xRadius;
                int z1 = te.getPos().getZ() + zRadius;

                calcEllipse(te, x0, z0, x1, z1, te.getPos().getY());
            }

        }

        te.cleanBlockList();
    }

    /**
     * This is the code from the Bresenham circle algorithm
     * members.chello.at/~easyfilter/bresenham.html
     */
    void calcCircle(TileEntityMarker te, int posX, int posY, int posZ, int radius) {

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

    /**
     * This is the code from the Bresenham ellipse algorithm
     * members.chello.at/~easyfilter/bresenham.html
     */
    void calcEllipse(TileEntityMarker te, int posX0, int posZ0, int posX1, int posZ1, int posY) {

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

    public enum ShapeMode {
        CIRCLE,
        ELLIPSE;

        public static ShapeMode getMode(int id) {
            return values()[MathHelper.clamp_int(id, 0, values().length - 1)];
        }

        public ShapeMode getNext() {
            int next = (this.ordinal() + 1) % values().length;
            return values()[next];
        }

        public String getTranslatedMode() {
            return StringHelper.localize(Names.NAME, "shapemode." + this.toString());
        }
    }
}
