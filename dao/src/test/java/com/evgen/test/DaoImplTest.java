package com.evgen.test;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.evgen.Guest;
import com.evgen.Hotel;
import com.evgen.Reservation;
import com.evgen.config.DaoImplTestConfig;
import com.evgen.dao.GuestRepository;
import com.evgen.dao.HotelRepository;
import com.evgen.dao.ReservationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoImplTestConfig.class)
public class DaoImplTest {

  private static final Logger LOGGER = LogManager.getLogger(DaoImplTest.class);
  private static final String RESERVATIONS = "/Reservation.json";
  private static final String RESERVATIONS_EXIST = "/ReservationExist.json";
  @Autowired
  GuestRepository guestRepository;
  @Autowired
  HotelRepository hotelRepository;
  @Autowired
  ReservationRepository reservationRepository;
  @Autowired
  ObjectMapper objectMapper;

  @Test
  public void saveReservationTest() throws IOException {
    LOGGER.debug("test: save reservation");

    Reservation reservationTest = objectMapper
        .readValue(getClass().getResourceAsStream(RESERVATIONS), Reservation.class);
    Reservation reservation = reservationRepository.save(reservationTest);
    Assert.assertEquals(reservationTest.getApartmentNumber(), reservation.getApartmentNumber());
  }

  @Test(expected = DuplicateKeyException.class)
  public void saveExistReservationTest() throws IOException {
    LOGGER.debug("test: save exist reservation");

    Reservation reservationTest = objectMapper
        .readValue(getClass().getResourceAsStream(RESERVATIONS_EXIST), Reservation.class);
    reservationRepository.save(reservationTest);
  }

  @Test
  public void getReservationByIdTest() throws IOException {
    LOGGER.debug("test: get reservation by id");

    Reservation reservationTest = objectMapper
        .readValue(getClass().getResourceAsStream(RESERVATIONS), Reservation.class);
    reservationRepository.save(reservationTest);
    Reservation reservation = reservationRepository.findById("5bc7340b677aa44e986d19db").orElse(null);

    Assert.assertNotNull(reservation);
    Assert.assertEquals(reservation.getApartmentNumber(), "3");
  }

  @Test
  public void deleteReservationByIdTest() {
    LOGGER.debug("test: delete reservation by id");

    reservationRepository.deleteByReservationId(new ObjectId("5bc7340b677aa44e986d19db"));
    Assert.assertEquals(reservationRepository.findAll().size(), 0);
  }

  @Test
  public void getHotelByName() {
    LOGGER.debug("test: get hotel by name");

    Hotel test = hotelRepository.findByHotelName("TestHotel");
    Assert.assertEquals(test.getHotelName(), "TestHotel");
  }

  @Test
  public void getGuests() {
    LOGGER.debug("test: get all guest");

    List<Guest> guests = guestRepository.findAll();
    Assert.assertEquals(guests.size(), 1);
  }
}
