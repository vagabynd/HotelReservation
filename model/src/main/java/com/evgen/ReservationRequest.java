package com.evgen;

import org.bson.types.ObjectId;

public class ReservationRequest {

  private String apartmentNumber;

  private String guestId;

  private String hotelName;

  private String startReservationData;

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
