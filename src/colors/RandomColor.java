package colors;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public final class RandomColor {
    private static final Random random = new Random();
    private static final ArrayList<Color> usedColor = new ArrayList<>();
    private static final Color[] colorConstants = GlobalColor.getAllPalette();

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

    public static void removeUsedColor(Color color) {
        if (!color.equals(GlobalColor.DEFAULT_COLOR)) {
            usedColor.remove(color);
        }
    }

    public static void addUsedColor(Color color) {
        usedColor.add(color);
    }
}
