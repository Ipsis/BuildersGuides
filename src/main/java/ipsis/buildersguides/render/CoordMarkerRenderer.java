package ipsis.buildersguides.render;

import ipsis.buildersguides.handler.ConfigurationHandler;
import ipsis.buildersguides.tileentity.TileCoordMarker;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class CoordMarkerRenderer extends TileEntitySpecialRenderer {

    private final RenderItem customRenderItem;

    public CoordMarkerRenderer() {

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

    private void render(TileCoordMarker te, double x, double y, double z) {

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
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float tick) {

        if (!ConfigurationHandler.enableClientRenderer)
            return;

        if (!(te instanceof TileCoordMarker))
            return;

        render((TileCoordMarker)te, x, y, z);
    }
}
