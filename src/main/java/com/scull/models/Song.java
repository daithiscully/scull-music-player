package com.scull.models;

import java.io.File;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Song {

  private String name;
  private File file;


  @Override
  public String toString() {
    return name;
  }
}
