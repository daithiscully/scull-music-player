package com.scull.controllers;

import static java.util.Objects.isNull;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.scull.models.Song;
import com.scull.services.MusicLibraryService;
import com.scull.services.MusicPlayerService;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
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
  private JFXSlider trackerSlider;

  @FXML
  private JFXButton addFolderToLibraryBtn;

  @FXML
  private TreeView<Song> libraryTreeView;

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
    MusicLibraryService.loadInitialLibrary(libraryTreeView);
    libraryTreeView.setOnMouseClicked(this::songSelection);
    trackerSlider.valueProperty().setValue(100);
    trackerSlider.valueProperty()
        .addListener((observable, oldValue, newValue) -> changeVolume(newValue));
    addFolderToLibraryBtn.setOnMouseClicked(
        event -> MusicLibraryService.addFolderToLibrary(libraryTreeView, mainPane));
  }

  private void changeVolume(Number newVolume) {
    double formattedVolumeValue = newVolume.doubleValue() / 100;
    log.info("Setting the volume to: {}", formattedVolumeValue);
    this.mediaPlayer.setVolume(formattedVolumeValue);
  }

  private void songSelection(MouseEvent event) {
    if (event.getClickCount() == 2) {
      TreeItem<Song> item = libraryTreeView.getSelectionModel().getSelectedItem();
      if (!isNull(item.getValue().getFile())) {
        log.info(item.getValue().getName());
        this.currentMusicFile = item.getValue().getFile();
        this.playMusicFile();
      }
    }
  }

  private void openMusicFile() {
    currentMusicFile = MusicPlayerService.openMusicFile(mainPane);
    if (isNull(currentMusicFile)) {
      return;
    }
    playMusicFile();
  }

  private void playMusicFile() {
    log.info("Playing {}", currentMusicFile.getName());
    Media media = new Media(currentMusicFile.toURI().toString());
    stopMusic();
    mediaPlayer = new MediaPlayer(media);
    nowPlayingTXT.setText("Playing: " + currentMusicFile.getName());
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
