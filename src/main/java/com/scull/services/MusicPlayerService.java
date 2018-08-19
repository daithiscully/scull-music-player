package com.scull.services;

import java.io.File;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MusicPlayerService {

  private static FileChooser fileChooser;
  private static DirectoryChooser directoryChooser;

  public static File openMusicFile(AnchorPane playerPane) {
    log.info("Opening music file");
    fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
    return fileChooser.showOpenDialog(playerPane.getScene().getWindow());
  }

  public static File openMusicFolder(AnchorPane playerPane) {
    log.info("Opening music folder");
    directoryChooser = new DirectoryChooser();
    directoryChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
    return directoryChooser.showDialog(playerPane.getScene().getWindow());
  }
}
