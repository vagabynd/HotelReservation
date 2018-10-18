package com.evgen.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.evgen.Guest;
import com.evgen.Hotel;

@Component
public class ReservationDaoImpl implements ReservationDao {

  private static final Logger LOGGER = LogManager.getLogger(ReservationDaoImpl.class);
  private final GuestRepository guestRepository;
  private final HotelRepository hotelRepository;

  @Autowired
  public ReservationDaoImpl(GuestRepository guestRepository, HotelRepository hotelRepository) {
    this.guestRepository = guestRepository;
    this.hotelRepository = hotelRepository;
  }

  @Override
  public void createReservation(Guest guest) {
    guestRepository.save(guest);
  }

  @Override
  public Guest getGuestByGuestId(ObjectId guestId) {
    return guestRepository.findByGuestId(guestId);
  }

  @Override
  public Hotel getHotelByName(String hotelName) {
    return hotelRepository.findByHotelName(hotelName);
  }

  @Override
  public List<Guest> getGuests() {
    return guestRepository.findAll();
  }

}
