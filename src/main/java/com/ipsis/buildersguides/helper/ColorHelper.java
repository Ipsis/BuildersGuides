package com.ipsis.buildersguides.helper;

public class ColorHelper {

    public static enum Color {

        BLACK(0.0F, 0.0F, 0.0F),
        RED(1.0F, 0.0F, 0.0F),
        GREEN(0.0F, 1.0F, 0.0F),
        BROWN(1.0F, 1.0F, 0.0F),
        BLUE(0.0F, 0.0F, 1.0F),
        PURPLE(1.0F, 1.0F, 1.0F),
        CYAN(1.0F, 1.0F, 1.0F),
        LIGHTGRAY(1.0F, 1.0F, 1.0F),
        GRAY(1.0F, 1.0F, 1.0F),
        PINK(1.0F, 1.0F, 1.0F),
        LIME(1.0F, 1.0F, 1.0F),
        YELLOW(1.0F, 1.0F, 1.0F),
        LIGHTBLUE(1.0F, 1.0F, 1.0F),
        MAGENTA(1.0F, 1.0F, 1.0F),
        ORANGE(1.0F, 1.0F, 1.0F),
        WHITE(1.0F, 1.0F, 1.0F);

        public static Color[] VALID_COLORS = { BLACK, RED, GREEN, BROWN, BLUE, PURPLE, CYAN, LIGHTGRAY, GRAY, PINK, LIME, YELLOW, LIGHTBLUE, MAGENTA, ORANGE, WHITE };

        private float red;
        private float green;
        private float blue;

        private Color(float red, float green, float blue) {

            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        public float getRed() {

            return this.red;
        }

        public float getGreen() {

            return this.green;
        }

        public float getBlue() {

            return this.blue;
        }

        public Color getNext() {

            int next = ordinal();
            next++;

            if (next < 0 || next >= VALID_COLORS.length)
                next = 0;

            return VALID_COLORS[next];
        }
    }
}
