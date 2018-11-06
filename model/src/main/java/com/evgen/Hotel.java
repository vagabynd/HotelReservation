package com.evgen;

import java.util.List;
import java.util.Objects;

public class Hotel {

  private String hotelId;

  private String hotelName;

  private List<Apartment> apartments;

  public Hotel() {
  }

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Hotel hotel = (Hotel) o;
    return Objects.equals(hotelId, hotel.hotelId) &&
        Objects.equals(hotelName, hotel.hotelName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hotelId, hotelName);
  }
}