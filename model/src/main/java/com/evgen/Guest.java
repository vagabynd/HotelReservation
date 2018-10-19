package com.evgen;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Guest {

  @Id
  private ObjectId guestId;

  private String name;

  @DBRef
  private List<Reservation> reservations;

  public Guest() {
  }

  public Guest(ObjectId guestId, List<Reservation> reservations) {
    this.guestId = guestId;
    this.reservations = reservations;
  }

  public ObjectId getGuestId() {
    return guestId;
  }

  public void setGuestId(ObjectId guestId) {
    this.guestId = guestId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Reservation> getReservations() {
    return reservations;
  }

  public void setReservations(List<Reservation> reservations) {
    this.reservations = reservations;
  }
}
