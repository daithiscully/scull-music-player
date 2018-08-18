package com.scull.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private String userName;
  private String email;
  private String password;
  private Long tenantId;

  public User(String email, String password) {
    this.email = email;
    this.password = password;
    this.tenantId = -1L;
  }
}
