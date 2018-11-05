package com.evgen;

import java.util.List;

public class Hotel {

  private String hotelId;

  private String hotelName;

  private List<Apartment> apartments;

  public Hotel(String hotelId, String hotelName) {
    this.hotelId = hotelId;
    this.hotelName = hotelName;
  }

  public Hotel(String hotelId, String hotelName, List<Apartment> apartments) {
    this.hotelId = hotelId;
    this.hotelName = hotelName;
    this.apartments = apartments;
  }

  public String getHotelId() {
    return hotelId;
  }

  public void setHotelId(String hotelId) {
    this.hotelId = hotelId;
  }

  public String getHotelName() {
    return hotelName;
  }

  public void setHotelName(String hotelName) {
    this.hotelName = hotelName;
  }

  public List<Apartment> getApartments() {
    return apartments;
  }

  public void setApartments(List<Apartment> apartments) {
    this.apartments = apartments;
  }
}