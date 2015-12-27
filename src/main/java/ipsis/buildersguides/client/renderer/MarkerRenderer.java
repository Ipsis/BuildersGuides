package ipsis.buildersguides.client.renderer;

import ipsis.buildersguides.tileentity.TileEntityMarker;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class MarkerRenderer extends TileEntitySpecialRenderer {

    @Override
    public void renderTileEntityAt(TileEntity te, double posX, double posY, double posZ, float tick, int p_180535_9_) {

        if (te instanceof TileEntityMarker)
            doRender((TileEntityMarker)te, posX, posY, posZ, tick, p_180535_9_);
    }

    public void doRender(TileEntity te, double posX, double posY, double posZ, float tick, int p_180535_9_) {

    }
}
