package utils;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.util.Duration;

public class TransitionUtils {

    private static FadeTransition fadeTransition = new FadeTransition();
    private static ScaleTransition scaleTransition = new ScaleTransition();
    private static TranslateTransition translateTransition = new TranslateTransition();

    public static void fadeOut(Node node, double millis) {
        fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(millis));
        fadeTransition.setNode(node);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
    }

    public static void fadeIn(Node node, double millis) {
        fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(millis));
        fadeTransition.setNode(node);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    public static void fadeOut(Node node, double millis, TakeAction takeAction) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(millis));
        fadeTransition.setNode(node);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((ActionEvent event) -> takeAction.action());
        fadeTransition.play();
    }

    public static void fadeIn(Node node, double millis, TakeAction takeAction) {
        fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(millis));
        fadeTransition.setNode(node);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setOnFinished((ActionEvent event) -> takeAction.action());
        fadeTransition.play();
    }

    public static void scaleIn(Node node, double millis) {
        scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(millis));
        scaleTransition.setNode(node);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(0);
        scaleTransition.setToY(0);
        scaleTransition.play();
    }

    public static void scaleIn(Node node, double millis, TakeAction takeAction) {
        scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(millis));
        scaleTransition.setNode(node);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(0);
        scaleTransition.setToY(0);
        scaleTransition.setOnFinished((ActionEvent event) -> takeAction.action());
        scaleTransition.play();
    }

    public static void scaleOut(Node node, double millis) {
        scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(millis));
        scaleTransition.setNode(node);
        scaleTransition.setFromX(0);
        scaleTransition.setFromY(0);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.play();
    }

    public static void scaleOut(Node node, double millis, TakeAction takeAction) {
        scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(millis));
        scaleTransition.setNode(node);
        scaleTransition.setFromX(0);
        scaleTransition.setFromY(0);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.setOnFinished((ActionEvent event) -> takeAction.action());
        scaleTransition.play();
    }

    public static void translateTo(Node node, double millis, double x, double y) {
        translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(millis));
        translateTransition.setNode(node);
        translateTransition.setToX(x);
        translateTransition.setToY(y);
        translateTransition.play();
    }

    public static void translateTo(Node node, double millis, double x, double y, TakeAction takeAction) {
        translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(millis));
        translateTransition.setNode(node);
        translateTransition.setToX(x);
        translateTransition.setToY(y);
        translateTransition.setOnFinished((ActionEvent event) -> takeAction.action());
        translateTransition.play();
    }

    public interface TakeAction {
        void action();
    }
}
