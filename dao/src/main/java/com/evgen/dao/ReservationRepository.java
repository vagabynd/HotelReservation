package com.evgen.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.evgen.Reservation;

@Component
public interface ReservationRepository extends MongoRepository<Reservation, String> {

}
