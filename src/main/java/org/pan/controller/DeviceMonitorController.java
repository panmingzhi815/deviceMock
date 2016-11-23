package org.pan.controller;

import com.google.inject.Inject;
import com.google.inject.Provider;
import io.advantageous.guicefx.LoadedBy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.pan.controller.dialog.DeviceDetailDialog;
import org.pan.controller.dialog.GenerateRecordDialog;
import org.pan.model.Device;
import org.pan.model.DeviceRecord;
import org.pan.model.EventType;
import org.pan.model.GenerateRecord;
import org.pan.server.HandlerDataProvider;
import org.pan.server.ResponseServer;
import org.pan.util.GenerateUtil;
import org.pan.util.JavafxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 设备模拟器
 * Created by panmingzhi on 2016/11/20 0020.
 */
@LoadedBy("/controller/deviceMonitor.fxml")
public class DeviceMonitorController implements Initializable,HandlerDataProvider {
    private Logger LOGGER = LoggerFactory.getLogger(DeviceMonitorController.class);
    private static final String DEVICE_JSON = "Device.json";
    private static final String RECORD_JSON = "Record.json";
    private final Provider<DeviceDetailDialog> deviceDetailDialogProvider;
    private final Provider<GenerateRecordDialog> generateRecordDialogProvider;
    private final ResponseServer reponseServer;

    @FXML
    public BorderPane root;
    @FXML
    public ToggleButton button_toggleServer;
    @FXML
    public TableView<Optional<Device>> deviceTable;
    @FXML
    public TableColumn<Device, String> nameColumn;
    @FXML
    public TableColumn<Device, String> addressColumn;
    @FXML
    public TableColumn<Device, String> typeColumn;
    @FXML
    public TableColumn<DeviceRecord,String> cardColumn;
    @FXML
    public TableColumn<DeviceRecord,String> timeColumn;
    @FXML
    public TableColumn<DeviceRecord,String> eventTypeColumn;
    @FXML
    public TableView<DeviceRecord> recordTable;
    private ObservableList<Optional<Device>> deviceObservableList = FXCollections.observableArrayList();
    private ObservableList<DeviceRecord> allRecordObservableList= FXCollections.observableArrayList();
    private ObservableList<DeviceRecord> recordTableObservableList= FXCollections.observableArrayList();
    private Map<String,List<DeviceRecord>> deviceRecordMap = new HashMap<>();

    @Inject
    public DeviceMonitorController(Provider<DeviceDetailDialog> deviceDetailDialog, Provider<GenerateRecordDialog> generateRecordDialogProvider, ResponseServer reponseServer) {
        this.deviceDetailDialogProvider = deviceDetailDialog;
        this.generateRecordDialogProvider = generateRecordDialogProvider;
        this.reponseServer = reponseServer;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavafxUtil.loadJsonToObservableList(DEVICE_JSON,Device.class,deviceObservableList);
        JavafxUtil.loadJsonToObservableList(RECORD_JSON,DeviceRecord.class,allRecordObservableList);

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().deviceTypeProperty());

        cardColumn.setCellValueFactory(cellData -> cellData.getValue().cardProperty());
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        eventTypeColumn.setCellValueFactory(cellData -> cellData.getValue().evenTypeProperty());

        deviceTable.setItems(deviceObservableList);
        recordTable.setItems(recordTableObservableList);

        groupDevice();
        deviceTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> filterDevice());
    }

    public void whenExistStoreData() {
        JavafxUtil.saveObservableListToJson(DEVICE_JSON, deviceObservableList);
        JavafxUtil.saveObservableListToJson(RECORD_JSON, allRecordObservableList);
    }

    public BorderPane getRoot() {
        return root;
    }

    public void addDevice() {
        DeviceDetailDialog deviceDetailDialog = this.deviceDetailDialogProvider.get();
        JavafxUtil.showDialog("添加设备", "请编辑设备信息", deviceDetailDialog.getRoot(), new ButtonType("确定")).ifPresent(e -> {
            Optional<Device> device = deviceDetailDialog.getDevice();
            deviceObservableList.add(device);
        });
    }

    public void editDevice() {
        if (JavafxUtil.isSelected(deviceTable)) {
            Optional<Device> selected = JavafxUtil.getSelected(deviceTable);
            DeviceDetailDialog deviceDetailDialog = this.deviceDetailDialogProvider.get();
            deviceDetailDialog.getDevice().copyFromDevice(selected);

            JavafxUtil.showDialog("添加设备", "请编辑设备信息", deviceDetailDialog.getRoot(), new ButtonType("确定")).ifPresent(e -> selected.copyFromDevice(deviceDetailDialog.getDevice()));
        }
    }

    public void deleteDevice() {
        JavafxUtil.showDialog("确认删除","你确定要删除这个设备吗?", Alert.AlertType.CONFIRMATION).filter(e->e == ButtonType.OK).ifPresent(e->{
            Optional<Device> selected = JavafxUtil.getSelected(deviceTable);
            deviceObservableList.remove(selected);
            allRecordObservableList.removeAll(deviceRecordMap.getOrDefault(selected.getName(),new ArrayList<>()));
            deviceRecordMap.remove(selected.getName());
        });
    }

    public void batchGenerateRecord() {
        if (JavafxUtil.isSelected(deviceTable)) {
            Optional<Device> selected = JavafxUtil.getSelected(deviceTable);
            GenerateRecordDialog deviceDetailDialog = this.generateRecordDialogProvider.get();
            JavafxUtil.showDialog("生成刷卡记录", "请编辑生成刷卡记录信息", deviceDetailDialog.getRoot(), new ButtonType("确定")).ifPresent(e -> {
                GenerateRecord generateRecord = deviceDetailDialog.getGenerateRecord();
                if (generateRecord.isAuto()) {
                    List<String> stringList = GenerateUtil.generateSCard(8, generateRecord.getNumber());
                    int index = 0;
                    for (String s : stringList) {
                        EventType eventType = EventType.parse(generateRecord.getEventType()).getEventType();
                        String format = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);
                        DeviceRecord deviceRecord = new DeviceRecord(s,eventType.name(), format,selected.getName());
                        System.out.println((index++) + deviceRecord.toString());
                        allRecordObservableList.add(deviceRecord);
                    }
                }else{
                    String format = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);
                    EventType eventType = EventType.parse(generateRecord.getEventType()).getEventType();
                    DeviceRecord deviceRecord = new DeviceRecord(generateRecord.getCard(), eventType.name(), format, selected.getName());
                    allRecordObservableList.add(deviceRecord);
                }
            });
            groupDevice();
            filterDevice();
        }
    }

    private void groupDevice() {
        Map<String, List<DeviceRecord>> collect = allRecordObservableList.stream().collect(Collectors.groupingBy(DeviceRecord::getDevice));
        this.deviceRecordMap.clear();
        this.deviceRecordMap.putAll(collect);
    }

    public void toggleServer() {
        if (reponseServer.isDisposed()) {
            reponseServer.start();
            button_toggleServer.setText("停止服务");
        }else{
            reponseServer.stop();
            button_toggleServer.setText("启动服务");
        }
    }

    private void filterDevice(){
        String selectDeviceName = Optional.ofNullable(JavafxUtil.getSelected(deviceTable)).map(Device::getName).orElse("");
        recordTableObservableList.setAll(this.deviceRecordMap.getOrDefault(selectDeviceName,new ArrayList<>()));
    }

    @Override
    public Optional<Device> getDevice(String address) {
        for (Optional<Device> device : deviceObservableList) {
            if (device.getAddress().equals(address)) {
                return device;
            }
        }
        return null;
    }

    @Override
    public DeviceRecord getFirstAndRemoveRecord(Optional<Device> device) {
        return null;
    }
}
