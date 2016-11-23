package org.pan.controller.dialog;

import io.advantageous.guicefx.LoadedBy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.pan.model.DeviceType;
import org.pan.model.EventType;
import org.pan.model.GenerateRecord;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by panmingzhi on 2016/11/20 0020.
 */
@LoadedBy("/controller/generateRecord.fxml")
public class GenerateRecordDialog implements Initializable{
    @FXML
    public GridPane root;
    @FXML
    public TextField text_card;
    @FXML
    public RadioButton radio_auto;
    @FXML
    public RadioButton radio_hand;
    @FXML
    public Slider slider_number;
    @FXML
    public ToggleGroup type;
    @FXML
    public ComboBox eventType;

    private GenerateRecord generateRecord = new GenerateRecord();

    public GridPane getRoot() {
        return root;
    }

    public GenerateRecord getGenerateRecord() {
        return generateRecord;
    }

    public void action_auto(ActionEvent actionEvent) {
        text_card.setDisable(true);
    }

    public void action_hand(ActionEvent actionEvent) {
        text_card.setDisable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        type.getSelectedToggle().selectedProperty().bindBidirectional(generateRecord.autoProperty());
        text_card.textProperty().bindBidirectional(generateRecord.cardProperty());
        slider_number.valueProperty().bindBidirectional(generateRecord.numberProperty());
        eventType.valueProperty().bindBidirectional(generateRecord.eventTypeProperty());

        ObservableList<String> items = FXCollections.observableArrayList(EventType.toStringArray());
        eventType.setItems(items);
    }
}
