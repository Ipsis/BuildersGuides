package ipsis.buildersguides.client.renderer.marker;

import ipsis.buildersguides.tileentity.TileEntityMarker;

public class RendererMarkerLaser extends RendererMarker {

    @Override
    public void doRenderMarkerType(TESRMarker tesrMarker, TileEntityMarker te, double relX, double relY, double relZ, float partialTicks) {

        if (te.getBlockList() == null || te.getBlockList().isEmpty())
            return;

        renderBlockList(te.getBlockList(), te, relX, relY, relZ);
    }
}
