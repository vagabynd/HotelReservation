package com.evgen.test;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.evgen.Guest;
import com.evgen.Hotel;
import com.evgen.Reservation;
import com.evgen.ReservationRequest;
import com.evgen.config.ServiceImplTestConf;
import com.evgen.dao.ReservationDao;
import com.evgen.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceImplTestConf.class)
public class ServiceImplTest {

  private static final Logger LOGGER = LogManager.getLogger(ServiceImplTest.class);

  private static final String GUEST = "/home/yauheni_rotar/HotelReservation/service/src/test/java/resources/Guest-with-reservations.json";
  private static final String RESERVATION_REQUEST = "/home/yauheni_rotar/HotelReservation/service/src/test/java/resources/ReservationRequest.json";
  private static final String HOTEL = "/home/yauheni_rotar/HotelReservation/service/src/test/java/resources/Hotel.json";

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private ReservationDao reservationMockDao;

  @After
  public void clean() {
    verify();
  }

  @Before
  public void setUp() {
    reset(reservationMockDao);
  }

  @Test
  public void retrieveReservationTest() throws Exception {
    LOGGER.debug("test: retrieve reservation");

    List<Guest> guests = new ArrayList<>();
    Guest guest = objectMapper.readValue(new File(GUEST), Guest.class);
    guests.add(guest);

    expect(reservationMockDao.getGuests()).andReturn(guests);
    replay(reservationMockDao);

    Reservation reservation = reservationService.retrieveReservation(new ObjectId("5bc7340b677aa44e986d19db"));

    Assert.assertEquals(reservation.getReservationId().toString(), "5bc7340b677aa44e986d19db");
  }

  @Test
  public void createReservationTest() throws Exception {
    LOGGER.debug("test: create reservation");

    Guest guest = objectMapper.readValue(new File(GUEST), Guest.class);
    Hotel hotel = objectMapper.readValue(new File(HOTEL), Hotel.class);

    expect(reservationMockDao.getGuestByGuestId(new ObjectId("5bc449c09ddbcd660ac58f07"))).andReturn(guest);
    expect(reservationMockDao.getHotelByName("Abc")).andReturn(hotel);
    reservationMockDao.createReservation(anyObject());
    replay(reservationMockDao);

    ReservationRequest reservationRequest = objectMapper
        .readValue(new File(RESERVATION_REQUEST), ReservationRequest.class);
    Guest guestReturn = reservationService.createReservation(reservationRequest);

    Assert.assertEquals(guestReturn.getReservations().size(), 2);
  }

  @Test
  public void deleteReservationTest() throws Exception {
    LOGGER.debug("test: delete reservation");

    Guest guest = objectMapper.readValue(new File(GUEST), Guest.class);

    expect(reservationMockDao.getGuestByGuestId(new ObjectId("5bc449c09ddbcd660ac58f07"))).andReturn(guest);
    reservationMockDao.createReservation(anyObject());
    replay(reservationMockDao);

    Guest guestReturn = reservationService
        .deleteReservation(new ObjectId("5bc7340b677aa44e986d19db"), new ObjectId("5bc449c09ddbcd660ac58f07"));

    Assert.assertEquals(guestReturn.getReservations().size(), 0);
  }
}
