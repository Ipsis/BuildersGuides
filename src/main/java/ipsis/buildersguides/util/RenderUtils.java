package ipsis.buildersguides.util;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import org.lwjgl.opengl.GL11;

public class RenderUtils {

    /**
     * Draw a shaded block
     * Must already be translated to the correct position
     */
    private static void shadedCube(float size) {

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldRenderer = tessellator.getBuffer();

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

    private static void outlineCube(float size) {

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldRenderer = tessellator.getBuffer();

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

    public static void drawBlockShaded() {
        drawBlockShaded(0.5F);
    }

    public static void drawBlockShaded(float scale) {

        float size = 1.0F * scale;
        shadedCube(size);
    }

    public static void drawBlockOutline() { drawBlockOutline(0.5F); }
    public static void drawBlockOutline(float size) {

        outlineCube(size);
    }

    public static void drawStairsShaded() {

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {

            GlStateManager.translate(0.0F, -0.25F, 0.0F);
            GlStateManager.scale(1.0F, 0.5F, 1.0F);
            shadedCube(0.5F);
            GlStateManager.translate(0.0F, 1.0F, -0.25F);
            GlStateManager.scale(1.0F, 1.0F, 0.5F);
            shadedCube(0.5F);
        }
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }

    public static void drawStairsOutline() {

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        {

            GlStateManager.translate(0.0F, -0.25F, 0.0F);
            GlStateManager.scale(1.0F, 0.5F, 1.0F);
            outlineCube(0.5F);
            GlStateManager.translate(0.0F, 1.0F, -0.25F);
            GlStateManager.scale(1.0F, 1.0F, 0.5F);
            outlineCube(0.5F);
        }
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }

    public static void drawLine(float x1, float y1, float z1, float x2, float y2, float z2) {

        // TODO this is supposed to be a "fancy" line
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldRenderer = tessellator.getBuffer();
        GL11.glLineWidth(3.5F);
        worldRenderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x1, y1, z1).endVertex();
        worldRenderer.pos(x2, y2, z2).endVertex();
        tessellator.draw();
    }
}
