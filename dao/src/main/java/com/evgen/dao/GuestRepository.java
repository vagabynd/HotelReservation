package com.evgen.dao;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.evgen.Guest;

@Component
public interface GuestRepository extends MongoRepository<Guest, String> {

  Guest findByGuestId(ObjectId guestId);

}
