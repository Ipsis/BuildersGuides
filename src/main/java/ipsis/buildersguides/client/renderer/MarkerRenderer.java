package ipsis.buildersguides.client.renderer;

import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.ColorBG;
import ipsis.buildersguides.util.RenderUtils;
import ipsis.oss.LogHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import sun.rmi.runtime.Log;

import java.util.List;
import java.util.Set;

public class MarkerRenderer extends TileEntitySpecialRenderer {

    @Override
    public void renderTileEntityAt(TileEntity te, double relativeX, double relativeY, double relativeZ, float partialTicks, int blockDamageProgress) {

        if (te instanceof TileEntityMarker) {
            if (((TileEntityMarker) te).getType() == MarkerType.SPACING)
                doRenderSpacing((TileEntityMarker) te, relativeX, relativeY, relativeZ, partialTicks, blockDamageProgress);
            else if (((TileEntityMarker) te).getType() == MarkerType.AXIS)
                doRenderAxis((TileEntityMarker)te, relativeX, relativeY, relativeZ, partialTicks, blockDamageProgress);
            else if (((TileEntityMarker) te).getType() == MarkerType.LASER)
                doRenderLaser((TileEntityMarker)te, relativeX, relativeY, relativeZ, partialTicks, blockDamageProgress);
            else if (((TileEntityMarker) te).getType() == MarkerType.WORLD)
                doRenderWorld((TileEntityMarker)te, relativeX, relativeY, relativeZ, partialTicks, blockDamageProgress);
            else if (((TileEntityMarker) te).getType() == MarkerType.CENTER)
                doRenderCenter((TileEntityMarker)te, relativeX, relativeY, relativeZ, partialTicks, blockDamageProgress);
            else if (((TileEntityMarker) te).getType() == MarkerType.GHOST)
                doRenderGhost((TileEntityMarker)te, relativeX, relativeY, relativeZ, partialTicks, blockDamageProgress);
        }
    }

    private void doRenderLaser(TileEntityMarker te, double relX, double relY, double relZ, float partialTicks, int blockDamageProgress) {

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldRenderer = tessellator.getWorldRenderer();

            // translate to center or te
            GlStateManager.translate(relX + 0.5F , relY + 0.5F, relZ + 0.5F);
            GlStateManager.disableLighting();
            GlStateManager.disableTexture2D();
            GlStateManager.color(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), 0.8F);

            worldRenderer.startDrawing(GL11.GL_LINES);
            for (EnumFacing f : EnumFacing.values()) {
                if (te.getV(f) != 0) {
                    worldRenderer.addVertex(0.0F, 0.0F, 0.0F);
                    worldRenderer.addVertex(
                            0.0F + (f.getFrontOffsetX() * 64.0F),
                            0.0F + (f.getFrontOffsetY() * 64.0F),
                            0.0F + (f.getFrontOffsetZ() * 64.0F));
                }
            }
            tessellator.draw();
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void doRenderAxis(TileEntityMarker te, double relX, double relY, double relZ, float partialTicks, int blockDamageProgress) {

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldRenderer = tessellator.getWorldRenderer();

            // translate to center or te
            GlStateManager.translate(relX + 0.5F , relY + 0.5F, relZ + 0.5F);
            GlStateManager.disableLighting();
            GlStateManager.disableTexture2D();
            GlStateManager.color(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), 0.8F);

            worldRenderer.startDrawing(GL11.GL_LINES);
            for (EnumFacing f : EnumFacing.values()) {
                worldRenderer.addVertex(0.0F, 0.0F, 0.0F);
                worldRenderer.addVertex(
                        0.0F + (f.getFrontOffsetX() * 64.0F),
                        0.0F + (f.getFrontOffsetY() * 64.0F),
                        0.0F + (f.getFrontOffsetZ() * 64.0F));
            }
            tessellator.draw();
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void doRenderWorld(TileEntityMarker te, double relX, double relY, double relZ, float partialTicks, int blockDamageProgress) {

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
            GlStateManager.color(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), 0.8F);

            worldRenderer.startDrawing(GL11.GL_LINES);
            for (BlockPos p : te.getBlockList()) {
                worldRenderer.addVertex(0.0F, 0.0F, 0.0F);
                worldRenderer.addVertex(
                        (te.getPos().getX() - p.getX()) * -1.0F,
                        (te.getPos().getY() - p.getY()) * -1.0F,
                        (te.getPos().getZ() - p.getZ()) * -1.0F);
            }
            tessellator.draw();
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void doRenderCenter(TileEntityMarker te, double relX, double relY, double relZ, float partialTicks, int blockDamageProgress) {

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {
            // translate to center or te
            GlStateManager.translate(relX + 0.5F , relY + 0.5F, relZ + 0.5F);
            GlStateManager.disableLighting();
            GlStateManager.disableTexture2D();
            GlStateManager.color(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), 1.0F);

            // render target points
            for (EnumFacing f : EnumFacing.VALUES) {
                if (te.hasTarget(f)) {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(
                            (te.getPos().getX() - te.getTarget(f).getX()) * -1.0F,
                            (te.getPos().getY() - te.getTarget(f).getY()) * -1.0F,
                            (te.getPos().getZ() - te.getTarget(f).getZ()) * -1.0F);
                    RenderUtils.drawBlockOutline(0.5005F);
                    GlStateManager.popMatrix();
                }
            }

            // render the center points
            ColorBG nextColor = te.getColor().getNext();
            GlStateManager.color(nextColor.getRed(), nextColor.getGreen(), nextColor.getBlue(), 1.0F);

            for (BlockPos p : te.getCenterList()) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(
                        (te.getPos().getX() - p.getX()) * -1.0F,
                        (te.getPos().getY() - p.getY()) * -1.0F,
                        (te.getPos().getZ() - p.getZ()) * -1.0F);
                RenderUtils.drawBlockOutline(0.5005F);
                GlStateManager.popMatrix();
            }
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void doRenderSpacing(TileEntityMarker te, double relX, double relY, double relZ, float partialTicks, int blockDamageProgress) {

        if (te.getBlockList() == null || te.getBlockList().isEmpty())
            return;

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {
            // translate to center or te
            GlStateManager.translate(relX + 0.5F , relY + 0.5F, relZ + 0.5F);
            GlStateManager.disableLighting();
            GlStateManager.disableTexture2D();
            GlStateManager.color(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), 1.0F);

            for (BlockPos p : te.getBlockList()) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(
                        (te.getPos().getX() - p.getX()) * -1.0F,
                        (te.getPos().getY() - p.getY()) * -1.0F,
                        (te.getPos().getZ() - p.getZ()) * -1.0F);
                RenderUtils.drawBlockShaded(0.4F);
                GlStateManager.popMatrix();
            }
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void doRenderGhost(TileEntityMarker te, double relX, double relY, double relZ, float partialTicks, int blockDamageProgress) {

        if (te.getBlockList() == null || te.getBlockList().isEmpty())
            return;

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {
            // translate to center or te
            GlStateManager.translate(relX + 0.5F , relY + 0.5F, relZ + 0.5F);
            GlStateManager.disableLighting();
            GlStateManager.disableTexture2D();
            GlStateManager.color(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), 0.8F);

            for (BlockPos p : te.getBlockList()) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(
                        (te.getPos().getX() - p.getX()) * -1.0F,
                        (te.getPos().getY() - p.getY()) * -1.0F,
                        (te.getPos().getZ() - p.getZ()) * -1.0F);
                RenderUtils.drawBlockShaded(0.4F);
                GlStateManager.popMatrix();
            }

            // render the center points
            ColorBG nextColor = te.getColor().getNext();
            GlStateManager.color(nextColor.getRed(), nextColor.getGreen(), nextColor.getBlue(), 1.0F);

            for (BlockPos p : te.getCenterList()) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(
                        (te.getPos().getX() - p.getX()) * -1.0F,
                        (te.getPos().getY() - p.getY()) * -1.0F,
                        (te.getPos().getZ() - p.getZ()) * -1.0F);
                RenderUtils.drawBlockOutline(0.5005F);
                GlStateManager.popMatrix();
            }
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
}
