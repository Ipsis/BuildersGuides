package ipsis.buildersguides.render;

import ipsis.buildersguides.handler.ConfigurationHandler;
import ipsis.buildersguides.tileentity.TileAdvancedMarker;
import ipsis.buildersguides.util.BlockPosition;
import ipsis.buildersguides.util.RenderUtils;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import java.util.List;


public class AdvancedMarkerRenderer extends TileEntitySpecialRenderer {

    private final RenderItem customRenderItem;

    public AdvancedMarkerRenderer() {

        customRenderItem = new RenderItem()
        {
            @Override
            public boolean shouldBob()
            {
                return false;
            }
        };

        customRenderItem.setRenderManager(RenderManager.instance);
    }

    private void render(TileAdvancedMarker te, double x, double y, double z) {

        if (te.getStructureMode() == TileAdvancedMarker.StructureMode.NONE)
            return;

        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5F, y + 0.5F, z + 0.5F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), 1.0F);
        GL11.glLineWidth(1.5F);

        if (te.getStructureMode() == TileAdvancedMarker.StructureMode.HORIZ_X_Z) {

            GL11.glBegin(GL11.GL_LINE_LOOP);
            GL11.glVertex3f(0.0F, 0.0F, 0.0F);
            GL11.glVertex3f(te.getDx(), 0.0F, 0.0F);
            GL11.glVertex3f(te.getDx(), 0.0F, te.getDz());
            GL11.glVertex3f(0.0F, 0.0F, te.getDz());
            GL11.glEnd();

        } else if (te.getStructureMode() == TileAdvancedMarker.StructureMode.VERT_X_Y) {

            GL11.glBegin(GL11.GL_LINE_LOOP);
            GL11.glVertex3f(0.0F, 0.0F, 0.0F);
            GL11.glVertex3f(te.getDx(), 0.0F, 0.0F);
            GL11.glVertex3f(te.getDx(), te.getDy(), 0.0F);
            GL11.glVertex3f(0.0F, te.getDy(), 0.0F);
            GL11.glEnd();

        } else if (te.getStructureMode() == TileAdvancedMarker.StructureMode.VERT_Y_Z) {

            GL11.glBegin(GL11.GL_LINE_LOOP);
            GL11.glVertex3f(0.0F, 0.0F, 0.0F);
            GL11.glVertex3f(0.0F, te.getDy(), 0.0F);
            GL11.glVertex3f(0.0F, te.getDy(), te.getDz());
            GL11.glVertex3f(0.0F, 0.0F, te.getDz());
            GL11.glEnd();

        } else if (te.getStructureMode() == TileAdvancedMarker.StructureMode.CUBOID) {

            GL11.glBegin(GL11.GL_LINE_LOOP);
            GL11.glVertex3f(0.0F, 0.0F, 0.0F);
            GL11.glVertex3f(te.getDx(), 0.0F, 0.0F);
            GL11.glVertex3f(te.getDx(), 0.0F, te.getDz());
            GL11.glVertex3f(0.0F, 0.0F, te.getDz());
            GL11.glEnd();

            GL11.glBegin(GL11.GL_LINE_LOOP);
            GL11.glVertex3f(0.0F, te.getDy(), 0.0F);
            GL11.glVertex3f(te.getDx(), te.getDy(), 0.0F);
            GL11.glVertex3f(te.getDx(), te.getDy(), te.getDz());
            GL11.glVertex3f(0.0F, te.getDy(), te.getDz());
            GL11.glEnd();

            GL11.glBegin(GL11.GL_LINES);
            GL11.glVertex3f(0.0F, 0.0F, 0.0F);
            GL11.glVertex3f(0.0F, te.getDy(), 0.0F);

            GL11.glVertex3f(te.getDx(), 0.0F, 0.0F);
            GL11.glVertex3f(te.getDx(), te.getDy(), 0.0F);

            GL11.glVertex3f(te.getDx(), 0.0F, te.getDz());
            GL11.glVertex3f(te.getDx(), te.getDy(), te.getDz());

            GL11.glVertex3f(0.0F, 0.0F, te.getDz());
            GL11.glVertex3f(0.0F, te.getDy(), te.getDz());
            GL11.glEnd();
        }

        List<BlockPosition> centers = te.getCenters();
        if (centers != null) {
            for (BlockPosition c : centers) {

                double dx = c.x - te.xCoord;
                double dy = c.y - te.yCoord;
                double dz = c.z - te.zCoord;

                RenderUtils.drawBlockOutline((float) dx, (float) dy, (float) dz);
            }
        }

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float tick) {

        if (!ConfigurationHandler.enableClientRenderer)
            return;

        if (!(te instanceof TileAdvancedMarker))
            return;

        if (((TileAdvancedMarker) te).getIsRenderDataValid())
            render((TileAdvancedMarker)te, x, y, z);
    }
}
