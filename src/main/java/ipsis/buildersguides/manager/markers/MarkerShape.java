package ipsis.buildersguides.manager.markers;

import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.reference.Names;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.BlockUtils;
import ipsis.buildersguides.util.StringHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import static ipsis.buildersguides.util.GeometryUtils.*;

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
        if (m == ShapeMode.CIRCLE || m == ShapeMode.SPHERE || m == ShapeMode.DOME)
            handleRadius(te, entityPlayer.isSneaking(), entityPlayer, side);
        else if (m == ShapeMode.ELLIPSE)
            handleHammerEllipse(te, entityPlayer.isSneaking(), entityPlayer, side);
        else if (m == ShapeMode.CYLINDER)
            handleHammerCylinder(te, entityPlayer.isSneaking(), entityPlayer, side);
        /*
        else if (m == ShapeMode.LINE)
            handleHammerLine(te, entityPlayer.isSneaking(), entityPlayer, side); */

        BlockUtils.markBlockForUpdate(worldIn, te.getPos());
    }

    void handleHammerEllipse(TileEntityMarker te, boolean isSneaking,EntityPlayer entityPlayer, EnumFacing side) {

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

        entityPlayer.sendMessage(new TextComponentString(
                String.format("%s: %d x %d",
                        getMode(te),
                        te.getV(EnumFacing.WEST), te.getV(EnumFacing.SOUTH))));
    }

    void handleHammerLine(TileEntityMarker te, boolean isSneaking,EntityPlayer entityPlayer, EnumFacing side) {

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

        entityPlayer.sendMessage(new TextComponentString(
                String.format("%s: %d x %d",
                        getMode(te),
                        te.getV(EnumFacing.WEST), te.getV(EnumFacing.SOUTH))));
    }

    void handleHammerCylinder(TileEntityMarker te, boolean isSneaking, EntityPlayer entityPlayer, EnumFacing side) {

        if (side == EnumFacing.UP) {
            if (isSneaking) {
                te.setV(side, te.getV(side) - 1);
                if (te.getV(side) < 0)
                    te.setV(side, 0);
            } else {
                te.setV(side, te.getV(side) + 1);
            }
        } else if (side == EnumFacing.DOWN) {
            // NOOP
        } else {

            int currRadius = te.getV(side);
            if (isSneaking) {
                currRadius -= 1;
                if (currRadius < 0) currRadius = 0;
            } else {
                currRadius += 1;
            }

            for (EnumFacing f : EnumFacing.VALUES) {
                if (f != EnumFacing.UP && f != EnumFacing.DOWN)
                    te.setV(f, currRadius);
            }
        }

        entityPlayer.sendMessage(new TextComponentString(
                String.format("%s: radius %d height %d", getMode(te), te.getV(EnumFacing.WEST), te.getV(EnumFacing.UP))));
    }

    void handleRadius(TileEntityMarker te, boolean isSneaking, EntityPlayer entityPlayer, EnumFacing side) {

        int currRadius = te.getV(side);
        if (isSneaking) {
            currRadius -= 1;
            if (currRadius < 0) currRadius = 0;
        } else {
            currRadius += 1;
        }

        for (EnumFacing f : EnumFacing.VALUES)
            te.setV(f, currRadius);

        entityPlayer.sendMessage(new TextComponentString(
                String.format("%s: radius %d", getMode(te), te.getV(EnumFacing.WEST))));
    }

    @Override
    public void handleConfig(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {

        if (entityPlayer.isSneaking()) {
            super.handleConfig(worldIn, te, entityPlayer, side);
        } else {
            ShapeMode m = ShapeMode.getMode(te.getMode());
            m = m.getNext();
            te.setMode(m.ordinal());
            entityPlayer.sendMessage(new TextComponentString(m.getTranslatedMode()));

            /* clear all the values when you change shape */
            for (EnumFacing f : EnumFacing.VALUES)
                te.setV(f, 0);

            BlockUtils.markBlockForUpdate(worldIn, te.getPos());
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
        } else if (shapeMode == ShapeMode.DOME) {
            calcDome(te, te.getPos().getX(), te.getPos().getY(), te.getPos().getZ(), te.getV(EnumFacing.SOUTH));
        } else if (shapeMode == ShapeMode.SPHERE) {
            calcSphere(te, te.getPos().getX(), te.getPos().getY(), te.getPos().getZ(), te.getV(EnumFacing.SOUTH));
        } else if (shapeMode == ShapeMode.CYLINDER) {
            calcCylinder(te, te.getPos().getX(), te.getPos().getY(), te.getPos().getZ(), te.getV(EnumFacing.SOUTH), te.getV(EnumFacing.UP));
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
        } /* else if (shapeMode == ShapeMode.LINE) {

            int xRadius = te.getV(EnumFacing.EAST);
            int zRadius = te.getV(EnumFacing.NORTH);

            int x0 = te.getPos().getX() - xRadius;
            int z0 = te.getPos().getZ() - zRadius;
            int x1 = te.getPos().getX() + xRadius;
            int z1 = te.getPos().getZ() + zRadius;

            calcLine(te, x0, z0, x1, z1, te.getPos().getY());
        } */
    }



    public enum ShapeMode {
        CIRCLE,
        ELLIPSE,
        SPHERE,
        DOME,
        CYLINDER;

        public static ShapeMode getMode(int id) {
            return values()[MathHelper.clamp(id, 0, values().length - 1)];
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
