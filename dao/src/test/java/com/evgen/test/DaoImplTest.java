package com.evgen.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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
  private static final String RESERVATIONS = "/json/Reservation.json";
  private static final String RESERVATIONS_UPDATE = "/json/ReservationExist.json";
  private static final String ID = "1";
  private static final String INCORRECT_ID = "100";

  @Autowired
  ReservationDao reservationDao;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  public void saveReservationTest() throws IOException {
    LOGGER.debug("test: save reservation");

    Reservation reservationTest = objectMapper
        .readValue(getClass().getResourceAsStream(RESERVATIONS), Reservation.class);
    Guest guest = reservationDao.createReservation(ID, reservationTest);
    Assert.assertEquals(ID, guest.getGuestId());
  }

  @Test(expected = DataIntegrityViolationException.class)
  public void saveReservationByIncorrectGuestIdTest() throws IOException {
    LOGGER.debug("test: save reservation by incorrect guest id");

    Reservation reservationTest = objectMapper
        .readValue(getClass().getResourceAsStream(RESERVATIONS), Reservation.class);
    reservationDao.createReservation(INCORRECT_ID, reservationTest);
  }

  @Test
  public void getReservationByIdTest() {
    LOGGER.debug("test: get reservation by id");

    Reservation reservation = reservationDao.retrieveReservation(1);

    Assert.assertNotNull(reservation);
    Assert.assertEquals(reservation.getApartmentNumber(), ID);
  }

  @Test(expected = EmptyResultDataAccessException.class)
  public void getReservationByIncorrectIdTest() {
    LOGGER.debug("test: get reservation by incorrect id");

    reservationDao.retrieveReservation(100);
  }

  @Test
  public void deleteReservationByIdTest() {
    LOGGER.debug("test: delete reservation by id");

    Guest guest = reservationDao.deleteReservation(ID, ID);
    Assert.assertEquals(guest.getReservations().size(), 0);
  }

  @Test
  public void deleteReservationByIncorrectIdTest() {
    LOGGER.debug("test: delete reservation by incorrect id");

    Guest guest = reservationDao.deleteReservation(INCORRECT_ID, ID);
    Assert.assertEquals(guest.getReservations().size(), 1);
  }

  @Test(expected = EmptyResultDataAccessException.class)
  public void deleteReservationByIncorrectGuestIdTest() {
    LOGGER.debug("test: delete reservation by incorrect guest id");

    reservationDao.deleteReservation(ID, INCORRECT_ID);
  }

  @Test
  public void updateReservationTest() throws IOException {
    LOGGER.debug("test: update reservation by id");

    Reservation reservationTest = objectMapper
        .readValue(getClass().getResourceAsStream(RESERVATIONS_UPDATE), Reservation.class);

    Guest guest = reservationDao.updateReservation(reservationTest, ID);

    Assert.assertEquals(guest.getReservations().size(), 1);
  }

  @Test(expected = EmptyResultDataAccessException.class)
  public void updateReservationByIncorrectIdTest() throws IOException {
    LOGGER.debug("test: update reservation by incorrect id");

    Reservation reservationTest = objectMapper
        .readValue(getClass().getResourceAsStream(RESERVATIONS_UPDATE), Reservation.class);

    reservationDao.updateReservation(reservationTest, INCORRECT_ID);

  }
}