package ipsis.buildersguides.util;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import org.lwjgl.opengl.GL11;

public class RenderUtils {

    public static void drawBlockOB() {

        final float HALF_SIZE = 0.4F;

        WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        worldRenderer.startDrawingQuads();

        // xy clockwise - back
        worldRenderer.addVertex(-HALF_SIZE, -HALF_SIZE, -HALF_SIZE);
        worldRenderer.addVertex(-HALF_SIZE, +HALF_SIZE, -HALF_SIZE);
        worldRenderer.addVertex(+HALF_SIZE, +HALF_SIZE, -HALF_SIZE);
        worldRenderer.addVertex(+HALF_SIZE, -HALF_SIZE, -HALF_SIZE);

        // xy anti-clockwise - front
        worldRenderer.addVertex(-HALF_SIZE, -HALF_SIZE, +HALF_SIZE);
        worldRenderer.addVertex(+HALF_SIZE, -HALF_SIZE, +HALF_SIZE);
        worldRenderer.addVertex(+HALF_SIZE, +HALF_SIZE, +HALF_SIZE);
        worldRenderer.addVertex(-HALF_SIZE, +HALF_SIZE, +HALF_SIZE);

        // anti-clockwise - left
        worldRenderer.addVertex(-HALF_SIZE, -HALF_SIZE, -HALF_SIZE);
        worldRenderer.addVertex(-HALF_SIZE, -HALF_SIZE, +HALF_SIZE);
        worldRenderer.addVertex(-HALF_SIZE, +HALF_SIZE, +HALF_SIZE);
        worldRenderer.addVertex(-HALF_SIZE, +HALF_SIZE, -HALF_SIZE);

        // clockwise - right
        worldRenderer.addVertex(+HALF_SIZE, -HALF_SIZE, -HALF_SIZE);
        worldRenderer.addVertex(+HALF_SIZE, +HALF_SIZE, -HALF_SIZE);
        worldRenderer.addVertex(+HALF_SIZE, +HALF_SIZE, +HALF_SIZE);
        worldRenderer.addVertex(+HALF_SIZE, -HALF_SIZE, +HALF_SIZE);

        // clockwise - bottom
        worldRenderer.addVertex(-HALF_SIZE, -HALF_SIZE, -HALF_SIZE);
        worldRenderer.addVertex(+HALF_SIZE, -HALF_SIZE, -HALF_SIZE);
        worldRenderer.addVertex(+HALF_SIZE, -HALF_SIZE, +HALF_SIZE);
        worldRenderer.addVertex(-HALF_SIZE, -HALF_SIZE, +HALF_SIZE);

        // anticlockwise - top
        worldRenderer.addVertex(-HALF_SIZE, +HALF_SIZE, -HALF_SIZE);
        worldRenderer.addVertex(-HALF_SIZE, +HALF_SIZE, +HALF_SIZE);
        worldRenderer.addVertex(+HALF_SIZE, +HALF_SIZE, +HALF_SIZE);
        worldRenderer.addVertex(+HALF_SIZE, +HALF_SIZE, -HALF_SIZE);

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

        worldRenderer.startDrawingQuads();

        // xy anti-clockwise - front
        worldRenderer.addVertex(-size, -size, size);
        worldRenderer.addVertex(size, -size, size);
        worldRenderer.addVertex(size, size, size);
        worldRenderer.addVertex(-size, size, size);

        // xy clockwise - back
        worldRenderer.addVertex(-size, -size, -size);
        worldRenderer.addVertex(-size, size, -size);
        worldRenderer.addVertex(size, size, -size);
        worldRenderer.addVertex(size, -size, -size);

        // anti-clockwise - left
        worldRenderer.addVertex(-size, -size, -size);
        worldRenderer.addVertex(-size, -size, size);
        worldRenderer.addVertex(-size, size, size);
        worldRenderer.addVertex(-size, size, -size);

        // clockwise - right
        worldRenderer.addVertex(size, -size, -size);
        worldRenderer.addVertex(size, size, -size);
        worldRenderer.addVertex(size, size, size);
        worldRenderer.addVertex(size, -size, size);

        // anticlockwise - top
        worldRenderer.addVertex(-size, size, -size);
        worldRenderer.addVertex(-size, size, size);
        worldRenderer.addVertex(size, size, size);
        worldRenderer.addVertex(size, size, -size);

        // clockwise - bottom
        worldRenderer.addVertex(-size, -size, -size);
        worldRenderer.addVertex(size, -size, -size);
        worldRenderer.addVertex(size, -size, size);
        worldRenderer.addVertex(-size, -size, size);

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

        worldRenderer.startDrawing(GL11.GL_LINE_LOOP);
        worldRenderer.addVertex(-size, size, -size);
        worldRenderer.addVertex( size,  size, -size);
        worldRenderer.addVertex( size, -size, -size);
        worldRenderer.addVertex(-size, -size, -size);
        tessellator.draw();

        worldRenderer.startDrawing(GL11.GL_LINE_LOOP);
        worldRenderer.addVertex(-size,  size,  size);
        worldRenderer.addVertex( size,  size,  size);
        worldRenderer.addVertex( size, -size,  size);
        worldRenderer.addVertex(-size, -size,  size);
        tessellator.draw();

        worldRenderer.startDrawing(GL11.GL_LINES);
        worldRenderer.addVertex(-size,  size, -size);
        worldRenderer.addVertex(-size,  size,  size);
        worldRenderer.addVertex( size,  size, -size);
        worldRenderer.addVertex( size,  size,  size);
        worldRenderer.addVertex(-size, -size, -size);
        worldRenderer.addVertex(-size, -size,  size);
        worldRenderer.addVertex( size, -size, -size);
        worldRenderer.addVertex( size, -size,  size);
        tessellator.draw();
    }
}
