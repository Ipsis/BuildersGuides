package ipsis.buildersguides.client.renderer.marker;

import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.BlockUtils;
import ipsis.buildersguides.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumFacing;
import org.lwjgl.opengl.GL11;
import scala.Int;

public class RendererMarkerRange extends RendererMarker {

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

                    GlStateManager.pushAttrib();
                    {
                        GlStateManager.pushMatrix();
                        {
                            GlStateManager.translate(
                                    (te.getPos().getX() - te.getTarget(f).getX()) * -1.0F,
                                    (te.getPos().getY() - te.getTarget(f).getY()) * -1.0F,
                                    (te.getPos().getZ() - te.getTarget(f).getZ()) * -1.0F);
                            RenderUtils.drawBlockOutline(0.5F);
                        }
                        GlStateManager.popMatrix();

                        int diff = BlockUtils.numBlocksBetween(te.getPos(), te.getTarget(f));
                        if (diff > 0) {

                            GlStateManager.disableLighting();
                            GlStateManager.disableDepth();
                            GlStateManager.disableBlend();
                            GlStateManager.enableTexture2D();
                            GlStateManager.pushMatrix();
                            {
                                /* Position of text depends on the line direction */
                                switch (f) {
                                    case UP:
                                        GlStateManager.translate(0.0F, 1.0F, 0.0F);
                                        break;
                                    case DOWN:
                                        GlStateManager.translate(0.0F, -1.0F, 0.0F);
                                        break;
                                    case EAST:
                                    case WEST:
                                        GlStateManager.translate(f.getFrontOffsetX(), 0.5F, 0.0F);
                                        break;
                                    case NORTH:
                                    case SOUTH:
                                        GlStateManager.translate(0.0F, 0.5F, f.getFrontOffsetZ());
                                        break;
                                }


                                GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
                                GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);

                                float f1 = 0.016666668F * 1.6F;
                                FontRenderer fontrenderer = tesrMarker.getFontRenderer();
                                String s = Integer.toString(diff);

                                GlStateManager.scale(-f1, -f1, f1);
                                fontrenderer.drawStringWithShadow(s, -fontrenderer.getStringWidth(s) / 2, 0, 16777215);

                            }
                            GlStateManager.popMatrix();
                            GlStateManager.enableLighting();
                            GlStateManager.enableDepth();
                            GlStateManager.enableBlend();
                            GlStateManager.disableTexture2D();
                        }
                    }
                    GlStateManager.popAttrib();
                }
            }
        }

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
}
