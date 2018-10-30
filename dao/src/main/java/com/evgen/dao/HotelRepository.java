package com.evgen.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.evgen.Hotel;

@Component
public interface HotelRepository extends MongoRepository<Hotel, String> {

  Optional<Hotel> findByHotelName(String hotelName);

}