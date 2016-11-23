package org.pan.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import org.pan.controller.DeviceMonitorController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

/**
 * Created by panmingzhi on 2016/11/20 0020.
 */
public class JavafxUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(DeviceMonitorController.class);

    public static Optional<ButtonType> showDialog(String title, String headerText, Node node, ButtonType... buttons) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().setAll(buttons);
        alert.getDialogPane().setContent(node);
        return alert.showAndWait();
    }

    public static boolean isSelected(TableView deviceTable) {
        return !deviceTable.getSelectionModel().getSelectedItems().isEmpty();
    }

    public static <T> T getSelected(TableView<T> deviceTable) {
        return deviceTable.getSelectionModel().getSelectedItem();
    }

    public static Optional<ButtonType> showDialog(String title, String headerText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        return alert.showAndWait();
    }


    public static <T> void saveObservableListToJson(String deviceJson, ObservableList<T> deviceObservableList) {
        try (FileOutputStream os = new FileOutputStream(deviceJson)) {
            JSON.writeJSONString(os, deviceObservableList, SerializerFeature.PrettyFormat);
        } catch (IOException e) {
            LOGGER.warn("存储数据失败",e);
        }
    }

    public static <T> void loadJsonToObservableList(String deviceJson,Class<T> clz,ObservableList<T> observableList) {
        try {
            String jsonStr = new String(Files.readAllBytes(Paths.get(deviceJson)));
            List<T> objects = JSON.parseArray(jsonStr, clz);
            observableList.setAll(FXCollections.observableArrayList(objects));
        } catch (IOException e) {
            LOGGER.warn("读取数据失败",e);
        }
    }
}
