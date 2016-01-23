package ipsis.buildersguides.client.renderer.marker;

import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.ColorBG;

public class RendererMarkerGhost extends RendererMarker {
    @Override
    public void doRenderMarkerType(TESRMarker tesrMarker, TileEntityMarker te, double relX, double relY, double relZ, float partialTicks) {

        if (te.getBlockList() == null || te.getBlockList().isEmpty())
            return;

        renderBlockList(te.getBlockList(), te, relX, relY, relZ);
        ColorBG color = te.getColor().getNext();
        renderBlockList(te.getCenterList(), te, relX, relY, relZ, color.getRed(), color.getGreen(), color.getBlue());
    }
}
