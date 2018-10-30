package com.evgen.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.evgen.Guest;

@Component
public interface GuestRepository extends MongoRepository<Guest, String> {

  Optional<Guest> findByGuestId(String guestId);

}