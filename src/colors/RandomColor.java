package colors;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

/**
 * Randoms a color and keppes track of already used colors.
 */
public final class RandomColor {
    /**
     * Random object used as a randomizer.
     */
    private static final Random random = new Random();
    /**
     * ArrayList of already used colors.
     */
    private static final ArrayList<Color> usedColor = new ArrayList<>();
    /**
     * Array of all Colors in the default palette.
     */
    private static final Color[] colorConstants = GlobalColor.getAllPalette();

    /**
     * Gets a randomized color that is not yet used, prioritizing the default palette first.
     * @return a randomized color that is not yet used.
     */
    public static Color getColor() {
        Color color;
        if (usedColor.size() < colorConstants.length) {
            do {
                color = colorConstants[random.nextInt(colorConstants.length)];
            } while (usedColor.contains(color) || color.equals(GlobalColor.DEFAULT_COLOR));
        } else {
            do {
                int red = random.nextInt(256);
                int green = random.nextInt(256);
                int blue = random.nextInt(256);
                color = Color.rgb(red, green, blue);
            } while (usedColor.contains(color) || color.equals(GlobalColor.DEFAULT_COLOR));
        }
        addUsedColor(color);
        return color;
    }

    /**
     * Removes a Color from the list of used colors.
     * @param color the Color to remove.
     */
    public static void removeUsedColor(Color color) {
        if ((color != null) && !color.equals(GlobalColor.DEFAULT_COLOR)) {
            usedColor.remove(color);
        }
    }

    /**
     * Adds a color to the list of used colors.
     * @param color the Color to add.
     */
    public static void addUsedColor(Color color) {
        usedColor.add(color);
    }
}
