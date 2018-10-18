package com.evgen.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.evgen.Guest;
import com.evgen.Hotel;
import com.evgen.Reservation;
import com.evgen.config.DaoImplTestConfig;
import com.evgen.dao.ReservationDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoImplTestConfig.class)
public class DaoImplTest {

  private static final Logger LOGGER = LogManager.getLogger(DaoImplTest.class);

  @Autowired
  ReservationDao reservationDao;

  @Test
  public void createReservationTest() {
    LOGGER.debug("test: create reservation");

    ObjectId guestId = new ObjectId("5bc70e09677aa47db3942744");
    Guest guest = reservationDao.getGuestByGuestId(guestId);
    Hotel testHotel = reservationDao.getHotelByName("TestHotel");
    Reservation reservation = new Reservation(testHotel, "2", new ObjectId());
    List<Reservation> reservationsTest = new ArrayList<Reservation>();
    reservationsTest.add(reservation);
    guest.setReservations(reservationsTest);

    reservationDao.createReservation(guest);

    Guest guestTest = reservationDao.getGuestByGuestId(guestId);
    Assert.assertEquals(guestTest.getReservations().size(), reservationsTest.size());
  }

  @Test
  public void getGuestByGuestIdTest() {
    LOGGER.debug("test: get guest by guest id");

    ObjectId guestId = new ObjectId("5bc70e09677aa47db3942744");
    Guest guest = reservationDao.getGuestByGuestId(guestId);
    Assert.assertEquals(guest.getName(), "sergei");
  }

  @Test
  public void getHotelByName() {
    LOGGER.debug("test: get hotel by name");

    Hotel test = reservationDao.getHotelByName("TestHotel");
    Assert.assertEquals(test.getHotelName(), "TestHotel");
  }

  @Test
  public void getGuests() {
    LOGGER.debug("test: get all guest");

    List<Guest> guests = reservationDao.getGuests();
    Assert.assertEquals(guests.size(), 1);
  }
}
