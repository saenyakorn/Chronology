package component.dialog.edit;

import component.base.BasicStoryComponent;
import component.components.timeModifier.PredefinedTimePeriod;
import component.components.timeModifier.TimePeriod;
import component.components.timeModifier.TimePeriodGenerator;
import component.dialog.Dialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jfxtras.scene.control.LocalDateTimeTextField;
import utils.SystemUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A dialog called on a component. Sets the timePeriod of the component to the inputted timePeriod.
 */
public class SetTimePeriodDialog extends Dialog {
    /**
     * The component whose time period will be set.
     */
    private final BasicStoryComponent component;
    /**
     * Root node.
     */
    @FXML
    VBox root;
    /**
     * Area containing options available in predefined mode.
     */
    @FXML
    VBox predefinedMode;
    /**
     * Date picker in predefined mode.
     */
    @FXML
    DatePicker predefinedModeDatePicker;
    /**
     * Toggle group in predefined mode.
     */
    @FXML
    HBox predefinedModeToggleGroup;
    /**
     * Choice of <i>dawn</i> in predefined mode toggle group.
     */
    @FXML
    RadioButton dawnChoice;
    /**
     * Choice of <i>morning</i> in predefined mode toggle group.
     */
    @FXML
    RadioButton morningChoice;
    /**
     * Choice of <i>midday</i> in predefined mode toggle group.
     */
    @FXML
    RadioButton middayChoice;
    /**
     * Choice of <i>afternoon</i> in predefined mode toggle group.
     */
    @FXML
    RadioButton afternoonChoice;
    /**
     * Choice of <i>evening</i> in predefined mode toggle group.
     */
    @FXML
    RadioButton eveningChoice;
    /**
     * Choice of <i>night</i> in predefined mode toggle group.
     */
    @FXML
    RadioButton nightChoice;
    /**
     * Check box that toggles between predefined mode and custom mode.
     */
    @FXML
    CheckBox customModeToggle;
    /**
     * Area containing options available in custom mode.
     */
    @FXML
    VBox customMode;
    /**
     * Date picker in predefined mode. Used to pick beginDateTime.
     */
    @FXML
    LocalDateTimeTextField customModeBeginDatePicker;
    /**
     * Date picker in predefined mode. Used to pick endDateTime.
     */
    @FXML
    LocalDateTimeTextField customModeEndDatePicker;
    /**
     * Set (confirm) button.
     */
    @FXML
    Button setButton;
    /**
     * Cancel (close) button.
     */
    @FXML
    Button cancelButton;
    /**
     * Whether custom mode is active or not. When false, the active mode is predefined mode.
     */
    private boolean isCustomMode;
    /**
     * Toggle group of predefined time period options.
     */
    private ToggleGroup predefinedTimePeriods;

    /**
     * Constructor for SetTitleDialog.
     * @param component the component whose timePeriod will be set.
     */
    public SetTimePeriodDialog(BasicStoryComponent component) {
        this.component = component;
        setTitle(SystemUtils.EDIT_DATA_TIME);
        loadFXML("SetTimePeriodDialog.fxml", "Dialog.css");
    }

    /**
     * Initializes the toggle group.
     */
    private void initializeToggleGroup() {
        predefinedTimePeriods = new ToggleGroup();
        dawnChoice.setToggleGroup(predefinedTimePeriods);
        morningChoice.setToggleGroup(predefinedTimePeriods);
        middayChoice.setToggleGroup(predefinedTimePeriods);
        afternoonChoice.setToggleGroup(predefinedTimePeriods);
        eveningChoice.setToggleGroup(predefinedTimePeriods);
        nightChoice.setToggleGroup(predefinedTimePeriods);
    }

    /**
     * Converts a choice in the toggle group to a PredefinedTimePeriod.
     * @return the selected PredefinedTimePeriod.
     */
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

    /**
     * Toggles between predefined mode and custom mode.
     */
    private void toggleCustomMode() {
        if (customModeToggle.isSelected()) {
            predefinedMode.setDisable(true);
            customMode.setDisable(false);
            isCustomMode = true;
        } else {
            predefinedMode.setDisable(false);
            customMode.setDisable(true);
            isCustomMode = false;
        }
    }

    /**
     * FXML initialize method, called after SetTitleDialog.fxml finishes loading.
     * Does the following:
     * <ol>
     *     <li>Initializes toggle group.</li>
     *     <li>Sets mode to predefined mode and setups customModeToggle.</li>
     *     <li>Disables set button, and sets it to be enabled when the fields are completely filled.</li>
     *     <li>Setups dialog to be able to be dragged and clicked.</li>
     *     <li>Setups set button and cancel button.</li>
     * </ol>
     */
    @FXML
    public void initialize() {
        initializeToggleGroup();
        customMode.setDisable(true);
        isCustomMode = false;
        customModeToggle.setOnAction((ActionEvent e) -> toggleCustomMode());

        setButton.setDisable(true);
        predefinedMode.setOnMouseExited((MouseEvent e) -> disableButton(setButton));
        customMode.setOnMouseExited((MouseEvent e) -> disableButton(setButton));

        System.out.println("Setting TimePeriod of " + component.toString());

        root.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        setButton.setOnAction((ActionEvent e) -> {
            if (!isSomeEmpty()) {
                if (isCustomMode) {
                    LocalDateTime beginDateTime = customModeBeginDatePicker.getLocalDateTime();
                    LocalDateTime endDateTime = customModeEndDatePicker.getLocalDateTime();
                    component.setTimePeriodAndDisplay(new TimePeriod(beginDateTime, endDateTime));
                    System.out.println("Custom date time set");
                } else {
                    LocalDate date = predefinedModeDatePicker.getValue();
                    component.setTimePeriodAndDisplay(TimePeriodGenerator.getTimePeriodFromPeriod(date, getSelectedPredefinedTimePeriod()));
                    System.out.println("Predefined date time set");
                }
                stage.close();
            }
        });
        cancelButton.setOnAction((ActionEvent e) -> stage.close());
    }

    /**
     * Specific method for SetTimePeriodDialog. Checks whether or not all fields are filled.
     * @return whether or not all text fields are filled.
     */
    private boolean isSomeEmpty() {
        boolean isSomeEmpty;
        if (isCustomMode) {
            isSomeEmpty = customModeBeginDatePicker.getLocalDateTime() == null || customModeEndDatePicker.getLocalDateTime() == null;
        } else {
            isSomeEmpty = predefinedModeDatePicker.getValue() == null || getSelectedPredefinedTimePeriod() == null;
        }
        return isSomeEmpty;
    }

    /**
     * Disables the set button when all required fields are not yet filled.
     * @param button button to be disabled.
     */
    private void disableButton(Button button) {
        if (isSomeEmpty()) {
            button.setDisable(true);
        } else if (isCustomMode) {
            LocalDateTime beginDateTime = customModeBeginDatePicker.getLocalDateTime();
            LocalDateTime endDateTime = customModeEndDatePicker.getLocalDateTime();
            setButton.setDisable(endDateTime.isBefore(beginDateTime));
        } else {
            setButton.setDisable(false);
        }
    }

}
