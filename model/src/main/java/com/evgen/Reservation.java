package com.evgen;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Reservation {

  @Id
  private ObjectId reservationId;

  @DBRef(lazy = true)
  private Hotel hotel;

  private String apartmentNumber;

  public Reservation() {
  }

  public Reservation(Hotel hotel, String apartmentNumber, ObjectId reservationId) {
    this.apartmentNumber = apartmentNumber;
    this.hotel = hotel;
    this.reservationId = reservationId;
  }

  public ObjectId getReservationId() {
    return reservationId;
  }

  public void setReservationId(ObjectId reservationId) {
    this.reservationId = reservationId;
  }

  public Hotel getHotel() {
    return hotel;
  }

  public void setHotel(Hotel hotel) {
    this.hotel = hotel;
  }

  public String getApartmentNumber() {
    return apartmentNumber;
  }

  public void setApartmentNumber(String apartmentNumber) {
    this.apartmentNumber = apartmentNumber;
  }
}
