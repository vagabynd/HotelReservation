package com.evgen.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.evgen.Reservation;

@Component
public interface ReservationRepository extends MongoRepository<Reservation, String> {

  List<Reservation> deleteByReservationId(String reservationId);

  Optional<Reservation> findByReservationId(String reservationId);

}