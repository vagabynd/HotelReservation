package com.evgen;

public class Apartment {

  private String apartmentNumber;

  private String roomCount;

  public Apartment() {
  }

  public Apartment(String apartmentNumber, String roomCount) {
    this.apartmentNumber = apartmentNumber;
    this.roomCount = roomCount;
  }

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