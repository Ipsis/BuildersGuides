package ipsis.buildersguides.manager.markers;

import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.oss.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GhostMarker extends Marker {

    @Override
    public boolean isMatch(MarkerType t) {
        return t == MarkerType.GHOST;
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
            // TODO fix the ghost mode display names
            entityPlayer.addChatComponentMessage(new ChatComponentText(m.name()));
        }
    }

    @Override
    public void handleServerUpdate(TileEntityMarker te) {

        te.clearBlocklist();

        GhostMode ghostMode = GhostMode.getMode(te.getMode());
        if (ghostMode == GhostMode.SINGLE) {

            for (EnumFacing f : EnumFacing.VALUES) {
                if (te.getV(f) != 0) {
                    for (int i = 1; i <= te.getV(f); i++)
                        te.addToBlockList(te.getPos().offset(f, i));
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

                    for (int i = 0; i <= te.getV(a); i++) {
                        for (int j = 0; j <= te.getV(b); j++) {
                            BlockPos p = te.getPos().offset(a, i).offset(b, j);
                            te.addToBlockList(p);
                        }
                    }
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

                    for (int i = 0; i <= te.getV(a); i++) {
                        for (int j = 0; j <= te.getV(b); j++) {
                            BlockPos p = te.getPos().offset(a, i).offset(b, j);
                            te.addToBlockList(p);
                        }
                    }
                }
            }
        } else if (ghostMode == GhostMode.CUBE) {

        }
    }

    public enum GhostMode {
        SINGLE,
        FLOOR,
        WALL,
        CUBE;

        public static GhostMode getMode(int id) {
            return values()[MathHelper.clamp_int(id, 0, values().length - 1)];
        }

        public GhostMode getNext() {
            int next = (this.ordinal() + 1) % values().length;
            return values()[next];
        }
    }
}
