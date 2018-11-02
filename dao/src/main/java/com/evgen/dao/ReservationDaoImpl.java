package com.evgen.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.evgen.Guest;
import com.evgen.Reservation;

@Component
public class ReservationDaoImpl {

  private final MongoTemplate mongoTemplate;

  @Autowired
  public ReservationDaoImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public void addReservationToGuest(Reservation reservation, String guestId) {
    Update update = new Update().push("reservations", reservation.getReservationId());
    Query query = new Query(Criteria.where("_id").is(guestId));

    mongoTemplate.updateFirst(query, update, Guest.class);
  }
}