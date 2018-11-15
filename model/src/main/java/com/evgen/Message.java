package com.evgen;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {

  private String id;
  private String endPoint;
  private List<Object> requestObject;

  public Message() {
  }

  public Message(String id, String endPoint) {
    this.id = id;
    this.endPoint = endPoint;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEndPoint() {
    return endPoint;
  }

  public void setEndPoint(String endPoint) {
    this.endPoint = endPoint;
  }

  public List<Object> getRequestObject() {
    return requestObject;
  }

  public void setRequestObject(List<Object> requestObject) {
    this.requestObject = requestObject;
  }
}
