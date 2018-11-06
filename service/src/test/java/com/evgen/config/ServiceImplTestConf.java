package com.evgen.config;

import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.evgen.ReservationDao;
import com.evgen.dao.ReservationDaoImpl;
import com.evgen.service.ReservationService;
import com.evgen.service.ReservationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ServiceImplTestConf {

  @Bean
  public ReservationDao reservationDao() {
    return EasyMock.createMock(ReservationDaoImpl.class);
  }

  @Bean
  public ReservationService reservationService() {
    return new ReservationServiceImpl(reservationDao());
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}