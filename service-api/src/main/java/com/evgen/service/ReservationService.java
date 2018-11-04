package com.evgen.service;

import com.evgen.Reservation;
import com.evgen.ReservationRequest;

public interface ReservationService {

  Integer createReservation(ReservationRequest reservationRequest);

  Reservation retrieveReservation(Integer id);

  Integer updateReservation(Integer reservationId, ReservationRequest reservationRequest);

  Integer deleteReservation(String id, String guestId);

}