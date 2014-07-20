package com.ipsis.buildersguides.render;

import com.ipsis.buildersguides.tileentity.TileAdvancedMarker;
import com.ipsis.buildersguides.tileentity.TileCoordMarker;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;


public class AdvancedMarkerRenderer extends TileEntitySpecialRenderer {

    private final RenderItem customRenderItem;

    public AdvancedMarkerRenderer() {

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

    private void render(TileAdvancedMarker te, double x, double y, double z) {

    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float tick) {

        if (!(te instanceof TileAdvancedMarker))
            return;

        render((TileAdvancedMarker)te, x, y, z);
    }
}
