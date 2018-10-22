package com.evgen;

import java.util.List;

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
  private String reservationId;

  @DBRef
  private Hotel hotel;

  private String apartmentNumber;

  private List<Long> reservationDay;

  public Reservation() {
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

  public static Builder builder() {
    return new Builder();
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

    public Builder setHotel(Hotel hotel) {
      instance.setHotel(hotel);
      return this;
    }

    public Builder setApartmentNumber(String number) {
      instance.setApartmentNumber(number);
      return this;
    }

    public Builder setReservationDay(List<Long> reservationDay) {
      instance.setReservationDay(reservationDay);
      return this;
    }

    public Builder setReservationId(String reservationId) {
      instance.setReservationId(reservationId);
      return this;
    }

    public Reservation build() {
      return instance;
    }
  }
}
