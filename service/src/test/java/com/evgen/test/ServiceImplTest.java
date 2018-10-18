package com.evgen.test;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

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
import com.evgen.dao.GuestRepository;
import com.evgen.dao.HotelRepository;
import com.evgen.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceImplTestConf.class)
public class ServiceImplTest {

  private static final Logger LOGGER = LogManager.getLogger(ServiceImplTest.class);

  private static final String GUEST = "/Guest-with-reservations.json";
  private static final String RESERVATION_REQUEST = "/Reservation-request.json";
  private static final String HOTEL = "/Hotel.json";
  private static final String UPDATE_RESERVATION_REQUEST = "/Update-reservation-request.json";

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private GuestRepository guestRepositoryMock;

  @Autowired
  private HotelRepository hotelRepositoryMock;

  @After
  public void clean() {
    verify();
  }

  @Before
  public void setUp() {
    reset(guestRepositoryMock);
    reset(hotelRepositoryMock);
  }

  @Test
  public void retrieveReservationTest() throws Exception {
    LOGGER.debug("test: retrieve reservation");

    List<Guest> guests = new ArrayList<>();
    Guest guest = objectMapper.readValue(getClass().getResourceAsStream(GUEST), Guest.class);
    guests.add(guest);

    expect(guestRepositoryMock.findAll()).andReturn(guests);
    replay(guestRepositoryMock);

    Reservation reservation = reservationService.retrieveReservation(new ObjectId("5bc7340b677aa44e986d19db"));

    Assert.assertEquals(reservation.getReservationId().toString(), "5bc7340b677aa44e986d19db");
  }

  @Test
  public void createReservationTest() throws Exception {
    LOGGER.debug("test: create reservation");

    Guest guest = objectMapper.readValue(getClass().getResourceAsStream(GUEST), Guest.class);
    Hotel hotel = objectMapper.readValue(getClass().getResourceAsStream(HOTEL), Hotel.class);

    expect(guestRepositoryMock.findByGuestId(new ObjectId("5bc449c09ddbcd660ac58f07"))).andReturn(guest);
    expect(hotelRepositoryMock.findByHotelName("Abc")).andReturn(hotel);
    expect(guestRepositoryMock.save(guest)).andReturn(guest);
    replay(hotelRepositoryMock, guestRepositoryMock);

    ReservationRequest reservationRequest = objectMapper
        .readValue(getClass().getResourceAsStream(RESERVATION_REQUEST), ReservationRequest.class);
    Guest guestReturn = reservationService.createReservation(reservationRequest);

    Assert.assertEquals(guestReturn.getReservations().size(), 2);
  }

  @Test
  public void deleteReservationTest() throws Exception {
    LOGGER.debug("test: delete reservation");

    Guest guest = objectMapper.readValue(getClass().getResourceAsStream(GUEST), Guest.class);

    expect(guestRepositoryMock.findByGuestId(new ObjectId("5bc449c09ddbcd660ac58f07"))).andReturn(guest);
    expect(guestRepositoryMock.save(guest)).andReturn(guest);
    replay(guestRepositoryMock);

    Guest guestReturn = reservationService
        .deleteReservation(new ObjectId("5bc7340b677aa44e986d19db"), new ObjectId("5bc449c09ddbcd660ac58f07"));

    Assert.assertEquals(guestReturn.getReservations().size(), 0);
  }

  @Test
  public void updateReservationTest() throws Exception {
    LOGGER.debug("test: update reservation");

    Guest guest = objectMapper.readValue(getClass().getResourceAsStream(GUEST), Guest.class);
    Hotel hotel = objectMapper.readValue(getClass().getResourceAsStream(HOTEL), Hotel.class);
    List<Guest> guests = new ArrayList<>();
    guests.add(guest);

    expect(guestRepositoryMock.findByGuestId(new ObjectId("5bc449c09ddbcd660ac58f07"))).andReturn(guest);
    expect(hotelRepositoryMock.findByHotelName("Abc")).andReturn(hotel);
    expect(guestRepositoryMock.findAll()).andReturn(guests);
    expect(guestRepositoryMock.save(guest)).andReturn(guest);
    replay(guestRepositoryMock, hotelRepositoryMock);

    ReservationRequest updateReservationRequest = objectMapper
        .readValue(getClass().getResourceAsStream(UPDATE_RESERVATION_REQUEST), ReservationRequest.class);
    Guest guestReturn = reservationService.updateReservation(new ObjectId("5bc7340b677aa44e986d19db"), updateReservationRequest);

    Reservation reservationReturn = guestReturn.getReservations()
        .stream()
        .filter(o -> o.getReservationId().equals(new ObjectId("5bc7340b677aa44e986d19db")))
        .findFirst()
        .orElse(null);

    assert reservationReturn != null;
    Assert.assertEquals(reservationReturn.getApartmentNumber(), "1");
  }
}
