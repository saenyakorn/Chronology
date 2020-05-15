package component.dialog.edit;

import component.base.BasicStoryComponent;
import component.components.timeModifier.PredefinedTimePeriod;
import component.components.timeModifier.TimePeriod;
import component.components.timeModifier.TimePeriodGenerator;
import component.dialog.Dialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jfxtras.scene.control.LocalDateTimeTextField;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SetTimePeriodDialog extends Dialog {
    private final BasicStoryComponent component;
    private boolean isCustomMode;
    private ToggleGroup predefinedTimePeriods;

    @FXML private VBox predefinedMode;
    @FXML private DatePicker predefinedModeDatePicker;
    @FXML private HBox predefinedModeToggleGroup;
    @FXML private RadioButton dawnChoice;
    @FXML private RadioButton morningChoice;
    @FXML private RadioButton middayChoice;
    @FXML private RadioButton afternoonChoice;
    @FXML private RadioButton eveningChoice;
    @FXML private RadioButton nightChoice;

    @FXML private CheckBox customModeToggle;
    @FXML private VBox customMode;
    @FXML private LocalDateTimeTextField customModeBeginDatePicker;
    @FXML private LocalDateTimeTextField customModeEndDatePicker;

    @FXML private Button setButton;
    @FXML private Button cancelButton;

    public SetTimePeriodDialog(BasicStoryComponent component) {
        this.component = component;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SetTimePeriodDialog.fxml"));
        fxmlLoader.setController(this);
        try {
            Parent root = fxmlLoader.load();
            stage.setTitle("Set Event Date & Time");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setToggleGroup() {
        predefinedTimePeriods = new ToggleGroup();
        dawnChoice.setToggleGroup(predefinedTimePeriods);
        morningChoice.setToggleGroup(predefinedTimePeriods);
        middayChoice.setToggleGroup(predefinedTimePeriods);
        afternoonChoice.setToggleGroup(predefinedTimePeriods);
        eveningChoice.setToggleGroup(predefinedTimePeriods);
        nightChoice.setToggleGroup(predefinedTimePeriods);
    }

    private PredefinedTimePeriod getSelectedPredefinedTimePeriod() {
        Toggle selectedToggle = predefinedTimePeriods.getSelectedToggle();
        if (dawnChoice.equals(selectedToggle)) {
            return PredefinedTimePeriod.DAWN;
        } else if (morningChoice.equals(selectedToggle)) {
            return PredefinedTimePeriod.MORNING;
        } else if (middayChoice.equals(selectedToggle)) {
            return PredefinedTimePeriod.MIDDAY;
        } else if (afternoonChoice.equals(selectedToggle)) {
            return PredefinedTimePeriod.AFTERNOON;
        } else if (eveningChoice.equals(selectedToggle)) {
            return PredefinedTimePeriod.EVENING;
        } else if (nightChoice.equals(selectedToggle)) {
            return PredefinedTimePeriod.NIGHT;
        }
        return null;
    }

    private void toggleCustomMode() {
        if(customModeToggle.isSelected()){
            predefinedMode.setDisable(true);
            customMode.setDisable(false);
            isCustomMode = true;
        }
        else {
            predefinedMode.setDisable(false);
            customMode.setDisable(true);
            isCustomMode = false;
        }
    }

    @FXML
    public void initialize() {
        setToggleGroup();
        customMode.setDisable(true);
        isCustomMode = false;
        customModeToggle.setOnAction((ActionEvent e) -> toggleCustomMode());

        setButton.setDisable(true);
        predefinedMode.setOnMouseExited((MouseEvent e) -> disableButton(setButton));
        customMode.setOnMouseExited((MouseEvent e) -> disableButton(setButton));

        System.out.println("Setting TimePeriod of " + component.toString());

        setButton.setOnAction((ActionEvent e) -> {
            if(!isSomeEmpty()){
                if(isCustomMode){
                    LocalDateTime beginDateTime = customModeBeginDatePicker.getLocalDateTime();
                    LocalDateTime endDateTime = customModeEndDatePicker.getLocalDateTime();
                    component.setTimePeriod(new TimePeriod(beginDateTime, endDateTime));
                    System.out.println("Custom date time set");
                }
                else {
                    LocalDate date = predefinedModeDatePicker.getValue();
                    component.setTimePeriod(TimePeriodGenerator.getTimePeriodFromPeriod(date, getSelectedPredefinedTimePeriod()));
                    System.out.println("Predefined date time set");
                }
                stage.close();
            }
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }

    private boolean isSomeEmpty() {
        boolean isSomeEmpty;
        if(isCustomMode) {
            isSomeEmpty = customModeBeginDatePicker.getLocalDateTime() == null || customModeEndDatePicker.getLocalDateTime() == null;
        }
        else {
            isSomeEmpty = predefinedModeDatePicker.getValue() == null || getSelectedPredefinedTimePeriod() == null;
        }
        return isSomeEmpty;
    }

    private void disableButton(Button button) {
        if (isSomeEmpty()) {
            button.setDisable(true);
        } else if(isCustomMode) {
            LocalDateTime beginDateTime = customModeBeginDatePicker.getLocalDateTime();
            LocalDateTime endDateTime = customModeEndDatePicker.getLocalDateTime();
            setButton.setDisable(endDateTime.isBefore(beginDateTime));
        } else {
            setButton.setDisable(false);
        }
    }

}
