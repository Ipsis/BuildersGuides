package ipsis.buildersguides.client.renderer.marker;

import ipsis.buildersguides.item.ItemMarkerCard;
import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import java.util.HashMap;

public class TESRMarker extends TileEntitySpecialRenderer<TileEntityMarker> {

    @Override
    public boolean isGlobalRenderer(TileEntityMarker te) {

        // BGZ004 Force the render even when the chunk is out of view
        return true;
    }

    private static HashMap<MarkerType, RendererMarker> map = new HashMap<MarkerType, RendererMarker>() {
        {
            put(MarkerType.AXIS, new RendererMarkerAxis());
            put(MarkerType.CENTER, new RendererMarkerCenter());
            put(MarkerType.CHUNK, new RendererMarkerChunk());
            put(MarkerType.GHOST, new RendererMarkerGhost());
            put(MarkerType.GHOSTSTAIRS, new RendererMarkerGhostStairs());
            /* put(MarkerType.LASER, new RendererMarkerLaser()); */
            put(MarkerType.RANGE, new RendererMarkerRange());
            put(MarkerType.SPACING, new RendererMarkerSpacing());
            put(MarkerType.WORLD, new RendererMarkerWorld());
            put(MarkerType.SHAPE, new RendererMarkerShape());
        }
    };

    @Override
    public void render(TileEntityMarker te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

        if (map.containsKey(te.getType())) {
            doRenderMarkerType(te, x, y, z, partialTicks);
            map.get(te.getType()).doRenderMarkerType(this, te, x, y, z, partialTicks);
        }
    }

    private void doRenderMarkerType(TileEntityMarker te, double relX, double relY, double relZ, float partialTicks) {

        ItemStack itemStack = ItemMarkerCard.getItemStack(te.getType());

        EntityItem entityItem = new EntityItem(te.getWorld(), 0.0D, 0.0D, 0.0D, itemStack);
        entityItem.getItem().setCount(1);

        /**
         * TODO Not sure I understand this properly yet - revisit this
         */
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {
            RenderHelper.enableStandardItemLighting();
            GlStateManager.translate(relX + 0.5F, relY + 0.5F, relZ + 0.5F);

            EnumFacing f = te.getFacing();
            if (f == EnumFacing.UP) {
                GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.translate(0.0F, 0.0F, -0.126F);
            } else if (f == EnumFacing.DOWN) {
                GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.translate(0.0F, 0.0F, 0.126F);
            } else if (f == EnumFacing.SOUTH) {
                GlStateManager.translate(0.0F, 0.0F, 0.126F);
            } else if (f == EnumFacing.NORTH) {
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.translate(0.0F, 0.0F, 0.126F);
            } else if (f == EnumFacing.WEST) {
                GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.translate(0.0F, 0.0F, 0.126F);
            } else if (f == EnumFacing.EAST) {
                GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.translate(0.0F, 0.0F, 0.126F);
            }

            GlStateManager.scale(0.25F, 0.25F, 0.25F);
            Minecraft.getMinecraft().getRenderItem().renderItem(itemStack, ItemCameraTransforms.TransformType.NONE);
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

}
