package com.evgen;

import com.evgen.Guest;
import com.evgen.Reservation;

public interface ReservationDao {

  Reservation retrieveReservation(Integer reservationId);

  Guest createReservation(String guestId, Reservation reservation);

  Guest updateReservation(Reservation reservation, String guestId);

  Guest deleteReservation(String reservationId, String guestId);

}
