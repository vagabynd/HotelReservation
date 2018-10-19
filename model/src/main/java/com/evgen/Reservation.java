package com.evgen;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({
    @CompoundIndex(name = "name3",
        unique = true,
        def = "{'apartmentNumber' : 2, 'reservationDay' : 2, 'hotel' : 1}")
})
public class Reservation {

  @Id
  private ObjectId reservationId;

  @DBRef
  private Hotel hotel;

  private String apartmentNumber;

  private List<Long> reservationDay;

  public List<Long> getReservationDay() {
    return reservationDay;
  }

  public void setReservationDay(List<Long> reservationDay) {
    this.reservationDay = reservationDay;
  }

  public Reservation() {
  }

  public Reservation(Hotel hotel, String apartmentNumber, ObjectId reservationId, List<Long> reservationDay) {
    this.apartmentNumber = apartmentNumber;
    this.hotel = hotel;
    this.reservationId = reservationId;
    this.reservationDay = reservationDay;
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
