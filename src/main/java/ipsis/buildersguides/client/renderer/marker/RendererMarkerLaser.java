package ipsis.buildersguides.client.renderer.marker;

import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.RenderUtils;
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
            // translate to center or te
            GlStateManager.translate(relX + 0.5F , relY + 0.5F, relZ + 0.5F);
            GlStateManager.disableLighting();
            GlStateManager.disableTexture2D();
            GlStateManager.color(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), RendererMarker.RENDER_ALPHA);

            for (EnumFacing f : EnumFacing.values()) {
                if (te.isFaceEnabled(f)) {
                    RenderUtils.drawLine(
                            0.0F, 0.0F, 0.0F,
                            0.0F + (f.getFrontOffsetX() * 64.0F),
                            0.0F + (f.getFrontOffsetY() * 64.0F),
                            0.0F + (f.getFrontOffsetZ() * 64.0F));
                }
            }
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
}
