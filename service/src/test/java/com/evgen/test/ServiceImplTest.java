package com.evgen.test;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.evgen.Guest;
import com.evgen.Reservation;
import com.evgen.ReservationDao;
import com.evgen.ReservationRequest;
import com.evgen.service.ReservationService;
import com.evgen.config.ServiceImplTestConf;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceImplTestConf.class)
public class ServiceImplTest {

  private static final Logger LOGGER = LogManager.getLogger(ServiceImplTest.class);

  private static final String GUEST = "/Guest-with-reservations.json";
  private static final String RESERVATION_REQUEST = "/Reservation-request.json";
  private static final String UPDATE_RESERVATION_REQUEST = "/Update-reservation-request.json";
  private static final String RESERVATIONS = "/Reservations.json";

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private ReservationDao reservationDao;


  @Before
  public void setUp() {
    reset(reservationDao);
  }

  @Test
  public void retrieveReservationTest() throws Exception {
    LOGGER.debug("test: retrieve reservation");

    Reservation reservationTest = objectMapper
        .readValue(getClass().getResourceAsStream(RESERVATIONS), Reservation.class);

    expect(reservationDao.retrieveReservation(5)).andReturn(reservationTest);
    replay(reservationDao);

    Reservation reservation = reservationService.retrieveReservation(5);

    Assert.assertEquals(reservation.getReservationId(), "5bc7340b677aa44e986d19db");
  }

  @Test
  public void createReservationTest() throws Exception {
    LOGGER.debug("test: create reservation");

    Guest guest = objectMapper.readValue(getClass().getResourceAsStream(GUEST), Guest.class);

    expect(reservationDao.createReservation(anyString(), anyObject())).andReturn(guest);
    replay(reservationDao);

    ReservationRequest reservationRequest = objectMapper
        .readValue(getClass().getResourceAsStream(RESERVATION_REQUEST), ReservationRequest.class);
    Guest guestReturn = reservationService.createReservation(reservationRequest);

    Assert.assertEquals(guestReturn.getReservations().size(), 1);
  }

  @Test
  public void deleteReservationTest() throws Exception {
    LOGGER.debug("test: delete reservation");

    Guest guest = objectMapper.readValue(getClass().getResourceAsStream(GUEST), Guest.class);
    expect(reservationDao.deleteReservation("5","5")).andReturn(guest);
    replay(reservationDao);

    Guest guestReturn = reservationService
        .deleteReservation("5", "5");

    Assert.assertNotNull(guestReturn);
  }

  @Test
  public void updateReservationTest() throws Exception {
    LOGGER.debug("test: update reservation");

    Guest guest = objectMapper.readValue(getClass().getResourceAsStream(GUEST), Guest.class);
    Reservation reservationTest = objectMapper
        .readValue(getClass().getResourceAsStream(RESERVATIONS), Reservation.class);

    expect(reservationDao.updateReservation(reservationTest, "5")).andReturn(guest);
    expect(reservationDao.retrieveReservation(5)).andReturn(reservationTest);
    replay(reservationDao);

    ReservationRequest updateReservationRequest = objectMapper
        .readValue(getClass().getResourceAsStream(UPDATE_RESERVATION_REQUEST), ReservationRequest.class);
    Guest guestReturn = reservationService
        .updateReservation(5, updateReservationRequest);

    Reservation reservationReturn = guestReturn.getReservations()
        .stream()
        .filter(o -> o.getReservationId().equals("5"))
        .findFirst()
        .orElse(null);

    assert reservationReturn != null;
    Assert.assertEquals(reservationReturn.getApartmentNumber(), "3");
  }
}