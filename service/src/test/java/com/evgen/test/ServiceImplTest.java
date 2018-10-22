package com.evgen.test;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.evgen.dao.ReservationRepository;
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
  private static final String RESERVATIONS = "/Reservations.json";

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ReservationService reservationService;

  @Autowired
  private GuestRepository guestRepositoryMock;

  @Autowired
  private HotelRepository hotelRepositoryMock;

  @Autowired
  private ReservationRepository reservationRepositoryMock;

  @After
  public void clean() {
    verify();
  }

  @Before
  public void setUp() {
    reset(guestRepositoryMock);
    reset(reservationRepositoryMock);
    reset(hotelRepositoryMock);
  }

  @Test
  public void retrieveReservationTest() throws Exception {
    LOGGER.debug("test: retrieve reservation");

    List<Reservation> reservations = new ArrayList<>();
    Reservation reservationTest = objectMapper
        .readValue(getClass().getResourceAsStream(RESERVATIONS), Reservation.class);
    reservations.add(reservationTest);

    expect(reservationRepositoryMock.findAll()).andReturn(reservations);
    replay(reservationRepositoryMock);

    Reservation reservation = reservationService.retrieveReservation("5bc7340b677aa44e986d19db");

    Assert.assertEquals(reservation.getReservationId().toString(), "5bc7340b677aa44e986d19db");
  }

  @Test
  public void createReservationTest() throws Exception {
    LOGGER.debug("test: create reservation");

    Guest guest = objectMapper.readValue(getClass().getResourceAsStream(GUEST), Guest.class);
    Hotel hotel = objectMapper.readValue(getClass().getResourceAsStream(HOTEL), Hotel.class);

    expect(guestRepositoryMock.findByGuestId("5bc449c09ddbcd660ac58f07")).andReturn(guest);
    expect(hotelRepositoryMock.findByHotelName("Abc")).andReturn(hotel);
    expect(guestRepositoryMock.save(guest)).andReturn(guest);
    expect(reservationRepositoryMock.save(anyObject())).andReturn(new Reservation());
    replay(hotelRepositoryMock, guestRepositoryMock, reservationRepositoryMock);

    ReservationRequest reservationRequest = objectMapper
        .readValue(getClass().getResourceAsStream(RESERVATION_REQUEST), ReservationRequest.class);
    Guest guestReturn = reservationService.createReservation(reservationRequest);

    Assert.assertEquals(guestReturn.getReservations().size(), 2);
  }

  @Test
  public void deleteReservationTest() throws Exception {
    LOGGER.debug("test: delete reservation");

    Guest guest = objectMapper.readValue(getClass().getResourceAsStream(GUEST), Guest.class);

    expect(guestRepositoryMock.findByGuestId("5bc449c09ddbcd660ac58f07")).andReturn(guest);
    expect(guestRepositoryMock.save(guest)).andReturn(guest);
    replay(guestRepositoryMock);

    Guest guestReturn = reservationService
        .deleteReservation("5bc7340b677aa44e986d19db", "5bc449c09ddbcd660ac58f07");

    Assert.assertEquals(guestReturn.getReservations().size(), 0);
  }

  @Test
  public void updateReservationTest() throws Exception {
    LOGGER.debug("test: update reservation");

    Guest guest = objectMapper.readValue(getClass().getResourceAsStream(GUEST), Guest.class);
    Hotel hotel = objectMapper.readValue(getClass().getResourceAsStream(HOTEL), Hotel.class);

    List<Reservation> reservations = new ArrayList<>();
    Reservation reservationTest = objectMapper
        .readValue(getClass().getResourceAsStream(RESERVATIONS), Reservation.class);
    reservations.add(reservationTest);

    expect(guestRepositoryMock.findByGuestId("5bc449c09ddbcd660ac58f07")).andReturn(guest);
    expect(hotelRepositoryMock.findByHotelName("Abc")).andReturn(hotel);
    expect(reservationRepositoryMock.save(anyObject())).andReturn(new Reservation());
    expect(reservationRepositoryMock.findAll()).andReturn(reservations);
    replay(guestRepositoryMock, hotelRepositoryMock, reservationRepositoryMock);

    ReservationRequest updateReservationRequest = objectMapper
        .readValue(getClass().getResourceAsStream(UPDATE_RESERVATION_REQUEST), ReservationRequest.class);
    Guest guestReturn = reservationService
        .updateReservation("5bc7340b677aa44e986d19db", updateReservationRequest);

    Reservation reservationReturn = guestReturn.getReservations()
        .stream()
        .filter(o -> o.getReservationId().equals("5bc7340b677aa44e986d19db"))
        .findFirst()
        .orElse(null);

    assert reservationReturn != null;
    Assert.assertEquals(reservationReturn.getApartmentNumber(), "3");
  }
}
