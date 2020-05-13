package application.layout;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import utils.SystemUtils;
import utils.TransitionUtils;

public class IntroScreen {

    private ProgressBar progressBar;

    @FXML
    private VBox root;
    @FXML
    private Text header;

    @FXML
    protected void initialize() {
        progressBar = new ProgressBar();
        progressBar.getStyleClass().add("progress-bar");
        header.setText(SystemUtils.APP_NAME);
        root.getChildren().add(progressBar);
        new Thread(() -> simulateProgress(0)).start();
    }

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