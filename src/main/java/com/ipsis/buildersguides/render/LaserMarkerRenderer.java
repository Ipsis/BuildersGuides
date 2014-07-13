package com.ipsis.buildersguides.render;

import com.ipsis.buildersguides.tileentity.TileLaserMarker;
import com.ipsis.buildersguides.tileentity.TileRangeMarker;
import com.ipsis.buildersguides.util.BlockPosition;
import com.ipsis.buildersguides.util.BlockUtils;
import com.ipsis.buildersguides.util.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class LaserMarkerRenderer extends TileEntitySpecialRenderer {

    private final RenderItem customRenderItem;

    public LaserMarkerRenderer() {

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

    /**
     * Renders a line, center and text to each available target
     * that the te provides
     */
    private void render(TileRangeMarker te, double x, double y, double z) {

        FontRenderer fontRenderer = this.func_147498_b();
        RenderManager renderManager = RenderManager.instance;

        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5F, y + 0.5F, z + 0.5F);
        GL11.glDisable(GL11.GL_LIGHTING);

        for (int i = 0 ; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {

            if (te.getTarget(i) == null)
                continue;

            /* Draw the connecting lines */
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glColor4f(1.0F, 0.0F, 0.0F, 1.0F);
            GL11.glLineWidth(1.5F);
            GL11.glBegin(GL11.GL_LINES);

            double dx = te.xCoord - te.getTarget(i).x;
            double dy = te.yCoord - te.getTarget(i).y;
            double dz = te.zCoord - te.getTarget(i).z;

            GL11.glVertex3d(0.0F, 0.0F, 0.0F);
            GL11.glVertex3d(dx * -1.0F, dy * -1.0F, dz * -1.0F);

            GL11.glEnd();

            BlockPosition c = te.getCenter(i);
            if (c != null) {

                dx = (te.xCoord - c.x) * -1.0F;
                dy = (te.yCoord - c.y) * -1.0F;
                dz = (te.zCoord - c.z) * -1.0F;

                RenderUtils.drawBlockOutline((float)dx, (float)dy, (float)dz);
            }

            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    private void render(TileLaserMarker te, double x, double y, double z) {

        if (te.getTarget() == null)
            return;

        FontRenderer fontRenderer = this.func_147498_b();
        RenderManager renderManager = RenderManager.instance;

        GL11.glPushMatrix();
        {
            GL11.glTranslated(x + 0.5F, y + 0.5F, z + 0.5F);

            GL11.glDisable(GL11.GL_LIGHTING);

            GL11.glPushMatrix();
            {
                /* Draw the distance above the block */
                GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
                GL11.glTranslatef(0.0F, 1.5F, 0.0F);
                GL11.glNormal3f(0.0F, 1.0F, 0.0F);

                /* Rotate to face the player  */
                GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

                /* Make the text smaller */
                float f = 1.6F;
                float f1 = 0.016666668F * f;
                GL11.glScalef(-f1, -f1, f1);

                int d = BlockUtils.numBlocksBetween(te.xCoord, te.yCoord, te.zCoord,
                        te.getTarget().x, te.getTarget().y, te.getTarget().z);

                if (d != 0) {
                    String s = d + " Blocks";
                    fontRenderer.drawString(s, -fontRenderer.getStringWidth(s) / 2, 0, 0x20FFFFFF);
                }

            }
            GL11.glPopMatrix();

            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glColor4f(1.0F, 0.0F, 0.0F, 1.0F);
            GL11.glLineWidth(1.5F);
            GL11.glBegin(GL11.GL_LINES);

            /* Draw the connecting line */
            double dx = te.xCoord - te.getTarget().x;
            double dy = te.yCoord - te.getTarget().y;
            double dz = te.zCoord - te.getTarget().z;

            GL11.glVertex3d(0.0F, 0.0F, 0.0F);
            GL11.glVertex3d(dx * -1.0F, dy * -1.0F, dz * -1.0F);

            GL11.glEnd();

            BlockPosition c = te.getCenter();
            if (c != null) {

                dx = (te.xCoord - c.x) * -1.0F;
                dy = (te.yCoord - c.y) * -1.0F;
                dz = (te.zCoord - c.z) * -1.0F;

                RenderUtils.drawBlockOutline((float)dx, (float)dy, (float)dz);
            }

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_LIGHTING);
        }
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float tick) {

        if (te instanceof TileLaserMarker)
            render((TileLaserMarker)te, x, y, z);
        else if (te instanceof TileRangeMarker)
            render((TileRangeMarker)te, x, y, z);
    }
}
