package ipsis.buildersguides.client.renderer.marker;

import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.ColorBG;
import ipsis.buildersguides.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjgl.opengl.GL11;

import java.util.Set;

public abstract class RendererMarker {

    public static final float RENDER_ALPHA = 0.7F;

    public abstract void doRenderMarkerType(TESRMarker tesrMarker, TileEntityMarker te, double relX, double relY, double relZ, float partialTicks);

    protected void renderBlock(float size) {

        RenderUtils.drawBlockShaded(size);
        //RenderUtils.drawBlockOutline(size);
    }

    protected void renderBlock() {

        RenderUtils.drawBlockShaded(0.2F);
        //RenderUtils.drawBlockOutline(0.4F);
    }

    protected void renderRangeToTargets(TESRMarker tesr, TileEntityMarker te, double relX, double relY, double relZ) {

        renderRangeToTargets(tesr, te, relX, relY, relZ, te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue());
    }

    protected void renderRangeToTargets(TESRMarker tesr, TileEntityMarker te, double relX, double relY, double relZ, float red, float green, float blue) {

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {
            // translate to center or te
            GlStateManager.translate(relX + 0.5F , relY + 0.5F, relZ + 0.5F);
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            GlStateManager.color(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue());
            FontRenderer fontrenderer = tesr.getFontRenderer();

            // render target points
            for (EnumFacing f : EnumFacing.VALUES) {
                if (te.hasTarget(f) && te.getFaceData(f) > 0) {

                    int diff = te.getFaceData(f);
                    String s = Integer.toString(diff);

                    GlStateManager.pushMatrix();
                    {
                        GlStateManager.translate(f.getFrontOffsetX() * 0.5F, f.getFrontOffsetY() * 0.5F, f.getFrontOffsetZ() * 0.5F);

                            /* Move the text into the space above the block */
                        GlStateManager.translate(0.0F, 1.0F, 0.0F);

                        GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
                        GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);

                        float f1 = 0.016666668F * 1.6F;
                        GlStateManager.scale(-f1, -f1, f1);
                        fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, ColorBG.BLACK.getV());

                    }
                    GlStateManager.popMatrix();
                }
            }
        }

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    protected void renderLineToTargets(TileEntityMarker te, double relX, double relY, double relZ) {

        renderLineToTargets(te, relX, relY, relZ, te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue());
    }

    protected void renderLineToTargets(TileEntityMarker te, double relX, double relY, double relZ, float red, float green, float blue) {

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(relX + 0.5F, relY + 0.5F, relZ + 0.5F);
            GlStateManager.disableLighting();
            GlStateManager.disableTexture2D();
            GlStateManager.color(red, green, blue);
            for (EnumFacing f : EnumFacing.values()) {
                if (te.hasTarget(f)) {
                    RenderUtils.drawLine(0.0F, 0.0F, 0.0F,
                            (te.getPos().getX() - te.getTarget(f).getX()) * -1.0F,
                            (te.getPos().getY() - te.getTarget(f).getY()) * -1.0F,
                            (te.getPos().getZ() - te.getTarget(f).getZ()) * -1.0F);
                }
            }
            GlStateManager.enableTexture2D();
            GlStateManager.enableLighting();
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    protected void renderTargets(TileEntityMarker te, double relX, double relY, double relZ) {

        renderTargets(te, relX, relY, relZ, te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue());
    }

    protected void renderTargets(TileEntityMarker te, double relX, double relY, double relZ, float red, float green, float blue) {

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {
            // translate to center of te
            GlStateManager.translate(relX + 0.5F, relY + 0.5F, relZ + 0.5F);
            GlStateManager.disableLighting();
            GlStateManager.disableTexture2D();
            GlStateManager.color(red, green, blue);

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
    }

    protected void renderBlockList(Set<BlockPos> blockPosList, TileEntityMarker te, double relX, double relY, double relZ) {

        renderBlockList(blockPosList, te, relX, relY, relZ, te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue());
    }

    protected void renderBlockList(Set<BlockPos> blockPosList, TileEntityMarker te, double relX, double relY, double relZ, float red, float green, float blue) {

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {
            // translate to center or te
            GlStateManager.translate(relX + 0.5F , relY + 0.5F, relZ + 0.5F);
            GlStateManager.disableLighting();   // so colors are correct
            GlStateManager.disableTexture2D();  // no texturing needed
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.enableBlend();

            GlStateManager.color(red, green, blue, RENDER_ALPHA);

            if (Minecraft.isAmbientOcclusionEnabled())
                GlStateManager.shadeModel(GL11.GL_SMOOTH);
            else
                GlStateManager.shadeModel(GL11.GL_FLAT);

            for (BlockPos p : blockPosList) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(
                        (te.getPos().getX() - p.getX()) * -1.0F,
                        (te.getPos().getY() - p.getY()) * -1.0F,
                        (te.getPos().getZ() - p.getZ()) * -1.0F);
//                RenderUtils.drawBlockShaded(0.2F);
                RenderUtils.drawBlockShaded(0.4F);
                GlStateManager.popMatrix();
            }

            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            GlStateManager.enableLighting();
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    protected void renderBlockListAsStairs(Set<BlockPos> blockPosList, TileEntityMarker te, double relX, double relY, double relZ, float angle) {

        renderBlockListAsStairs(blockPosList, te, relX, relY, relZ, angle, te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue());
    }

    protected void renderBlockListAsStairs(Set<BlockPos> blockPosList, TileEntityMarker te, double relX, double relY, double relZ, float angle, float red, float green, float blue) {

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {
            // translate to center or te
            GlStateManager.translate(relX + 0.5F , relY + 0.5F, relZ + 0.5F);
            GlStateManager.disableLighting();   // so colors are correct
            GlStateManager.disableTexture2D();  // no texturing needed
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.enableBlend();

            GlStateManager.color(red, green, blue, RENDER_ALPHA);

            if (Minecraft.isAmbientOcclusionEnabled())
                GlStateManager.shadeModel(GL11.GL_SMOOTH);
            else
                GlStateManager.shadeModel(GL11.GL_FLAT);

            for (BlockPos p : blockPosList) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(
                        (te.getPos().getX() - p.getX()) * -1.0F,
                        (te.getPos().getY() - p.getY()) * -1.0F,
                        (te.getPos().getZ() - p.getZ()) * -1.0F);
                GlStateManager.rotate(angle, 0.0F, 1.0F, 0.0F);
                RenderUtils.drawStairsShaded();
                GlStateManager.popMatrix();
            }

            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            GlStateManager.enableLighting();
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
}
