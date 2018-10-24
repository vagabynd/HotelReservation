package com.evgen.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.evgen.Guest;
import com.evgen.Reservation;
import com.mongodb.client.result.UpdateResult;

@Component
public class ReservationDaoImpl {

  private MongoTemplate mongoTemplate;

  @Autowired
  public ReservationDaoImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public UpdateResult addReservationToGuest(String guestId, Reservation reservation) {
    Update update = new Update().push("reservations", reservation.getReservationId());
    Query query = new Query(Criteria.where("_id").is(guestId));

    return mongoTemplate.updateFirst(query, update, Guest.class);
  }
}
