package application;

public class SystemConstants {

    /* Application constants */
    public static final String APP_NAME = "Chronology";
    public static final int SIDEBAR_PREF_WIDTH = 200;
    
    /* Styling constants */
    public static final int SPACING = 8;
    public static final int BORDER_RADIUS = 20;
    public static final String DROP_SHADOW = " -fx-effect: dropshadow(gaussian, rgba(28,28,28,0.2), 10, 0, 0, 10);";

    /* Font Constants */
    public static final String FONT_REGULAR_PATH = "file:res/fonts/NotoSansThai-Regular.ttf";
    public static final String FONT_BOLD_PATH = "file:res/fonts/NotoSansThai-Bold.ttf";
    public static final int HEADING_WEIGHT = 14;
    public static final int REGULAR_WEIGHT = 12;

    public static final String LOADFONT_REGULAR = "@font-face {" +
            "    font-family: NotoSansRegular;" +
            "    src: url('" + FONT_REGULAR_PATH + "');" +
            "}";

    public static final String LOADFONT_BOLD = "@font-face {" +
            "    font-family: NotoSansBold;" +
            "    src: url('" + FONT_BOLD_PATH + "');" +
            "}";

    /*Color scheme constants*/
    //Monochrome
    public static final String WHITE = "#FFFFFF";
    public static final String LIGHT_GRAY = "#F6F6F6";
    public static final String MEDIUM_GRAY = "#4F4F4F";
    public static final String DARK_GRAY = "#6B6B6B";
    public static final String BLACK = "#1C1C1C";

    //Default palette
    public static final String PINK = "#FF76B8";
    public static final String RED = "#FF3E60";
    public static final String ORANGE = "#FFA63E";
    public static final String LIME = "#94EE6A";
    public static final String SKY_BLUE = "#3EC5FF";
    public static final String LAVENDER = "#A587FB";

}
