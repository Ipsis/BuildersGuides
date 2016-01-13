package ipsis.buildersguides.client.renderer;

import ipsis.buildersguides.item.ItemMarkerCard;
import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.ColorBG;
import ipsis.buildersguides.util.RenderUtils;
import ipsis.oss.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import sun.rmi.runtime.Log;

import java.util.List;
import java.util.Set;

public class MarkerRenderer extends TileEntitySpecialRenderer {

    private static final float RENDER_ALPHA = 0.7F;

    @Override
    public void renderTileEntityAt(TileEntity te, double relativeX, double relativeY, double relativeZ, float partialTicks, int blockDamageProgress) {

        if (te instanceof TileEntityMarker) {

            doRenderMarkerType((TileEntityMarker)te, relativeX, relativeY, relativeZ, partialTicks);

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

    private void doRenderMarkerType(TileEntityMarker te, double relX, double relY, double relZ, float partialTicks) {

        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        ItemStack itemStack = ItemMarkerCard.getItemStack(te.getType());

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(relX + 0.5F, relY + 0.5F, relZ + 0.5F);
            GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
            EntityItem entityItem = new EntityItem(te.getWorld(), 0.0D, 0.0D, 0.0D, itemStack);
            entityItem.getEntityItem().stackSize = 1;
            GlStateManager.scale(0.25F, 0.25F, 0.25F);
            renderItem.renderItem(itemStack, ItemCameraTransforms.TransformType.FIXED);
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
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
            GlStateManager.color(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), RENDER_ALPHA);

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
            GlStateManager.color(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), RENDER_ALPHA);

            worldRenderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
            for (EnumFacing f : EnumFacing.values()) {
                worldRenderer.pos(0.0F, 0.0F, 0.0F).endVertex();
                worldRenderer.pos(
                        0.0F + (f.getFrontOffsetX() * 64.0F),
                        0.0F + (f.getFrontOffsetY() * 64.0F),
                        0.0F + (f.getFrontOffsetZ() * 64.0F)).endVertex();
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
            GlStateManager.color(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), RENDER_ALPHA);

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

    private void doRenderCenter(TileEntityMarker te, double relX, double relY, double relZ, float partialTicks, int blockDamageProgress) {

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {
            // translate to center or te
            GlStateManager.translate(relX + 0.5F , relY + 0.5F, relZ + 0.5F);
            GlStateManager.disableLighting();
            GlStateManager.disableTexture2D();
            GlStateManager.color(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), RENDER_ALPHA);

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
            GlStateManager.color(nextColor.getRed(), nextColor.getGreen(), nextColor.getBlue(), RENDER_ALPHA);

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
            GlStateManager.color(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), RENDER_ALPHA);

            for (BlockPos p : te.getBlockList()) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(
                        (te.getPos().getX() - p.getX()) * -1.0F,
                        (te.getPos().getY() - p.getY()) * -1.0F,
                        (te.getPos().getZ() - p.getZ()) * -1.0F);
                renderBlock();
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
            GlStateManager.color(te.getColor().getRed(), te.getColor().getGreen(), te.getColor().getBlue(), RENDER_ALPHA);

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
            GlStateManager.color(nextColor.getRed(), nextColor.getGreen(), nextColor.getBlue(), RENDER_ALPHA);

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

    private static void renderBlock(float size) {

        RenderUtils.drawBlockShaded(size);
        //RenderUtils.drawBlockOutline(size);
    }

    private static void renderBlock() {

        RenderUtils.drawBlockShaded(0.2F);
        //RenderUtils.drawBlockOutline(0.4F);
    }
}
