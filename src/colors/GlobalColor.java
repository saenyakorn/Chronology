package colors;

import javafx.scene.paint.Color;

/**
 * Contains globally defined palette and Color utility functions.
 */
public class GlobalColor {

    /* Default Color */
    /**
     * The default color for components and icons.
     */
    public static final Color DEFAULT_COLOR = Color.rgb(99, 99, 99);

    /* Default Palette */
    /**
     * Default pink.
     */
    public static final Color PINK = Color.web("rgb(255, 118, 184)");
    /**
     * Default red.
     */
    public static final Color RED = Color.web("rgb(255, 62, 96)");
    /**
     * Default orange.
     */
    public static final Color ORANGE = Color.web("rgb(255, 166, 62)");
    /**
     * Default green.
     */
    public static final Color LIME = Color.web("rgb(148, 238, 106)");
    /**
     * Default blue.
     */
    public static final Color SKYBLUE = Color.web("rgb(62, 197, 255)");
    /**
     * Default purple.
     */
    public static final Color LAVENDER = Color.web("rgb(165, 135, 251)");

    /**
     * Getter for the default palette.
     * @return an array of all Colors in the default palette.
     */
    public static final Color[] getAllPalette() {
        return new Color[]{PINK, RED, ORANGE, LIME, SKYBLUE, LAVENDER};
    }

    /**
     * Converts a Color object into a hex string.
     * @param color the Color to be converted
     * @return the passed Color in hex string format
     */
    public static String colorToHex(Color color) {
        return String.format("#%02x%02x%02x",
                (int) (255 * color.getRed()),
                (int) (255 * color.getGreen()),
                (int) (255 * color.getBlue()));
    }
}
