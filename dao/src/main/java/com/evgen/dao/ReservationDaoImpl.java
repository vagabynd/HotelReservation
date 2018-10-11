package com.evgen.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.evgen.Reservation;

@Component
public class ReservationDaoImpl implements ReservationDao {

  @Autowired
  private ReservationRepository repository;

  private static final Logger LOGGER = LogManager.getLogger(ReservationDaoImpl.class);

  @Override
  public void createReservation(Reservation reservation) {
    repository.save(reservation);
  }


  /*
  public void add()  {
    repository.save(new Guest("ABC" ));
  }

  public void get() {
    for (Guest guest : repository.findAll()) {
      LOGGER.debug("test: " + guest.getName());
    }
  }
  */
}
