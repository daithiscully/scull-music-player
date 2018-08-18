package com.scull;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
 */
@Slf4j
public class Main extends Application {

  public static void main(String[] args) {
    log.info("Starting application...");
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/fxml/music-player.fxml"));
    AnchorPane root = loader.load();
    primaryStage.setTitle("Scull Music Player");
    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
    Scene scene = new Scene(root);
    scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

    primaryStage.setScene(scene);
    primaryStage.show();

  }
}
