package com.ipsis.buildersguides.render;

import com.ipsis.buildersguides.tileentity.TileLaserMarker;
import com.ipsis.buildersguides.util.BlockPosition;
import com.ipsis.buildersguides.util.LogHelper;
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

    private void render(TileLaserMarker te, double x, double y, double z) {

        if (te.getTarget() == null)
            return;

        FontRenderer fontRenderer = this.func_147498_b();
        RenderManager renderManager = RenderManager.instance;

        GL11.glPushMatrix();
        {
            GL11.glTranslated(x + 0.5F, y + 0.5F, z + 0.5F);

            GL11.glDisable(GL11.GL_TEXTURE_2D);
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

                String s = "12 blocks or more text";
                fontRenderer.drawString(s, -fontRenderer.getStringWidth(s) / 2, 0, 0x20FFFFFF);

            }
            GL11.glPopMatrix();

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

                GL11.glTranslated(dx, dy, dz);
                GL11.glBegin(GL11.GL_LINES);

                /* I feel ashamed of this! */

                for (float v = -0.5F; v <= 0.5F; v += 1.0F) {
                    GL11.glVertex3d(-0.5F, 0.5F, v);
                    GL11.glVertex3d(0.5F, 0.5F, v);
                    GL11.glVertex3d(0.5F, 0.5F, v);
                    GL11.glVertex3d(0.5F, -0.5F, v);
                    GL11.glVertex3d(0.5F, -0.5F, v);
                    GL11.glVertex3d(-0.5F, -0.5F, v);
                    GL11.glVertex3d(-0.5F, -0.5F, v);
                    GL11.glVertex3d(-0.5F, 0.5F, v);
                }

                for (float v = -0.5F; v <= 0.5F; v += 1.0F) {
                    GL11.glVertex3d(-0.5F, v, 0.5F);
                    GL11.glVertex3d(0.5F, v, 0.5F);
                    GL11.glVertex3d(0.5F, v, 0.5F);
                    GL11.glVertex3d(0.5F, v, -0.5F);
                    GL11.glVertex3d(0.5F, v, -0.5F);
                    GL11.glVertex3d(-0.5F, v, -0.5F);
                    GL11.glVertex3d(-0.5F, v, -0.5F);
                    GL11.glVertex3d(-0.5F, v, 0.5F);
                }

                GL11.glEnd();
            }

            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);

        }
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float tick) {

        if (!(te instanceof TileLaserMarker))
            return;

        render((TileLaserMarker)te, x, y, z);
    }
}
