package com.evgen.service;

import org.bson.types.ObjectId;

import com.evgen.Guest;
import com.evgen.Reservation;
import com.evgen.ReservationRequest;

public interface ReservationService {

  Guest createReservation(ReservationRequest reservationRequest);

  Reservation retrieveReservation(ObjectId id);

  Guest updateReservation(ObjectId reservationId,ReservationRequest reservationRequest);

  Guest deleteReservation(ObjectId id, ObjectId guestId);

}
