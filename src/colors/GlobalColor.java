package colors;

import javafx.scene.paint.Color;

public class GlobalColor {

    /* Default Color */
    public static final Color DEFAULT_COLOR = Color.rgb(99, 99, 99); // grey color

    /* Default Palette */
    public static final Color PINK = Color.web("rgb(255, 118, 184)"); // pink
    public static final Color RED = Color.web("rgb(255, 62, 96)"); // red
    public static final Color ORANGE = Color.web("rgb(255, 166, 62)"); // orange
    public static final Color LIME = Color.web("rgb(148, 238, 106)"); // lime
    public static final Color SKYBLUE = Color.web("rgb(62, 197, 255)"); // sky blue
    public static final Color LAVENDER = Color.web("rgb(165, 135, 251)"); // lavender

    public static final Color[] getAllPalette() {
        return new Color[]{PINK, RED, ORANGE, LIME, SKYBLUE, LAVENDER};
    }

    public static String colorToHex(Color color) {
        return String.format("#%02x%02x%02x",
                (int) (255 * color.getRed()),
                (int) (255 * color.getGreen()),
                (int) (255 * color.getBlue()));
    }
}
