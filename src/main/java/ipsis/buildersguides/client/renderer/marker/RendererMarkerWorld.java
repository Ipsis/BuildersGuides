package ipsis.buildersguides.client.renderer.marker;

import ipsis.buildersguides.tileentity.TileEntityMarker;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

public class RendererMarkerWorld extends RendererMarker {
    @Override
    public void doRenderMarkerType(TileEntityMarker te, double relX, double relY, double relZ, float partialTicks) {

        if (te.getBlockList() == null || te.getBlockList().isEmpty())
            return;

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldRenderer = tessellator.getWorldRenderer();

            // translate to center or te
            GlStateManager.translate(relX + 0.5F , relY + 0.5F, relZ + 0.5F);
            GlStateManager.disableLighting();
            GlStateManager.disableTexture2D();
            GlStateManager.color(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), RendererMarker.RENDER_ALPHA);

            worldRenderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
            for (BlockPos p : te.getBlockList()) {
                worldRenderer.pos(0.0F, 0.0F, 0.0F).endVertex();
                worldRenderer.pos(
                        (te.getPos().getX() - p.getX()) * -1.0F,
                        (te.getPos().getY() - p.getY()) * -1.0F,
                        (te.getPos().getZ() - p.getZ()) * -1.0F).endVertex();
            }
            tessellator.draw();
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
}
