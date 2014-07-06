package com.ipsis.buildersguides.render;

import com.ipsis.buildersguides.tileentity.TileAxisMarker;
import com.ipsis.buildersguides.tileentity.TileBGMarker;
import com.ipsis.buildersguides.tileentity.TileCoordMarker;
import com.ipsis.buildersguides.util.AxisHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class AxisMarkerRenderer extends TileEntitySpecialRenderer {

    private final RenderItem customRenderItem;

    public AxisMarkerRenderer() {

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

    private void render(TileAxisMarker te, double x, double y, double z) {
        /* Start in the center */
        double x1 = x + 0.5;
        double y1 = y + 0.5;
        double z1 = z + 0.5;

        GL11.glPushMatrix();

        GL11.glDisable(GL11.GL_TEXTURE_2D); // disable 2D texturing
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glLineWidth(1.5F);
        GL11.glBegin(GL11.GL_LINES);

        /* Depends on the color */
        GL11.glColor4f(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), 1.0f);

        if (AxisHelper.Axis.hasX(te.getAxis())) {

            GL11.glVertex3d(x1 - TileBGMarker.MAX_DISTANCE, y1, z1);
            GL11.glVertex3d(x1 + TileBGMarker.MAX_DISTANCE, y1, z1);
        }

        if (AxisHelper.Axis.hasY(te.getAxis())) {

            GL11.glVertex3d(x1, y1 - TileBGMarker.MAX_DISTANCE, z1);
            GL11.glVertex3d(x1, y1 + TileBGMarker.MAX_DISTANCE, z1);
        }

        if (AxisHelper.Axis.hasZ(te.getAxis())) {

            GL11.glVertex3d(x1, y1, z1 - TileBGMarker.MAX_DISTANCE);
            GL11.glVertex3d(x1, y1, z1 + TileBGMarker.MAX_DISTANCE);
        }

        GL11.glEnd();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float tick) {

        if (!(te instanceof TileAxisMarker))
            return;

        render((TileAxisMarker)te, x, y, z);
    }
}
