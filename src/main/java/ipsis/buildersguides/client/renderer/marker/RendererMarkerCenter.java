package ipsis.buildersguides.client.renderer.marker;

import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.ColorBG;
import ipsis.buildersguides.util.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class RendererMarkerCenter extends RendererMarker {

    @Override
    public void doRenderMarkerType(TESRMarker tesrMarker, TileEntityMarker te, double relX, double relY, double relZ, float partialTicks) {

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {
            // translate to center or te
            GlStateManager.translate(relX + 0.5F , relY + 0.5F, relZ + 0.5F);
            GlStateManager.disableLighting();
            GlStateManager.disableTexture2D();
            GlStateManager.color(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), RendererMarker.RENDER_ALPHA);

            // render target points
            for (EnumFacing f : EnumFacing.VALUES) {
                if (te.hasTarget(f)) {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(
                            (te.getPos().getX() - te.getTarget(f).getX()) * -1.0F,
                            (te.getPos().getY() - te.getTarget(f).getY()) * -1.0F,
                            (te.getPos().getZ() - te.getTarget(f).getZ()) * -1.0F);
                    RenderUtils.drawBlockOutline(0.5F);
                    GlStateManager.popMatrix();
                }
            }

            // render the center points
            ColorBG nextColor = te.getColor().getNext();
            GlStateManager.color(nextColor.getRed(), nextColor.getGreen(), nextColor.getBlue(), RendererMarker.RENDER_ALPHA);

            for (BlockPos p : te.getCenterList()) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(
                        (te.getPos().getX() - p.getX()) * -1.0F,
                        (te.getPos().getY() - p.getY()) * -1.0F,
                        (te.getPos().getZ() - p.getZ()) * -1.0F);
                renderBlock(0.4F);
                GlStateManager.popMatrix();
            }
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
}
