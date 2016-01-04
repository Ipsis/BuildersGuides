package ipsis.buildersguides.util;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class RenderUtils {

    public static void drawBlockOB() {

        final float HALF_SIZE = 0.4F;

        WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);

        // xy clockwise - back
        worldRenderer.pos(-HALF_SIZE, -HALF_SIZE, -HALF_SIZE).endVertex();
        worldRenderer.pos(-HALF_SIZE, +HALF_SIZE, -HALF_SIZE).endVertex();
        worldRenderer.pos(+HALF_SIZE, +HALF_SIZE, -HALF_SIZE).endVertex();
        worldRenderer.pos(+HALF_SIZE, -HALF_SIZE, -HALF_SIZE).endVertex();

        // xy anti-clockwise - front
        worldRenderer.pos(-HALF_SIZE, -HALF_SIZE, +HALF_SIZE).endVertex();
        worldRenderer.pos(+HALF_SIZE, -HALF_SIZE, +HALF_SIZE).endVertex();
        worldRenderer.pos(+HALF_SIZE, +HALF_SIZE, +HALF_SIZE).endVertex();
        worldRenderer.pos(-HALF_SIZE, +HALF_SIZE, +HALF_SIZE).endVertex();

        // anti-clockwise - left
        worldRenderer.pos(-HALF_SIZE, -HALF_SIZE, -HALF_SIZE).endVertex();
        worldRenderer.pos(-HALF_SIZE, -HALF_SIZE, +HALF_SIZE).endVertex();
        worldRenderer.pos(-HALF_SIZE, +HALF_SIZE, +HALF_SIZE).endVertex();
        worldRenderer.pos(-HALF_SIZE, +HALF_SIZE, -HALF_SIZE).endVertex();

        // clockwise - right
        worldRenderer.pos(+HALF_SIZE, -HALF_SIZE, -HALF_SIZE).endVertex();
        worldRenderer.pos(+HALF_SIZE, +HALF_SIZE, -HALF_SIZE).endVertex();
        worldRenderer.pos(+HALF_SIZE, +HALF_SIZE, +HALF_SIZE).endVertex();
        worldRenderer.pos(+HALF_SIZE, -HALF_SIZE, +HALF_SIZE).endVertex();

        // clockwise - bottom
        worldRenderer.pos(-HALF_SIZE, -HALF_SIZE, -HALF_SIZE).endVertex();
        worldRenderer.pos(+HALF_SIZE, -HALF_SIZE, -HALF_SIZE).endVertex();
        worldRenderer.pos(+HALF_SIZE, -HALF_SIZE, +HALF_SIZE).endVertex();
        worldRenderer.pos(-HALF_SIZE, -HALF_SIZE, +HALF_SIZE).endVertex();

        // anticlockwise - top
        worldRenderer.pos(-HALF_SIZE, +HALF_SIZE, -HALF_SIZE).endVertex();
        worldRenderer.pos(-HALF_SIZE, +HALF_SIZE, +HALF_SIZE).endVertex();
        worldRenderer.pos(+HALF_SIZE, +HALF_SIZE, +HALF_SIZE).endVertex();
        worldRenderer.pos(+HALF_SIZE, +HALF_SIZE, -HALF_SIZE).endVertex();

        Tessellator.getInstance().draw();
    }

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
