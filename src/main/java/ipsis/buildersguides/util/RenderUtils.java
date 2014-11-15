package ipsis.buildersguides.util;

import org.lwjgl.opengl.GL11;

public class RenderUtils {

    /**
     * Translate to x,y,z and draw the outline of a block
     * where x,y,z is the center
     */
    public static void drawBlockOutline(float x, float y, float z) {

        GL11.glPushMatrix();
        {
            GL11.glTranslatef(x, y, z);

            GL11.glBegin(GL11.GL_LINE_LOOP);
            GL11.glVertex3f(-0.5F,  0.5F, -0.5F);
            GL11.glVertex3f( 0.5F,  0.5F, -0.5F);
            GL11.glVertex3f( 0.5F, -0.5F, -0.5F);
            GL11.glVertex3f(-0.5F, -0.5F, -0.5F);
            GL11.glEnd();

            GL11.glBegin(GL11.GL_LINE_LOOP);
            GL11.glVertex3f(-0.5F,  0.5F,  0.5F);
            GL11.glVertex3f( 0.5F,  0.5F,  0.5F);
            GL11.glVertex3f( 0.5F, -0.5F,  0.5F);
            GL11.glVertex3f(-0.5F, -0.5F,  0.5F);
            GL11.glEnd();

            GL11.glBegin(GL11.GL_LINES);
            GL11.glVertex3f(-0.5F,  0.5F, -0.5F);
            GL11.glVertex3f(-0.5F,  0.5F,  0.5F);
            GL11.glVertex3f( 0.5F,  0.5F, -0.5F);
            GL11.glVertex3f( 0.5F,  0.5F,  0.5F);
            GL11.glVertex3f(-0.5F, -0.5F, -0.5F);
            GL11.glVertex3f(-0.5F, -0.5F,  0.5F);
            GL11.glVertex3f( 0.5F, -0.5F, -0.5F);
            GL11.glVertex3f( 0.5F, -0.5F,  0.5F);
            GL11.glEnd();

        }
        GL11.glPopMatrix();
    }
}
