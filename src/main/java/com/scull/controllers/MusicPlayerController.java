package com.scull.controllers;

import static java.util.Objects.isNull;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.scull.services.MusicPlayerService;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MusicPlayerController implements Initializable {

  private MediaPlayer mediaPlayer;
  private File currentMusicFile;

  @FXML
  private AnchorPane mainPane;

  @FXML
  private JFXTextField nowPlayingTXT;

  @FXML
  private JFXButton playBtn;
  @FXML
  private JFXButton stopBtn;
  @FXML
  private JFXButton pauseBtn;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    nowPlayingTXT.setOnMouseClicked(event -> openMusicFile());
    playBtn.setVisible(false);
    stopBtn.setVisible(false);
    pauseBtn.setVisible(false);
    playBtn.setOnMouseClicked(e -> resumeMusic());
    stopBtn.setOnMouseClicked(event -> stopMusic());
    pauseBtn.setOnMouseClicked(event -> pauseMusic());
  }

  private void openMusicFile() {
    currentMusicFile = MusicPlayerService.openMusicFile(mainPane);
    if (isNull(currentMusicFile)) {
      return;
    }
    log.info("Playing {}", currentMusicFile.getName());
    nowPlayingTXT.setText("Playing: " + currentMusicFile.getName());
    Media media = new Media(currentMusicFile.toURI().toString());
    stopMusic();
    mediaPlayer = new MediaPlayer(media);
    mediaPlayer.play();
    stopBtn.setVisible(true);
    pauseBtn.setVisible(true);
  }

  private void pauseMusic() {
    if (isNull(mediaPlayer)) {
      return;
    }
    nowPlayingTXT.setText("Paused: " + currentMusicFile.getName());
    mediaPlayer.pause();
    playBtn.setVisible(true);
    stopBtn.setVisible(true);
    pauseBtn.setVisible(false);
  }

  private void resumeMusic() {
    if (isNull(mediaPlayer)) {
      return;
    }
    nowPlayingTXT.setText("Playing: " + currentMusicFile.getName());
    mediaPlayer.play();
    playBtn.setVisible(false);
    pauseBtn.setVisible(true);
  }

  private void stopMusic() {
    if (isNull(mediaPlayer)) {
      return;
    }
    nowPlayingTXT.setText("Select song...");
    mediaPlayer.stop();
    playBtn.setVisible(false);
    stopBtn.setVisible(false);
    pauseBtn.setVisible(false);
    mediaPlayer = null;
  }

}
