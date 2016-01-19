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
            // translate to center of te
            GlStateManager.translate(relX + 0.5F, relY + 0.5F, relZ + 0.5F);
            GlStateManager.disableLighting();
            GlStateManager.disableTexture2D();
            GlStateManager.color(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue());

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

            GlStateManager.enableTexture2D();
            GlStateManager.enableLighting();
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();

        ColorBG color = te.getColor().getNext();
        renderBlockList(te.getCenterList(), te, relX, relY, relZ, color.getRed(), color.getGreen(), color.getBlue());
    }
}
