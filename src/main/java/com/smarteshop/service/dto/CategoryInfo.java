package com.smarteshop.service.dto;

import java.util.List;

public class CategoryInfo {

  private String name;
  private long id;
  private List<CategoryInfo> sub;
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }
  public List<CategoryInfo> getSub() {
    return sub;
  }
  public void setSub(List<CategoryInfo> sub) {
    this.sub = sub;
  }
  @Override
  public String toString() {
    return "CategoryInfo [name=" + name + ", id=" + id + ", sub=" + sub + "]";
  }


}
