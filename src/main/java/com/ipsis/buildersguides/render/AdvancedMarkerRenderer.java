package com.ipsis.buildersguides.render;

import com.ipsis.buildersguides.tileentity.TileAdvancedMarker;
import com.ipsis.buildersguides.tileentity.TileCoordMarker;
import com.ipsis.buildersguides.util.BlockPosition;
import com.ipsis.buildersguides.util.LogHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;


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

        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5F, y + 0.5F, z + 0.5F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
        GL11.glLineWidth(2.5F);

        if (te.getMode() == TileAdvancedMarker.Mode.HORIZ_PLANE) {

            GL11.glBegin(GL11.GL_LINE_LOOP);
            GL11.glVertex3f(0.0F, 0.0F, 0.0F);
            GL11.glVertex3f(te.dx, 0.0F, 0.0F);
            GL11.glVertex3f(te.dx, 0.0F, te.dz);
            GL11.glVertex3f(0.0F, 0.0F, te.dz);
            GL11.glEnd();

        } else if (te.getMode() == TileAdvancedMarker.Mode.VERT_PLANE) {

            if (te.dx != 0 && te.dy != 0) {

                GL11.glBegin(GL11.GL_LINE_LOOP);
                GL11.glVertex3f(0.0F, 0.0F, 0.0F);
                GL11.glVertex3f(te.dx, 0.0F, 0.0F);
                GL11.glVertex3f(te.dx, te.dy, 0.0F);
                GL11.glVertex3f(0.0F, te.dy, 0.0F);
                GL11.glEnd();

            } else if (te.dy != 0 && te.dz != 0) {

                GL11.glBegin(GL11.GL_LINE_LOOP);
                GL11.glVertex3f(0.0F, 0.0F, 0.0F);
                GL11.glVertex3f(0.0F, te.dy, 0.0F);
                GL11.glVertex3f(0.0F, te.dy, te.dz);
                GL11.glVertex3f(0.0F, 0.0F, te.dz);
                GL11.glEnd();
            }
        } else if (te.getMode() == TileAdvancedMarker.Mode.CUBOID) {

            GL11.glBegin(GL11.GL_LINE_LOOP);
            GL11.glVertex3f(0.0F, 0.0F, 0.0F);
            GL11.glVertex3f(te.dx, 0.0F, 0.0F);
            GL11.glVertex3f(te.dx, 0.0F, te.dz);
            GL11.glVertex3f(0.0F, 0.0F, te.dz);
            GL11.glEnd();

            GL11.glBegin(GL11.GL_LINE_LOOP);
            GL11.glVertex3f(0.0F, te.dy, 0.0F);
            GL11.glVertex3f(te.dx, te.dy, 0.0F);
            GL11.glVertex3f(te.dx, te.dy, te.dz);
            GL11.glVertex3f(0.0F, te.dy, te.dz);
            GL11.glEnd();

            GL11.glBegin(GL11.GL_LINE);

            GL11.glVertex3f(0.0F, 0.0F, 0.0F);
            GL11.glVertex3f(0.0F, te.dy, 0.0F);

            GL11.glVertex3f(te.dx, 0.0F, 0.0F);
            GL11.glVertex3f(te.dx, te.dy, 0.0F);

            GL11.glVertex3f(te.dx, 0.0F, te.dz);
            GL11.glVertex3f(te.dx, te.dy, te.dz);

            GL11.glVertex3f(0.0F, 0.0F, te.dz);
            GL11.glVertex3f(0.0F, te.dy, te.dz);

            GL11.glEnd();
        }

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float tick) {

        if (!(te instanceof TileAdvancedMarker))
            return;

        if (((TileAdvancedMarker) te).canRender)
            render((TileAdvancedMarker)te, x, y, z);
    }
}
