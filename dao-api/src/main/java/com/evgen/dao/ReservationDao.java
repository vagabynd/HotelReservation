package com.evgen.dao;

import java.util.List;

import org.bson.types.ObjectId;

import com.evgen.Guest;
import com.evgen.Hotel;

public interface ReservationDao {

  void createReservation(Guest guest);

  Guest getGuestByGuestId(ObjectId guestId);

  List<Guest> getGuests();

  Hotel getHotelByName(String hotelName);

}
