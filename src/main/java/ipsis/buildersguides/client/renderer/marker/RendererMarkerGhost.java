package ipsis.buildersguides.client.renderer.marker;

import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.ColorBG;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

public class RendererMarkerGhost extends RendererMarker {
    @Override
    public void doRenderMarkerType(TESRMarker tesrMarker, TileEntityMarker te, double relX, double relY, double relZ, float partialTicks) {

        if (te.getBlockList() == null || te.getBlockList().isEmpty())
            return;

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {
            // translate to center or te
            GlStateManager.translate(relX + 0.5F , relY + 0.5F, relZ + 0.5F);
            GlStateManager.disableLighting();
            GlStateManager.disableTexture2D();
            GlStateManager.color(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), RendererMarker.RENDER_ALPHA);

            RenderHelper.disableStandardItemLighting();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.enableBlend();

            if (Minecraft.isAmbientOcclusionEnabled())
                GlStateManager.shadeModel(GL11.GL_SMOOTH);
            else
                GlStateManager.shadeModel(GL11.GL_FLAT);

            for (BlockPos p : te.getBlockList()) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(
                        (te.getPos().getX() - p.getX()) * -1.0F,
                        (te.getPos().getY() - p.getY()) * -1.0F,
                        (te.getPos().getZ() - p.getZ()) * -1.0F);
                renderBlock(0.4F);
                GlStateManager.popMatrix();
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
                renderBlock(0.5F);
                GlStateManager.popMatrix();
            }
            RenderHelper.enableStandardItemLighting();
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
}
