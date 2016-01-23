package ipsis.buildersguides.client.renderer.marker;

import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.ColorBG;

public class RendererMarkerCenter extends RendererMarker {

    @Override
    public void doRenderMarkerType(TESRMarker tesrMarker, TileEntityMarker te, double relX, double relY, double relZ, float partialTicks) {

        renderLineToTargets(te, relX, relY, relZ);
        renderTargets(te, relX, relY, relZ);
        renderRangeToTargets(tesrMarker, te, relX, relY, relZ);

        ColorBG color = te.getColor().getNext();
        renderBlockList(te.getCenterList(), te, relX, relY, relZ, color.getRed(), color.getGreen(), color.getBlue());
    }
}
