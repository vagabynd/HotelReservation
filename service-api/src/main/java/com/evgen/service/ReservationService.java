package com.evgen.service;

import com.evgen.Guest;
import com.evgen.Reservation;
import com.evgen.ReservationRequest;

public interface ReservationService {

  Guest createReservation(ReservationRequest reservationRequest);

  Reservation retrieveReservation(Integer id);

  Guest updateReservation(Integer reservationId, ReservationRequest reservationRequest);

  Guest deleteReservation(String id, String guestId);

}