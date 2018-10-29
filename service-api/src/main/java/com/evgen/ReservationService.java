package com.evgen;

public interface ReservationService {

  Guest createReservation(ReservationRequest reservationRequest);

  Reservation retrieveReservation(String id);

  Guest updateReservation(String reservationId, ReservationRequest reservationRequest);

  Guest deleteReservation(String id, String guestId);

}