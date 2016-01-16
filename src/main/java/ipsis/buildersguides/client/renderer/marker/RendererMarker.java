package ipsis.buildersguides.client.renderer.marker;

import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.RenderUtils;

public abstract class RendererMarker {

    public static final float RENDER_ALPHA = 0.7F;

    public abstract void doRenderMarkerType(TileEntityMarker te, double relX, double relY, double relZ, float partialTicks);

    protected void renderBlock(float size) {

        RenderUtils.drawBlockShaded(size);
        //RenderUtils.drawBlockOutline(size);
    }

    protected void renderBlock() {

        RenderUtils.drawBlockShaded(0.2F);
        //RenderUtils.drawBlockOutline(0.4F);
    }
}
