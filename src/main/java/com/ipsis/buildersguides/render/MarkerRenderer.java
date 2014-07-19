package com.ipsis.buildersguides.render;

import com.ipsis.buildersguides.tileentity.TileBaseMarker;
import com.ipsis.buildersguides.tileentity.TileLaserMarker2;
import com.ipsis.buildersguides.tileentity.TileRangeMarker2;
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

public class MarkerRenderer extends TileEntitySpecialRenderer {

    private final RenderItem customRenderItem;

    public MarkerRenderer() {

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

        if (te instanceof TileBaseMarker)
            doRender((TileBaseMarker)te, x, y, z);
    }

    private void doRender(TileBaseMarker te, double x, double y, double z) {

        FontRenderer fontRenderer = this.func_147498_b();
        RenderManager renderManager = RenderManager.instance;

        /* Text */
        float f = 1.6F;
        float f1 = 0.016666668F * f;

        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5F, y + 0.5F, z + 0.5F);
        GL11.glDisable(GL11.GL_LIGHTING);

        for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS)  {

            if (te.getTarget(d) == null)
                continue;

            /* Draw the connecting lines */
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glColor4f(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), 1.0F);
            GL11.glLineWidth(2.5F);
            GL11.glBegin(GL11.GL_LINES);

            double dx = te.xCoord - te.getTarget(d).x;
            double dy = te.yCoord - te.getTarget(d).y;
            double dz = te.zCoord - te.getTarget(d).z;

            GL11.glVertex3d(0.0F, 0.0F, 0.0F);
            GL11.glVertex3d(dx * -1.0F, dy * -1.0F, dz * -1.0F);

            GL11.glEnd();

            BlockPosition c = te.getCenter(d);
            if (c != null) {

                dx = (te.xCoord - c.x) * -1.0F;
                dy = (te.yCoord - c.y) * -1.0F;
                dz = (te.zCoord - c.z) * -1.0F;

                RenderUtils.drawBlockOutline((float) dx, (float) dy, (float) dz);
            }

            /* Render the text above the line */
            GL11.glPushMatrix();
            {
                /* Rotate to face the player  */
                GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

                GL11.glScalef(-f1, -f1, f1);
                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                int num = BlockUtils.numBlocksBetween(te.xCoord, te.yCoord, te.zCoord,
                        te.getTarget(d).x, te.getTarget(d).y, te.getTarget(d).z);

                if (num > 0) {
                    GL11.glTranslatef(3 * d.offsetX, 3 * d.offsetY, 3 * d.offsetZ);
                    String s = d.toString();
                    fontRenderer.drawString(s, -fontRenderer.getStringWidth(s) / 2, 0, 0x20FFFFFF);
                    GL11.glTranslatef(-(3 * d.offsetX), -(3 * d.offsetY), -(3 * d.offsetZ));
                }
            }
            GL11.glPopMatrix();

            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

}
