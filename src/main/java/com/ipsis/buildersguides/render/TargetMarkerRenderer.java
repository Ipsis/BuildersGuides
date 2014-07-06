package com.ipsis.buildersguides.render;

import com.ipsis.buildersguides.tileentity.TileCoordMarker;
import com.ipsis.buildersguides.tileentity.TileTargetMarker;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class TargetMarkerRenderer extends TileEntitySpecialRenderer {

    private final RenderItem customRenderItem;

    public TargetMarkerRenderer() {

        customRenderItem = new RenderItem()
        {
            @Override
            public boolean shouldBob()
            {
                return false;
            }
        };

        customRenderItem.setRenderManager(RenderManager.instance);
    }

    private void render(TileTargetMarker te, double x, double y, double z) {

    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float tick) {

        if (!(te instanceof TileTargetMarker))
            return;

        render((TileTargetMarker)te, x, y, z);
    }
}
