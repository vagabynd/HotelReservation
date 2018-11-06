package com.evgen.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.evgen.Guest;
import com.evgen.Reservation;
import com.evgen.ReservationDao;
import com.evgen.config.DaoImplTestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@Import(DaoImplTestConfig.class)
@Transactional
public class DaoImplTest {

  private static final Logger LOGGER = LogManager.getLogger(DaoImplTest.class);
  private static final String RESERVATIONS = "/Reservation.json";
  private static final String RESERVATIONS_UPDATE = "/ReservationExist.json";

  @Autowired
  ReservationDao reservationDao;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  public void saveReservationTest() throws IOException {
    LOGGER.debug("test: save reservation");

    Reservation reservationTest = objectMapper
        .readValue(getClass().getResourceAsStream(RESERVATIONS), Reservation.class);
    Guest guest = reservationDao.createReservation("1", reservationTest);
    Assert.assertEquals("1", guest.getGuestId());
  }


  @Test
  public void getReservationByIdTest() {
    LOGGER.debug("test: get reservation by id");

    Reservation reservation = reservationDao.retrieveReservation(1);

    LOGGER.debug(reservation.getStartReservationDay());

    Assert.assertNotNull(reservation);
    Assert.assertEquals(reservation.getApartmentNumber(), "1");
  }

  @Test
  public void deleteReservationByIdTest() {
    LOGGER.debug("test: delete reservation by id");

    Guest guest = reservationDao.deleteReservation("1", "1");
    Assert.assertEquals(guest.getReservations().size(), 0);
  }

  @Test
  public void updateReservationTest() throws IOException {
    LOGGER.debug("test: update reservation by id");

    Reservation reservationTest = objectMapper
        .readValue(getClass().getResourceAsStream(RESERVATIONS_UPDATE), Reservation.class);

    Guest guest = reservationDao.updateReservation(reservationTest, "1");

    Assert.assertEquals(guest.getReservations().size(), 1);
  }

}