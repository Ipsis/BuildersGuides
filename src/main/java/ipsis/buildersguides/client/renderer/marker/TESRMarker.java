package ipsis.buildersguides.client.renderer.marker;

import ipsis.buildersguides.item.ItemMarkerCard;
import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.HashMap;

public class TESRMarker extends TileEntitySpecialRenderer {

    private static HashMap<MarkerType, RendererMarker> map = new HashMap<MarkerType, RendererMarker>() {
        {
            put(MarkerType.AXIS, new RendererMarkerAxis());
            put(MarkerType.CENTER, new RendererMarkerCenter());
            put(MarkerType.CHUNK, new RendererMarkerChunk());
            put(MarkerType.GHOST, new RendererMarkerGhost());
            put(MarkerType.GHOSTSTAIRS, new RendererMarkerGhostStairs());
            put(MarkerType.LASER, new RendererMarkerLaser());
            put(MarkerType.RANGE, new RendererMarkerRange());
            put(MarkerType.SPACING, new RendererMarkerSpacing());
            put(MarkerType.WORLD, new RendererMarkerWorld());
        }
    };

    @Override
    public void renderTileEntityAt(TileEntity te, double relativeX, double relativeY, double relativeZ, float partialTicks, int blockDamageProgress) {

        if (!(te instanceof TileEntityMarker))
            return;

        if (map.containsKey(((TileEntityMarker) te).getType())) {
            doRenderMarkerType((TileEntityMarker)te, relativeX, relativeY, relativeZ, partialTicks);
            map.get(((TileEntityMarker) te).getType()).doRenderMarkerType(this, (TileEntityMarker) te, relativeX, relativeY, relativeZ, partialTicks);
        }
    }

    private void doRenderMarkerType(TileEntityMarker te, double relX, double relY, double relZ, float partialTicks) {

        ItemStack itemStack = ItemMarkerCard.getItemStack(te.getType());

        EntityItem entityItem = new EntityItem(te.getWorld(), 0.0D, 0.0D, 0.0D, itemStack);
        entityItem.getEntityItem().stackSize = 1;

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {
            RenderHelper.enableStandardItemLighting();
            GlStateManager.enableLighting();
            GlStateManager.translate(relX + 0.5F, relY + 0.5F, relZ + 0.5F);

            GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(0.25F, 0.25F, 0.25F);
            Minecraft.getMinecraft().getRenderItem().renderItem(itemStack, ItemCameraTransforms.TransformType.NONE);
        }
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

}
