package ipsis.buildersguides.client.renderer;

import ipsis.buildersguides.tileentity.TileEntityMarker;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class MarkerRenderer extends TileEntitySpecialRenderer {

    @Override
    public void renderTileEntityAt(TileEntity te, double posX, double posY, double posZ, float tick, int p_180535_9_) {

        if (te instanceof TileEntityMarker)
            doRender(te, posX, posY, posZ, tick, p_180535_9_);
    }

    public void doRender(TileEntity te, double posX, double posY, double posZ, float tick, int p_180535_9_) {

        GL11.glPushMatrix();
        {
            GL11.glTranslated(posX + 0.5F, posY + 0.5F, posZ + 0.5F);
            GL11.glDisable(GL11.GL_LIGHTING);

            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glLineWidth(1.5F);
            GL11.glBegin(GL11.GL_LINES);

            GL11.glVertex3d(0.0F, 0.0F, 0.0F);
            GL11.glVertex3d(100.0F, 100.0F, 100.0F);
            GL11.glEnd();


            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_LIGHTING);
        }
        GL11.glPopMatrix();
    }
}
