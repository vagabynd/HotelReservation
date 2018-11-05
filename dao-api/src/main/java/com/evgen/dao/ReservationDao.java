package com.evgen.dao;

import com.evgen.Guest;
import com.evgen.Reservation;

public interface ReservationDao {

  Reservation retrieveReservation(Integer reservationId);

  Guest createReservation(String guestId, Reservation reservation);

  Guest updateReservation(Reservation reservation);

  Guest deleteReservation(String reservationId);

}
