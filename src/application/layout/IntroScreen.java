package application.layout;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import utils.SystemUtils;
import utils.TransitionUtils;

/**
 * Intro screen displayed on app launch.
 */
public class IntroScreen {

    /**
     * The progress bar shown on the intro screen.
     */
    private ProgressBar progressBar;

    /**
     * The root node of the screen.
     */
    @FXML
    private VBox root;
    /**
     * The app name text shown on the intro screen.
     */
    @FXML
    private Text header;

    /**
     * FXML initialize method, called after IntroScreen.fxml finishes loading.
     * Sets the text and css styles.
     */
    @FXML
    protected void initialize() {
        progressBar = new ProgressBar();
        progressBar.getStyleClass().add("progress-bar");
        header.setText(SystemUtils.APP_NAME);
        root.getChildren().add(progressBar);
        new Thread(() -> simulateProgress(0)).start();
    }

    /**
     * Simulates loading on the progress bar.
     * @param initValue the initial value
     */
    private void simulateProgress(double initValue) {
        double value = initValue;
        try {
            Thread.sleep(500);
            while (value < 1d) {
                Thread.sleep(getProgressTime(value));
                final double finalValue = value;
                Platform.runLater(() -> progressBar.setProgress(finalValue));
                value += 0.002d;
            }
            Thread.sleep(300);
            Platform.runLater(() -> TransitionUtils.fadeOut(root, SystemUtils.INTRO_DURATION, () -> root.setVisible(false)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to convert a value into progress on the progress bar
     * @param value the value passed to the method
     * @return progress time to update in progress bar
     */
    private long getProgressTime(double value) {
        if (value < 0.5) {
            return 3;
        } else if (value < 0.7) {
            return 4;
        } else if (value < 0.8) {
            return 5;
        } else if (value < 0.9) {
            return 8;
        } else {
            return 12;
        }
    }

}