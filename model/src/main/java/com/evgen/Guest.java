package com.evgen;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Guest {

  @Id
  private ObjectId guestId;

  private String name;

  private Reservation reservation;

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

  public Reservation getReservation() {
    return reservation;
  }

 // public void setReservation(Reservation reservation) {
 //   this.reservation = reservation;
 // }
}
