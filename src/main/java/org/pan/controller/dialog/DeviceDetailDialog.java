package org.pan.controller.dialog;

import com.google.inject.Inject;
import com.jgoodies.common.base.Strings;
import io.advantageous.guicefx.LoadedBy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.pan.model.Device;
import org.pan.model.DeviceType;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by panmingzhi on 2016/11/20 0020.
 */
@LoadedBy("/controller/deviceDetail.fxml")
public class DeviceDetailDialog implements Initializable{

    @FXML
    public GridPane root;

    @FXML
    public TextField name;

    @FXML
    public TextField address;

    @FXML
    public ComboBox type;

    private final Optional<Device> device;

    @Inject
    public DeviceDetailDialog(Optional<Device> device) {
        this.device = device;
    }

    public GridPane getRoot() {
        return root;
    }

    public Optional<Device> getDevice() {
        return device;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> items = FXCollections.observableArrayList(DeviceType.toStringArray());
        type.setItems(items);

        name.textProperty().bindBidirectional(device.nameProperty());
        address.textProperty().bindBidirectional(device.addressProperty());
        type.valueProperty().bindBidirectional(device.deviceTypeProperty());
    }
}
