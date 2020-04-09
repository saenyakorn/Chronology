package component.dialog;

import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SetTimePeriodDialog extends Dialog {

    public SetTimePeriodDialog() {
        VBox root = new VBox();

        HBox hbox = new HBox();
        hbox.setSpacing(5);

        ToggleGroup modeSelector = new ToggleGroup();
        RadioButton predefined = new RadioButton("Use Predefined Times");
        RadioButton custom = new RadioButton("Customize Times");
        predefined.setToggleGroup(modeSelector);
        custom.setToggleGroup(modeSelector);
        predefined.setSelected(true);

        hbox.getChildren().addAll(predefined,custom);

        stage.setScene(new Scene(root,300,400));
    }

    private void predefinedMode() {

    }

    private void customMode() {
        Text setBeginDatePrompt = new Text("Begin Date:");
        Text setBeginTimePrompt = new Text("Begin Time:");
        Text setEndDatePrompt = new Text("End Date:");
        Text setEndTimePrompt = new Text("End Time:");

        DatePicker beginDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();
    }
}
