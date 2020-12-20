package com.boarz.auth.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "role_auth")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name", columnDefinition = "VARCHAR(255)")
  private String name;

  @Column(name = "description", columnDefinition = "text")
  private String description;

  @Column(name = "creation_date")
  private long creationDate;

  @Column(name = "update_date")
  private long updateDate;

  public Role(long id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public Role(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public Role() {}

  @PrePersist
  public void prePersist() {
    creationDate = new Date().getTime();
  }

  @PreUpdate
  public void preUpdate() {
    updateDate = new Date().getTime();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public long getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(long creationDate) {
    this.creationDate = creationDate;
  }

  public long getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(long updateDate) {
    this.updateDate = updateDate;
  }
}
