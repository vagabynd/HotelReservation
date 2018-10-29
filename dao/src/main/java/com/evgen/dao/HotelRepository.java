package com.evgen.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.evgen.Hotel;

@Component
public interface HotelRepository extends MongoRepository<Hotel, String> {

  Hotel findByHotelName(String hotelName);

}