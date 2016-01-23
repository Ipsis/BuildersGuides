package ipsis.buildersguides.manager.markers;

import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.reference.Names;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.BlockUtils;
import ipsis.buildersguides.util.StringHelper;
import ipsis.oss.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

/**
 * Ghost Marker
 *
 * Displays a single, plane or box and the center blocks depending on mode
 */
public class MarkerGhost extends Marker {

    @Override
    public boolean isMatch(MarkerType t) {
        return t == MarkerType.GHOST;
    }

    @Override
    public boolean isFaceEnabled(TileEntityMarker te, EnumFacing f) {
        return te.hasValidV(f);
    }

    @Override
    public void handleHammer(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {

        LogHelper.info("handleHammer: GHOST");

        if (entityPlayer.isSneaking()) {
            te.setV(side, te.getV(side) - 1);
            if (te.getV(side) < 0)
                te.setV(side, 0);
        } else {
            te.setV(side, te.getV(side) + 1);
        }
        worldIn.markBlockForUpdate(te.getPos());
    }

    @Override
    public void handleConfig(World worldIn, TileEntityMarker te, EntityPlayer entityPlayer, EnumFacing side) {

        LogHelper.info("handleConfig: GHOST");

        if (entityPlayer.isSneaking()) {

        } else {

            GhostMode m = GhostMode.getMode(te.getMode());
            m = m.getNext();
            te.setMode(m.ordinal());
            entityPlayer.addChatComponentMessage(new ChatComponentText(m.getTranslatedMode()));
            worldIn.markBlockForUpdate(te.getPos());
        }
    }

    @Override
    public String getMode(TileEntityMarker te) {

        return GhostMode.getMode(te.getMode()).getTranslatedMode();
    }

    @Override
    public void handleServerUpdate(TileEntityMarker te) {

        te.clearClientData();

        GhostMode ghostMode = GhostMode.getMode(te.getMode());
        if (ghostMode == GhostMode.SINGLE) {

            for (EnumFacing f : EnumFacing.VALUES) {
                if (te.getV(f) != 0) {
                    for (int i = 1; i <= te.getV(f); i++)
                        te.addToBlockList(te.getPos().offset(f, i));

                    /* centers */
                    List<BlockPos> centerList = BlockUtils.getCenterBlockList(te.getPos(), te.getPos().offset(f, te.getV(f)), f);
                    for (BlockPos p : centerList)
                        te.addToCenterList(p);
                }
            }

        } else if (ghostMode == GhostMode.FLOOR) {

            EnumFacing[] dirA = new EnumFacing[]{ EnumFacing.NORTH, EnumFacing.SOUTH };
            EnumFacing[] dirB = new EnumFacing[]{ EnumFacing.EAST, EnumFacing.WEST };

            for (EnumFacing a : dirA) {
                if (!te.hasValidV(a))
                    continue;

                for (EnumFacing b : dirB) {
                    if (!te.hasValidV(b))
                        continue;

                    addPlane(te, te.getPos(), a, b);
                }
            }
        } else if (ghostMode == GhostMode.WALL) {

            EnumFacing[] dirA = new EnumFacing[]{ EnumFacing.UP, EnumFacing.DOWN };
            EnumFacing[] dirB = new EnumFacing[]{ EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST };

            for (EnumFacing a : dirA) {
                if (!te.hasValidV(a))
                    continue;

                for (EnumFacing b : dirB) {
                    if (!te.hasValidV(b))
                        continue;

                    addPlane(te, te.getPos(), a, b);
                }
            }
        } else if (ghostMode == GhostMode.CUBOID) {

            if (te.hasValidV(EnumFacing.UP)) {

                EnumFacing[] dirY = new EnumFacing[]{ EnumFacing.UP, EnumFacing.DOWN };
                EnumFacing[] dirZ = new EnumFacing[]{ EnumFacing.NORTH, EnumFacing.SOUTH };
                EnumFacing[] dirX = new EnumFacing[]{ EnumFacing.EAST, EnumFacing.WEST };

                for (EnumFacing y : dirY ) {

                    if (!te.hasValidV(y))
                        continue;

                    for (EnumFacing z : dirZ) {

                        if (!te.hasValidV(z))
                            continue;

                        for (EnumFacing x : dirX) {

                            if (!te.hasValidV(x))
                                continue;

                            // Front
                            addPlane(te, te.getPos(), y, x);
                            // Bottom
                            addPlane(te, te.getPos(), z, x);
                            // Left
                            addPlane(te, te.getPos(), y, z);
                            // Back
                            addPlane(te, te.getPos().offset(z, te.getV(z)), y, x);
                            // TOP
                            addPlane(te, te.getPos().offset(y, te.getV(y)), z, x);
                            // Right
                            addPlane(te, te.getPos().offset(x, te.getV(x)), y, z);
                        }
                    }
                }
            }
        }

        te.cleanBlockList();
    }

    private void addPlane(TileEntityMarker te, BlockPos origin, EnumFacing axisA, EnumFacing axisB) {

        BlockUtils.PlaneInfo planeInfo = BlockUtils.getPlaneBlockList(origin, axisA, axisB, te.getV(axisA), te.getV(axisB));
        te.addToBlockList(planeInfo.blockList);
        te.addToCenterList(planeInfo.centerList);
    }

    public enum GhostMode {
        SINGLE,
        FLOOR,
        WALL,
        CUBOID;

        public static GhostMode getMode(int id) {
            return values()[MathHelper.clamp_int(id, 0, values().length - 1)];
        }

        public GhostMode getNext() {
            int next = (this.ordinal() + 1) % values().length;
            return values()[next];
        }

        public String getTranslatedMode() {
            return StringHelper.localize(Names.NAME, "ghostmode." + this.toString());
        }
    }
}
