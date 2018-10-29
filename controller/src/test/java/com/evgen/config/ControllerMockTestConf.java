package com.evgen.config;

import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.evgen.ReservationService;
import com.evgen.service.ReservationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.evgen.controller")
public class ControllerMockTestConf {

  @Bean
  public ReservationService reservationService() {
    return EasyMock.createMock(ReservationServiceImpl.class);
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}