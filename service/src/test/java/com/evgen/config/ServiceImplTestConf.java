package com.evgen.config;

import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.evgen.dao.GuestRepository;
import com.evgen.dao.HotelRepository;
import com.evgen.service.ReservationService;
import com.evgen.service.ReservationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ServiceImplTestConf {

  @Bean
  public GuestRepository guestRepository() {
    return EasyMock.createMock(GuestRepository.class);
  }

  @Bean
  public HotelRepository hotelRepository() {
    return EasyMock.createMock(HotelRepository.class);
  }

  @Bean
  public ReservationService reservationService(){
    return new ReservationServiceImpl(guestRepository(),hotelRepository());
  }

  @Bean
  public ObjectMapper objectMapper() {
    return  new ObjectMapper();
  }
}
