package com.ipsis.buildersguides.render;

import com.ipsis.buildersguides.tileentity.TileChunkMarker;
import com.ipsis.buildersguides.tileentity.TileCoordMarker;
import com.ipsis.buildersguides.util.LogHelper;
import com.ipsis.buildersguides.util.RenderUtils;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class ChunkMarkerRenderer extends TileEntitySpecialRenderer {

    private final RenderItem customRenderItem;

    public ChunkMarkerRenderer() {

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

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float tick) {

        if (!(te instanceof TileChunkMarker))
            return;

        render((TileChunkMarker)te, x, y, z);
    }

    private void render(TileChunkMarker te, double x, double y, double z) {

        if (te.hasChunkInfo() == false || te.hasWorldObj() == false)
            return;

        GL11.glPushMatrix();
        {

            double dx = te.getChunkX() - te.xCoord;
            double dz = te.getChunkZ() - te.zCoord;

            /* Can we offset +/- 16 from the Y */
            double pdy = 16.0F;
            double ndy = 16.0F;

            if (te.yCoord + 16 > te.getWorldObj().getHeight())
                pdy = te.getWorldObj().getHeight() - te.yCoord;
            if (te.yCoord - 16 < 0)
                ndy = te.yCoord;

            /* Move to the chunk corner */
            GL11.glTranslated(x + dx, y, z + dz);

            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_TEXTURE_2D);

            GL11.glColor4f(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), 1.0F);
            GL11.glLineWidth(1.5F);

            for (double yc = -1.0 * ndy; yc <= pdy; yc++) {

                GL11.glBegin(GL11.GL_LINE_LOOP);
                GL11.glVertex3d(0.0F, yc, 0.0F);
                GL11.glVertex3d(16.0F, yc, 0.0F);
                GL11.glVertex3d(16.0F, yc, 16.0F);
                GL11.glVertex3d(0.0F, yc, 16.0F);
                GL11.glEnd();
            }

            for (int xc  = 0; xc <= 16; xc ++) {

                GL11.glBegin(GL11.GL_LINES);
                GL11.glVertex3d(xc, 0.0F - ndy, 0.0F);
                GL11.glVertex3d(xc, 0.0F + pdy, 0.0F);

                GL11.glVertex3d(xc, 0.0F - ndy, 16.0F);
                GL11.glVertex3d(xc, 0.0F + pdy, 16.0F);

                GL11.glEnd();
            }

            for (int zc  = 0; zc <= 16; zc ++) {

                GL11.glBegin(GL11.GL_LINES);
                GL11.glVertex3d(0.0F, 0.0F - ndy, zc);
                GL11.glVertex3d(0.0F, 0.0F + pdy, zc);

                GL11.glVertex3d(16.0F, 0.0F - ndy, zc);
                GL11.glVertex3d(16.0F, 0.0F + pdy, zc);

                GL11.glEnd();
            }

            RenderUtils.drawBlockOutline(8.0F, 0.0F, 8.0F);
            RenderUtils.drawBlockOutline(8.0F, 0.0F, 9.0F);
            RenderUtils.drawBlockOutline(9.0F, 0.0F, 9.0F);
            RenderUtils.drawBlockOutline(9.0F, 0.0F, 8.0F);

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_LIGHTING);
        }
        GL11.glPopMatrix();
    }
}
