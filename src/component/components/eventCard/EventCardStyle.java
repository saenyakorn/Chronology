package component.components.eventCard;

import application.SystemConstants;

class EventCardStyle {

    public static final String PADDING = "-fx-padding: 10 15 10 15;";
    public static final String DROP_SHADOW = SystemConstants.DROP_SHADOW;
    
    public static final String WHITE_BACKGROUND = "-fx-background-color: " + SystemConstants.WHITE + ";";
    public static String COLOR_BACKGROUND = "-fx-background-color: " + "" + ";";
    public static final String WHITE_TEXT = "-fx-fill: " + SystemConstants.WHITE + ";";
    public static final String BLACK_TEXT = "-fx-fill: " + SystemConstants.BLACK + ";";
    
    //TODO : Add font weights to SystemConstants
    public static String BEGINDATE_TEXT = "-fx-font: 24px Noto Sans Bold;";
    public static String BEGINTIME_TEXT = "-fx-font: 14px Noto Sans Bold";

}
