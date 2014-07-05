package com.ipsis.buildersguides.render;

import com.ipsis.buildersguides.helper.ColorHelper;
import com.ipsis.buildersguides.util.LogHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.ipsis.buildersguides.tileentity.TileBGMarker;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class BGMarkerRenderer extends TileEntitySpecialRenderer {

    private final RenderItem customRenderItem;

    public BGMarkerRenderer() {

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

    private void renderAxesMarker(TileBGMarker te, double x, double y, double z) {

        /* Start in the center */
        double x1 = x + 0.5;
        double y1 = y + 0.5;
        double z1 = z + 0.5;

        GL11.glPushMatrix();

        GL11.glDisable(GL11.GL_TEXTURE_2D); // disable 2D texturing
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glLineWidth(10.5F);
        GL11.glBegin(GL11.GL_LINES);

        /* Depends on the color */
        GL11.glColor4f(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), 1.0f);

        if (te.showX()) {

            GL11.glVertex3d(x1 - TileBGMarker.MAX_DISTANCE, y1, z1);
            GL11.glVertex3d(x1 + TileBGMarker.MAX_DISTANCE, y1, z1);
        }

        if (te.showY()) {

            GL11.glVertex3d(x1, y1 - TileBGMarker.MAX_DISTANCE, z1);
            GL11.glVertex3d(x1, y1 + TileBGMarker.MAX_DISTANCE, z1);
        }

        if (te.showZ()) {

            GL11.glVertex3d(x1, y1, z1 - TileBGMarker.MAX_DISTANCE);
            GL11.glVertex3d(x1, y1, z1 + TileBGMarker.MAX_DISTANCE);
        }

        GL11.glEnd();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glPopMatrix();
    }

    private void renderPairMarker(double x, double y, double z)
    {

    }

    private void renderCoordMarker(TileBGMarker te, double x, double y, double z)
    {
        FontRenderer fontRenderer = this.func_147498_b();
        RenderManager renderManager = RenderManager.instance;

        String s = "(" + te.xCoord + ", " + te.yCoord + ", " + te.zCoord + ")";

        float f = 1.6F;
        float f1 = 0.016666668F * f;

        GL11.glPushMatrix();
        {
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);

            /* Move to the center of the tile */
            GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

            GL11.glNormal3f(0.0F, 1.0F, 0.0F);

            /* Rotate to face the player  */
            GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

            /* Make the text smaller */
            GL11.glScalef(-f1, -f1, f1);

            fontRenderer.drawString(s, -fontRenderer.getStringWidth(s) / 2, 0, 0x20FFFFFF);
            GL11.glEnable(GL11.GL_LIGHTING);
        }
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {

        if (!(tileEntity instanceof TileBGMarker))
            return;

        TileBGMarker te = (TileBGMarker)tileEntity;

        if (te.getType() == TileBGMarker.Type.AXES)
            renderAxesMarker(te, x, y, z);
        else if (te.getType() == TileBGMarker.Type.PAIR)
            renderPairMarker(x, y, z);
        else if (te.getType() == TileBGMarker.Type.COORD)
            renderCoordMarker(te, x, y, z);
    }
}
