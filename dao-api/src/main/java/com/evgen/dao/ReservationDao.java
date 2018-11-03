package com.evgen.dao;

import java.util.List;
import java.util.Optional;

import com.evgen.Guest;
import com.evgen.Hotel;
import com.evgen.Reservation;

public interface ReservationDao {

  Optional<Guest> findByGuestId(String guestId);

  Optional<Hotel> findByHotelName(String hotelName);

  List<Reservation> deleteByReservationId(String reservationId);

  Optional<Reservation> findByReservationId(String reservationId);

  void addReservationToGuest(Reservation reservation, String guestId);

}
