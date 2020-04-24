package colors;

import application.SystemConstants;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public final class RandomColor {
    private static final Random random = new Random();
    private static final ArrayList<Color> usedColor = new ArrayList<>();
    private static final Color[] colorConstants = {
            Color.web("rgb(255, 118, 184)"), // pink
            Color.web("rgb(255, 62, 96)"), // red
            Color.web("rgb(255, 166, 62)"), // orange
            Color.web("rgb(148, 238, 106)"), // lime
            Color.web("rgb(62, 197, 255)"), // sky blue
            Color.web("rgb(165, 135, 251)"), // lavender
    };

    public static Color getColor() {
        Color color;
        if (usedColor.size() < colorConstants.length) {
            do {
                color = colorConstants[random.nextInt(colorConstants.length)];
            } while (usedColor.contains(color) || color.equals(SystemConstants.DEFAULT_COLOR));
        } else {
            do {
                int red = random.nextInt(256);
                int green = random.nextInt(256);
                int blue = random.nextInt(256);
                color = Color.rgb(red, green, blue);
            } while (usedColor.contains(color) || color.equals(SystemConstants.DEFAULT_COLOR));
        }
        addUsedColor(color);
        return color;
    }

    public static void removeUsedColor(Color color) {
        if (!color.equals(SystemConstants.DEFAULT_COLOR)) {
            usedColor.remove(color);
        }
    }

    public static void addUsedColor(Color color) {
        usedColor.add(color);
    }
}
