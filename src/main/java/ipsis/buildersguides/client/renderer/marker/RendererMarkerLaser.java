package ipsis.buildersguides.client.renderer.marker;

import ipsis.buildersguides.tileentity.TileEntityMarker;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import org.lwjgl.opengl.GL11;

public class RendererMarkerLaser extends RendererMarker {

    @Override
    public void doRenderMarkerType(TileEntityMarker te, double relX, double relY, double relZ, float partialTicks) {

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
            for (EnumFacing f : EnumFacing.values()) {
                if (te.isFaceEnabled(f)) {
                    worldRenderer.pos(0.0F, 0.0F, 0.0F).endVertex();
                    worldRenderer.pos(
                            0.0F + (f.getFrontOffsetX() * 64.0F),
                            0.0F + (f.getFrontOffsetY() * 64.0F),
                            0.0F + (f.getFrontOffsetZ() * 64.0F)).endVertex();
                }
            }
            tessellator.draw();
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
}
