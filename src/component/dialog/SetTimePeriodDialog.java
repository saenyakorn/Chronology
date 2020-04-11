package component.dialog;

import application.SystemConstants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import jfxtras.scene.control.LocalDateTextField;

import java.io.IOException;

public class SetTimePeriodDialog extends Dialog {

    @FXML private VBox predefinedMode;
    @FXML private DatePicker predefinedModeDatePicker;
    @FXML private RadioButton dawnChoice;
    @FXML private RadioButton morningChoice;
    @FXML private RadioButton middayChoice;
    @FXML private RadioButton afternoonChoice;
    @FXML private RadioButton eveningChoice;
    @FXML private RadioButton nightChoice;

    @FXML private CheckBox customModeToggle;
    @FXML private VBox customMode;
    @FXML private LocalDateTextField customModeBeginDatePicker;
    @FXML private LocalDateTextField customModeEndDatePicker;

    @FXML private Button setButton;
    @FXML private Button cancelButton;

    public SetTimePeriodDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SetTimePeriodDialog.fxml"));
        fxmlLoader.setController(this);
        try {
            Parent root = fxmlLoader.load();
            stage.setTitle("Set Event Date & Time");
            stage.setScene(new Scene(root, SystemConstants.DIALOG_PREF_HEIGHT, SystemConstants.DIALOG_PREF_WIDTH * 2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setToggleGroup() {
        ToggleGroup predefinedTimePeriods = new ToggleGroup();
        dawnChoice.setToggleGroup(predefinedTimePeriods);
        morningChoice.setToggleGroup(predefinedTimePeriods);
        middayChoice.setToggleGroup(predefinedTimePeriods);
        afternoonChoice.setToggleGroup(predefinedTimePeriods);
        eveningChoice.setToggleGroup(predefinedTimePeriods);
        nightChoice.setToggleGroup(predefinedTimePeriods);
    }

    @FXML
    public void initialize() {
        setToggleGroup();

        boolean isCustomMode = false;
        customMode.setDisable(true);

        /*Move this to a method*/
        customModeToggle.setOnAction((ActionEvent e) -> {
            if(customModeToggle.isSelected()){
                predefinedMode.setDisable(true);
                customMode.setDisable(false);
            }
            else {
                predefinedMode.setDisable(false);
                customMode.setDisable(true);
            }
        });

        setButton.setOnAction((ActionEvent e) -> {

        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }
}
