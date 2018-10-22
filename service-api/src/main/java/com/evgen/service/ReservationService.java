package com.evgen.service;

import com.evgen.Guest;
import com.evgen.Reservation;
import com.evgen.ReservationRequest;

public interface ReservationService {

  Guest createReservation(ReservationRequest reservationRequest);

  Reservation retrieveReservation(String id);

  Guest updateReservation(String reservationId,ReservationRequest reservationRequest);

  Guest deleteReservation(String id, String guestId);

}
