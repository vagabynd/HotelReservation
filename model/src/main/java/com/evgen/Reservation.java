package com.evgen;

import java.util.List;

public class Reservation {

  private String reservationId;

  private Hotel hotel;

  private String apartmentNumber;

  private List<Long> reservationDay;

  private String startReservationDay;

  private String endReservationDay;

  public Reservation() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public List<Long> getReservationDay() {
    return reservationDay;
  }

  public void setReservationDay(List<Long> reservationDay) {
    this.reservationDay = reservationDay;
  }

  public String getReservationId() {
    return reservationId;
  }

  public void setReservationId(String reservationId) {
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

  public String getStartReservationDay() {
    return startReservationDay;
  }

  public void setStartReservationDay(String startReservationDay) {
    this.startReservationDay = startReservationDay;
  }

  public String getEndReservationDay() {
    return endReservationDay;
  }

  public void setEndReservationDay(String endReservationDay) {
    this.endReservationDay = endReservationDay;
  }

  public Builder updater() {
    return new Builder(this);
  }

  public static class Builder {

    private Reservation instance;

    Builder() {
      this.instance = new Reservation();
    }

    Builder(Reservation instance) {
      this.instance = instance;
    }

    public Builder hotel(Hotel hotel) {
      instance.setHotel(hotel);
      return this;
    }

    public Builder apartmentNumber(String number) {
      instance.setApartmentNumber(number);
      return this;
    }

    public Builder reservationDay(List<Long> reservationDay) {
      instance.setReservationDay(reservationDay);
      return this;
    }

    public Builder reservationId(String reservationId) {
      instance.setReservationId(reservationId);
      return this;
    }

    public Builder startReservationDay(String startReservationDay) {
      instance.setStartReservationDay(startReservationDay);
      return this;
    }

    public Builder endReservationDay(String endReservationDay) {
      instance.setEndReservationDay(endReservationDay);
      return this;
    }

    public Reservation build() {
      return instance;
    }
  }
}