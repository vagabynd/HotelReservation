package com.evgen;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Apartment {

  @Id
  private ObjectId apartmentId;

  private Integer roomCount;

  private Hotel hotel;

  public ObjectId getApartmentId() {
    return apartmentId;
  }

  public void setApartmentId(ObjectId apartmentId) {
    this.apartmentId = apartmentId;
  }

  public Integer getRoomCount() {
    return roomCount;
  }

  public void setRoomCount(Integer roomCount) {
    this.roomCount = roomCount;
  }

  public Hotel getHotel() {
    return hotel;
  }

  public void setHotel(Hotel hotel) {
    this.hotel = hotel;
  }
}
