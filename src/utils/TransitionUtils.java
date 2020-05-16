package utils;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Provides transition functions for the app.
 */
public class TransitionUtils {

    /**
     * Adds a fade out transition to the specified node.
     * @param node the node to perform the transition
     * @param takeAction action to perform after transition completes.
     * @param millis duration of transition in ms.
     */
    public static void fadeOut(Node node, double millis, TakeAction takeAction) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(millis));
        fadeTransition.setNode(node);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((ActionEvent event) -> takeAction.action());
        fadeTransition.play();
    }

    /**
     * An interface to be implemented in order to pass functions as a parameter
     */
    public interface TakeAction {
        void action();
    }
}
