package ipsis.buildersguides.util;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class RenderUtils {


    /**
     * Draw a shaded block
     * Must already be translated to the correct position
     */
    public static void drawBlockShaded() {
        drawBlockShaded(0.5F);
    }

    public static void drawBlockShaded(float scale) {

        float size = 1.0F * scale;

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        // Front - anticlockwise vertices
        // Back - clockwise vertices

        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);

        // xy anti-clockwise - front
        worldRenderer.pos(-size, -size, size).endVertex();
        worldRenderer.pos(size, -size, size).endVertex();
        worldRenderer.pos(size, size, size).endVertex();
        worldRenderer.pos(-size, size, size).endVertex();

        // xy clockwise - back
        worldRenderer.pos(-size, -size, -size).endVertex();
        worldRenderer.pos(-size, size, -size).endVertex();
        worldRenderer.pos(size, size, -size).endVertex();
        worldRenderer.pos(size, -size, -size).endVertex();

        // anti-clockwise - left
        worldRenderer.pos(-size, -size, -size).endVertex();
        worldRenderer.pos(-size, -size, size).endVertex();
        worldRenderer.pos(-size, size, size).endVertex();
        worldRenderer.pos(-size, size, -size).endVertex();

        // clockwise - right
        worldRenderer.pos(size, -size, -size).endVertex();
        worldRenderer.pos(size, size, -size).endVertex();
        worldRenderer.pos(size, size, size).endVertex();
        worldRenderer.pos(size, -size, size).endVertex();

        // anticlockwise - top
        worldRenderer.pos(-size, size, -size).endVertex();
        worldRenderer.pos(-size, size, size).endVertex();
        worldRenderer.pos(size, size, size).endVertex();
        worldRenderer.pos(size, size, -size).endVertex();

        // clockwise - bottom
        worldRenderer.pos(-size, -size, -size).endVertex();
        worldRenderer.pos(size, -size, -size).endVertex();
        worldRenderer.pos(size, -size, size).endVertex();
        worldRenderer.pos(-size, -size, size).endVertex();

        tessellator.draw();
    }

    public static void drawStairsShaded() {

        float size = 0.5F;

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {

            // Front - anticlockwise vertices
            // Back - clockwise vertices
            worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);

            GlStateManager.translate(0.0F, -0.25F, 0.0F);
            GlStateManager.scale(1.0F, 0.5F, 1.0F);

            // xy anti-clockwise - front
            worldRenderer.pos(-size, -size, size).endVertex();
            worldRenderer.pos(size, -size, size).endVertex();
            worldRenderer.pos(size, size, size).endVertex();
            worldRenderer.pos(-size, size, size).endVertex();

            // xy clockwise - back
            worldRenderer.pos(-size, -size, -size).endVertex();
            worldRenderer.pos(-size, size, -size).endVertex();
            worldRenderer.pos(size, size, -size).endVertex();
            worldRenderer.pos(size, -size, -size).endVertex();

            // anti-clockwise - left
            worldRenderer.pos(-size, -size, -size).endVertex();
            worldRenderer.pos(-size, -size, size).endVertex();
            worldRenderer.pos(-size, size, size).endVertex();
            worldRenderer.pos(-size, size, -size).endVertex();

            // clockwise - right
            worldRenderer.pos(size, -size, -size).endVertex();
            worldRenderer.pos(size, size, -size).endVertex();
            worldRenderer.pos(size, size, size).endVertex();
            worldRenderer.pos(size, -size, size).endVertex();

            // anticlockwise - top
            worldRenderer.pos(-size, size, -size).endVertex();
            worldRenderer.pos(-size, size, size).endVertex();
            worldRenderer.pos(size, size, size).endVertex();
            worldRenderer.pos(size, size, -size).endVertex();

            // clockwise - bottom
            worldRenderer.pos(-size, -size, -size).endVertex();
            worldRenderer.pos(size, -size, -size).endVertex();
            worldRenderer.pos(size, -size, size).endVertex();
            worldRenderer.pos(-size, -size, size).endVertex();

            tessellator.draw();

            worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);

            GlStateManager.translate(0.0F, 1.0F, -0.25F);
            GlStateManager.scale(1.0F, 1.0F, 0.5F);

            // xy anti-clockwise - front
            worldRenderer.pos(-size, -size, size).endVertex();
            worldRenderer.pos(size, -size, size).endVertex();
            worldRenderer.pos(size, size, size).endVertex();
            worldRenderer.pos(-size, size, size).endVertex();

            // xy clockwise - back
            worldRenderer.pos(-size, -size, -size).endVertex();
            worldRenderer.pos(-size, size, -size).endVertex();
            worldRenderer.pos(size, size, -size).endVertex();
            worldRenderer.pos(size, -size, -size).endVertex();

            // anti-clockwise - left
            worldRenderer.pos(-size, -size, -size).endVertex();
            worldRenderer.pos(-size, -size, size).endVertex();
            worldRenderer.pos(-size, size, size).endVertex();
            worldRenderer.pos(-size, size, -size).endVertex();

            // clockwise - right
            worldRenderer.pos(size, -size, -size).endVertex();
            worldRenderer.pos(size, size, -size).endVertex();
            worldRenderer.pos(size, size, size).endVertex();
            worldRenderer.pos(size, -size, size).endVertex();

            // anticlockwise - top
            worldRenderer.pos(-size, size, -size).endVertex();
            worldRenderer.pos(-size, size, size).endVertex();
            worldRenderer.pos(size, size, size).endVertex();
            worldRenderer.pos(size, size, -size).endVertex();

            // clockwise - bottom
            worldRenderer.pos(-size, -size, -size).endVertex();
            worldRenderer.pos(size, -size, -size).endVertex();
            worldRenderer.pos(size, -size, size).endVertex();
            worldRenderer.pos(-size, -size, size).endVertex();

            tessellator.draw();
        }
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }

    public static void drawStairOutline() {

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        float size = 0.5F;

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(0.0F, -0.25F, 0.0F);
            GlStateManager.scale(1.0F, 0.5F, 1.0F);

            /* Lower block */
            worldRenderer.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
            worldRenderer.pos(-size, size, -size).endVertex();
            worldRenderer.pos(size, size, -size).endVertex();
            worldRenderer.pos(size, -size, -size).endVertex();
            worldRenderer.pos(-size, -size, -size).endVertex();
            tessellator.draw();

            worldRenderer.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
            worldRenderer.pos(-size, size, size).endVertex();
            worldRenderer.pos(size, size, size).endVertex();
            worldRenderer.pos(size, -size, size).endVertex();
            worldRenderer.pos(-size, -size, size).endVertex();
            tessellator.draw();

            worldRenderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
            worldRenderer.pos(-size, size, -size).endVertex();
            worldRenderer.pos(-size, size, size).endVertex();
            worldRenderer.pos(size, size, -size).endVertex();
            worldRenderer.pos(size, size, size).endVertex();
            worldRenderer.pos(-size, -size, -size).endVertex();
            worldRenderer.pos(-size, -size, size).endVertex();
            worldRenderer.pos(size, -size, -size).endVertex();
            worldRenderer.pos(size, -size, size).endVertex();
            tessellator.draw();

            GlStateManager.translate(0.0F, 1.0F, -0.25F);
            GlStateManager.scale(1.0F, 1.0F, 0.5F);

            worldRenderer.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
            worldRenderer.pos(-size, size, -size).endVertex();
            worldRenderer.pos(size, size, -size).endVertex();
            worldRenderer.pos(size, -size, -size).endVertex();
            worldRenderer.pos(-size, -size, -size).endVertex();
            tessellator.draw();

            worldRenderer.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
            worldRenderer.pos(-size, size, size).endVertex();
            worldRenderer.pos(size, size, size).endVertex();
            worldRenderer.pos(size, -size, size).endVertex();
            worldRenderer.pos(-size, -size, size).endVertex();
            tessellator.draw();

            worldRenderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
            worldRenderer.pos(-size, size, -size).endVertex();
            worldRenderer.pos(-size, size, size).endVertex();
            worldRenderer.pos(size, size, -size).endVertex();
            worldRenderer.pos(size, size, size).endVertex();
            worldRenderer.pos(-size, -size, -size).endVertex();
            worldRenderer.pos(-size, -size, size).endVertex();
            worldRenderer.pos(size, -size, -size).endVertex();
            worldRenderer.pos(size, -size, size).endVertex();
            tessellator.draw();
        }
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }

    /**
     * Draw the outline of a block
     * Must already be translated to the correct position
     */
    public static void drawBlockOutline() { drawBlockOutline(0.5F); }
    public static void drawBlockOutline(float size) {

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        worldRenderer.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
        worldRenderer.pos(-size, size, -size).endVertex();
        worldRenderer.pos( size,  size, -size).endVertex();
        worldRenderer.pos( size, -size, -size).endVertex();
        worldRenderer.pos(-size, -size, -size).endVertex();
        tessellator.draw();

        worldRenderer.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
        worldRenderer.pos(-size,  size,  size).endVertex();
        worldRenderer.pos( size,  size,  size).endVertex();
        worldRenderer.pos( size, -size,  size).endVertex();
        worldRenderer.pos(-size, -size,  size).endVertex();
        tessellator.draw();

        worldRenderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
        worldRenderer.pos(-size,  size, -size).endVertex();
        worldRenderer.pos(-size,  size,  size).endVertex();
        worldRenderer.pos( size,  size, -size).endVertex();
        worldRenderer.pos( size,  size,  size).endVertex();
        worldRenderer.pos(-size, -size, -size).endVertex();
        worldRenderer.pos(-size, -size,  size).endVertex();
        worldRenderer.pos( size, -size, -size).endVertex();
        worldRenderer.pos( size, -size,  size).endVertex();
        tessellator.draw();
    }
}
