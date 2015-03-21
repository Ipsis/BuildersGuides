package ipsis.buildersguides.render;

import ipsis.buildersguides.handler.ConfigurationHandler;
import ipsis.buildersguides.tileentity.TileDireMarker;
import ipsis.buildersguides.tileentity.TileGhostMarker;
import ipsis.buildersguides.util.BlockPosition;
import ipsis.buildersguides.util.RenderUtils;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Set;


public class GhostMarkerRenderer extends TileEntitySpecialRenderer {

    private final RenderItem customRenderItem;

    public GhostMarkerRenderer() {

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

        if (!ConfigurationHandler.enableClientRenderer)
            return;

        if (!(te instanceof TileGhostMarker))
            return;

        render((TileGhostMarker)te, x, y, z);
    }

    private void render(TileGhostMarker te, double x, double y, double z) {

        if (te.getBlockList() == null)
            return;

        GL11.glPushMatrix();
        {
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_TEXTURE_2D);

            GL11.glTranslated(x + 0.5F, y + 0.5F, z + 0.5F);


            GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
            //GL11.glColor4f(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), 1.0F);
            GL11.glLineWidth(1.5F);

            List<BlockPosition> blocks = te.getBlockList();
            for (BlockPosition p : blocks) {
                float dx = p.x - te.xCoord;
                float dy = p.y - te.yCoord;
                float dz = p.z - te.zCoord;
                RenderUtils.drawBlockOutline(dx, dy, dz);
            }

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_LIGHTING);
        }
        GL11.glPopMatrix();
    }
}
