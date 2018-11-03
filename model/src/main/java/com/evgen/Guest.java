package com.evgen;

import java.util.List;

public class Guest {

  private String guestId;

  private String name;

  private String password;

  private List<Reservation> reservations;

  public Guest() {
  }

  public Guest(String guestId, List<Reservation> reservations) {
    this.guestId = guestId;
    this.reservations = reservations;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getGuestId() {
    return guestId;
  }

  public void setGuestId(String guestId) {
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