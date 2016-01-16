package ipsis.buildersguides.client.renderer.marker;

import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;
import org.lwjgl.opengl.GL11;

public class RendererMarkerChunk extends RendererMarker {

    @Override
    public void doRenderMarkerType(TileEntityMarker te, double relX, double relY, double relZ, float partialTicks) {

        Chunk chunk = te.getWorld().getChunkFromBlockCoords(te.getPos());
        if (chunk == null)
            return;

        BlockPos pos = new BlockPos(chunk.xPosition << 4, te.getPos().getY(), chunk.zPosition << 4);

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldRenderer = tessellator.getWorldRenderer();

            double dx = pos.getX() - te.getPos().getX();
            double dz = pos.getZ() - te.getPos().getZ();

            double pdy = 16.0F;
            double ndy = 16.0F;

            // Can we offset +/- 16 from the y
            if (te.getPos().getY() + 16 > te.getWorld().getHeight())
                pdy = te.getWorld().getHeight() - te.getPos().getY();
            if (te.getPos().getY() - 16 < 0)
                ndy = te.getPos().getY();

            GlStateManager.translate(relX + dx, relY, relZ + dz);

            GlStateManager.disableLighting();
            GlStateManager.disableTexture2D();
            GlStateManager.color(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), RendererMarker.RENDER_ALPHA);

            for (double yc = -1.0 * ndy; yc <= pdy; yc++) {
                worldRenderer.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
                worldRenderer.pos(0.0F, yc, 0.0F).endVertex();
                worldRenderer.pos(16.0F, yc, 0.0F).endVertex();
                worldRenderer.pos(16.0F, yc, 16.0F).endVertex();
                worldRenderer.pos(0.0F, yc, 16.0F).endVertex();
                tessellator.draw();
            }

            for (int xc  = 0; xc <= 16; xc ++) {
                worldRenderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
                worldRenderer.pos(xc, 0.0F - ndy, 0.0F).endVertex();
                worldRenderer.pos(xc, 0.0F + pdy, 0.0F).endVertex();
                worldRenderer.pos(xc, 0.0F - ndy, 16.0F).endVertex();
                worldRenderer.pos(xc, 0.0F + pdy, 16.0F).endVertex();
                tessellator.draw();
            }

            for (int zc  = 0; zc <= 16; zc ++) {
                worldRenderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
                worldRenderer.pos(0.0F, 0.0F - ndy, zc).endVertex();
                worldRenderer.pos(0.0F, 0.0F + pdy, zc).endVertex();
                worldRenderer.pos(16.0F, 0.0F - ndy, zc).endVertex();
                worldRenderer.pos(16.0F, 0.0F + pdy, zc).endVertex();
                tessellator.draw();
            }

            float[][] center = new float[][]{
                    { 7.5F, 0.5F, 7.5F },
                    { 7.5F, 0.5F, 8.5F },
                    { 8.5F, 0.5F, 7.5F },
                    { 8.5F, 0.5F, 8.5F }
            };

            for (int i = 0; i < center.length; i++) {
                GlStateManager.pushMatrix();
                {
                    GlStateManager.translate(center[i][0], center[i][1], center[i][2]);
                    RenderUtils.drawBlockOutline();
                }
                GlStateManager.popMatrix();
            }

        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
}
