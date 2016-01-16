package ipsis.buildersguides.util;

public enum ColorBG {
    BLACK(0.0F, 0.0F, 0.0F),
    RED(1.0F, 0.0F, 0.0F),
    GREEN(0.0F, 1.0F, 0.0F),
    BROWN(0.1F, 0.0F, 0.0F),
    BLUE(0.0F, 0.0F, 1.0F),
    PURPLE(1.0F, 0.0F, 1.0F),
    CYAN(0.5F, 1.0F, 1.0F),
    LIGHTGRAY(0.8F, 0.8F, 0.8F),
    GRAY(1.0F, 1.0F, 1.0F),
    PINK(1.0F, 1.0F, 1.0F),
    LIME(1.0F, 1.0F, 1.0F),
    YELLOW(1.0F, 1.0F, 0.0F),
    LIGHTBLUE(0.0F, 0.5F, 1.0F),
    MAGENTA(0.5F, 0.5F, 0.5F),
    ORANGE(1.0F, 0.5F, 0.0F),
    WHITE(1.0F, 1.0F, 1.0F);

    public static ColorBG[] VALID_COLORS = { BLACK, RED, GREEN, BROWN, BLUE, PURPLE, CYAN, LIGHTGRAY, GRAY, PINK, LIME, YELLOW, LIGHTBLUE, MAGENTA, ORANGE, WHITE };

    public static ColorBG getColor(int id) {

        if (id >= 0 && id < VALID_COLORS.length)
            return VALID_COLORS[id];

        return ColorBG.WHITE;
    }

    private float red;
    private float green;
    private float blue;

    ColorBG(float red, float green, float blue) {

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

    public ColorBG getNext() {

        int next = ordinal();
        next++;

        if (next < 0 || next >= VALID_COLORS.length)
            next = 0;

        return VALID_COLORS[next];
    }

    @Override
    public String toString() {

        return "R:" + this.red + " G:" + this.getGreen() + " B:" + this.getBlue();
    }
}
