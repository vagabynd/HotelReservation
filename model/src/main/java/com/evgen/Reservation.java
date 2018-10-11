package com.evgen;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Reservation {

  @Id
  private ObjectId reservationId;

  private Apartment apartment;

  private Guest guest;

  public ObjectId getReservationId() {
    return reservationId;
  }

  public void setReservationId(ObjectId reservationId) {
    this.reservationId = reservationId;
  }

  public Apartment getApartment() {
    return apartment;
  }

  public void setApartment(Apartment apartment) {
    this.apartment = apartment;
  }

  public Guest getGuest() {
    return guest;
  }

  public void setGuest(Guest guest) {
    this.guest = guest;
  }
}
