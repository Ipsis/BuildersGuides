package com.ipsis.buildersguides.gui;

import com.ipsis.buildersguides.gui.container.ContainerToolBox;
import com.ipsis.buildersguides.gui.container.InventoryToolBox;
import com.ipsis.buildersguides.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiToolBox extends GuiContainer {

    private final ItemStack parentStack;
    private final InventoryToolBox inventoryToolBox;

    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.GUI_TEXTURE_BASE + "toolBox.png");

    public GuiToolBox(EntityPlayer entityPlayer, InventoryToolBox inventoryToolBox) {

        super(new ContainerToolBox(entityPlayer, inventoryToolBox));

        xSize = 174;
        ySize = 177;

        this.inventoryToolBox = inventoryToolBox;
        this.parentStack = inventoryToolBox.parentStack;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1, 1, 1, 1);

        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
