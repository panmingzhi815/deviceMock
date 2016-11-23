package org.pan;

import com.google.inject.Guice;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.pan.controller.DeviceMonitorController;

/**
 * 主程序
 * Created by panmingzhi on 2016/11/20 0020.
 */
public class MyApplication extends Application {

    public static void main(String[] args) {
        Application.launch(MyApplication.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        DeviceMonitorController instance = Guice.createInjector(new ApplicationModule()).getInstance(DeviceMonitorController.class);
        primaryStage.setScene(new Scene(instance.getRoot()));
        primaryStage.setTitle("设备模拟器");
        primaryStage.getIcons().add(new Image("/image/logo_64.png"));
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> instance.whenExistStoreData());
    }

}
