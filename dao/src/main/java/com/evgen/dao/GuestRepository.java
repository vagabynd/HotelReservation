package com.evgen.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.evgen.Guest;

@Component
public interface GuestRepository extends MongoRepository<Guest, String> {

  Guest findByGuestId(String guestId);

}
