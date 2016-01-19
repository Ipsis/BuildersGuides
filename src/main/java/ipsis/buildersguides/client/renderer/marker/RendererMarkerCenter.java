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

        renderTargets(te, relX, relY, relZ);

        ColorBG color = te.getColor().getNext();
        renderBlockList(te.getCenterList(), te, relX, relY, relZ, color.getRed(), color.getGreen(), color.getBlue());
    }
}
