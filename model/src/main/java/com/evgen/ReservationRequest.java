package com.evgen;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;

public class ReservationRequest {

  @NotNull(message = "ApartmentNumber cannot be null")
  private String apartmentNumber;

  @NotNull(message = "GuestId cannot be null")
  private String guestId;

  @NotNull(message = "HotelName cannot be null")
  private String hotelName;

  @NotNull(message = "StartReservationData cannot be null")
  private String startReservationData;

  @NotNull(message = "EndReservationData cannot be null")
  private String endReservationData;

  public String getGuestId() {
    return guestId;
  }

  public void setGuestId(String guestId) {
    this.guestId = guestId;
  }

  public String getApartmentNumber() {
    return apartmentNumber;
  }

  public void setApartmentNumber(String apartmentId) {
    this.apartmentNumber = apartmentId;
  }

  public String getHotelName() {
    return hotelName;
  }

  public void setHotelName(String hotelName) {
    this.hotelName = hotelName;
  }

  public ObjectId getGuestIdAsObjectId() {
    return new ObjectId(guestId);
  }

  public String getStartReservationData() {
    return startReservationData;
  }

  public void setStartReservationData(String startReservationData) {
    this.startReservationData = startReservationData;
  }

  public String getEndReservationData() {
    return endReservationData;
  }

  public void setEndReservationData(String endReservationData) {
    this.endReservationData = endReservationData;
  }
}
