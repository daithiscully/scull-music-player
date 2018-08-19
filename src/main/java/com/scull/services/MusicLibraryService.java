package com.scull.services;

import com.scull.models.Song;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MusicLibraryService {

  // REQUIREMENT - A DEFAULT_FOLDER/path with audio files
  public static void loadInitialLibrary(TreeView<Song> libraryTreeView) {
    List<String> persistedFolderLocations = getPersistedFolderLocations();
    TreeItem<Song> rootItem = new TreeItem<>(Song.builder().name("Root").build());
    rootItem.setExpanded(true);

    persistedFolderLocations.forEach(location -> {
      log.info("LOADING FILES FROM: {}", location);
      File folder = new File(location);
      TreeItem<Song> artist = new TreeItem<>(Song.builder().name(folder.getName()).build());
      rootItem.getChildren().add(artist);
      File[] files = folder.listFiles();
      if (files == null) {
        return;
      }
      for (final File fileEntry : files) {
        if (fileEntry.isDirectory()) {
          log.info("Found a nested folder, ignoring.");
        } else {
          log.info("Loading file: {}", fileEntry.getName());
          Song song = Song.builder().name(fileEntry.getName()).file(fileEntry).build();
          TreeItem<Song> songTreeItem = new TreeItem<>(song);
          artist.getChildren().add(songTreeItem);
        }
      }
      artist.setExpanded(false);
    });

    libraryTreeView.setRoot(rootItem);
  }

  public static void addFolderToLibrary(TreeView<Song> libraryTreeView, AnchorPane mainPane) {
    TreeItem<Song> root = libraryTreeView.getRoot();
    File folder = MusicPlayerService.openMusicFolder(mainPane);
    // TODO
//    persistNewFolderLocation(folder.getAbsolutePath());
    Song songFolder = Song.builder().name(folder.getName()).file(folder).build();
    TreeItem<Song> newSongFolderItem = new TreeItem<>(songFolder);
    root.getChildren().add(newSongFolderItem);

    File[] files = folder.listFiles();
    if (files == null) {
      return;
    }
    for (final File fileEntry : files) {
      if (fileEntry.isDirectory()) {
        log.info("Found a nested folder, ignoring.");
      } else {
        log.info("Loading file: {}", fileEntry.getName());
        if (fileEntry.getName().endsWith(".mp3")) {
          Song song = Song.builder().name(fileEntry.getName()).file(fileEntry).build();
          TreeItem<Song> songTreeItem = new TreeItem<>(song);
          newSongFolderItem.getChildren().add(songTreeItem);
        }
      }
    }
  }

  public static List<String> getPersistedFolderLocations() {
    ArrayList<String> folderList = new ArrayList<>();

    try (InputStream inputStream = MusicLibraryService.class.getClassLoader()
        .getResourceAsStream("persistence/FOLDERS.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      while ((line = br.readLine()) != null) {
        folderList.add(line);
      }
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    return folderList;
  }

  // TODO figure out how best to approach this persistence logic
  public static void persistNewFolderLocation(String newLocation) {

    URL fileURL = MusicLibraryService.class.getClassLoader()
        .getResource("persistence/FOLDERS.txt");
    try {
      Files
          .write(Paths.get(fileURL.getPath()), newLocation.getBytes(), StandardOpenOption.APPEND);
    } catch (IOException e) {
      //exception handling left as an exercise for the reader
      log.error("ERROR persisting new folder location: {}", e.getMessage());
    }
  }
}
