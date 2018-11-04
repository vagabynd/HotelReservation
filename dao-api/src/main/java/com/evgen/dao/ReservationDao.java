package com.evgen.dao;

import com.evgen.Reservation;

public interface ReservationDao {

  Reservation retrieveReservation(Integer reservationId);

  Integer createReservation(String guestId, Reservation reservation);

  Integer updateReservation(Reservation reservation);

  Integer deleteReservation(String reservationId);

}
