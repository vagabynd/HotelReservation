package com.evgen;

import java.io.Serializable;

public class Apartment implements Serializable {

  private String apartmentNumber;

  private String roomCount;

  public String getApartmentNumber() {
    return apartmentNumber;
  }

  public void setApartmentNumber(String apartmentNumber) {
    this.apartmentNumber = apartmentNumber;
  }

  public String getRoomCount() {
    return roomCount;
  }

  public void setRoomCount(String roomCount) {
    this.roomCount = roomCount;
  }
}