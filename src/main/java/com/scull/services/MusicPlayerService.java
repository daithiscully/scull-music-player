package com.scull.services;

import com.scull.controllers.MusicPlayerController;
import java.io.File;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MusicPlayerService {

  private static FileChooser fileChooser;

  public static File openMusicFile(AnchorPane playerPane) {
    log.info("Opening music file");
    fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "\\sample-audio-files"));
    return fileChooser.showOpenDialog(playerPane.getScene().getWindow());
  }
}
