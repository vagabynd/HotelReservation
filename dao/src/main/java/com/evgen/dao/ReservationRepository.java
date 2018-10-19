package com.evgen.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.evgen.Reservation;

@Component
public interface ReservationRepository extends MongoRepository<Reservation, String> {

  List<Reservation> deleteByReservationId(ObjectId reservationId);

}